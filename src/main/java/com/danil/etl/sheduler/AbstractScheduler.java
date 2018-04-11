package com.danil.etl.sheduler;

import com.danil.etl.dao.AbstractObjectDao;
import com.danil.etl.dao.DestinationDataDao;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.entity.TransformTaskStatus;
import com.danil.etl.task.TransformerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractScheduler {

    protected final AtomicLong taskTime = new AtomicLong();

    @Autowired
    protected FlightDao flightDao;

    @Autowired
    protected TaskInfoDao taskInfoDao;

    @Value("${thread.pool.size}")
    protected int poolSize;

    @Value("${task.queue.size}")
    protected int taskQueueSize;

    @Value("${chunk.size}")
    protected int chunkSize;

    protected long totalRecordsSize;

    public abstract void schedule();

    public abstract void resumeTasks(ExecutorService executor, List<TaskInfo> currentProcessingState, AtomicBoolean needMoreIterations);
    public abstract Runnable getTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao,
                                     List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicLong taskTime, AtomicBoolean needMoreIterations);
    public abstract Class getTaskType();

    public void execute(AtomicBoolean needMoreIterations) {
        totalRecordsSize = flightDao.getApproximatedRowsCount();
        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(this.taskQueueSize);
        final ExecutorService executor = new ThreadPoolExecutor(1, this.poolSize, 0L, TimeUnit.MILLISECONDS, queue);

        final List<TaskInfo> currentProcessingState = taskInfoDao.getOrderedByStart(getTaskType());
        final Long prevRecordId;

        if (CollectionUtils.isEmpty(currentProcessingState)) {
            prevRecordId = 0l;
        } else {
            resumeTasks(executor, currentProcessingState, needMoreIterations);
            prevRecordId = currentProcessingState.get(currentProcessingState.size() - 1).getEndIndex();
        }

        try {
            List<Flight> chunk = this.flightDao.getNextChunk(prevRecordId, chunkSize);
            int tmpChunkSize = chunk.size();
            String progressAnimation = "|/-\\";
            int animationCounter = 0;
            while (tmpChunkSize > 0) {
                Long startIndex = chunk.get(tmpChunkSize - 1).getId();
                final Runnable task = getTask(this.flightDao, this.taskInfoDao, chunk, null, this.taskTime, needMoreIterations);
                executor.submit(task);
                chunk = flightDao.getNextChunk(startIndex, this.chunkSize);
                tmpChunkSize = chunk.size();

                while (queue.size() == this.taskQueueSize) {
                    Thread.sleep(this.taskTime.longValue());
                }
                if (tmpChunkSize > 0) {
                    this.totalRecordsSize -= tmpChunkSize;
                    final long chunkAmount = this.totalRecordsSize / chunkSize;
                    long timeEstimation = chunkAmount * this.taskTime.get();

                    final String estimationMessage = String.format("Estimated time to finish %s: %02d h %02d min %02d sec.",
                            task.getClass().getSimpleName(),
                            TimeUnit.MILLISECONDS.toHours(timeEstimation),
                            TimeUnit.MILLISECONDS.toMinutes(timeEstimation) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeEstimation)),
                            TimeUnit.MILLISECONDS.toSeconds(timeEstimation) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeEstimation)));

                    System.out.print("\r" + estimationMessage + " Processing..." + progressAnimation.charAt(animationCounter % progressAnimation.length()) + " ");
                    animationCounter++;
                }
            }

        } catch (InterruptedException ex) {
            System.out.println("Someone tries to stop me => " + ex.getMessage());
        } finally {
            executor.shutdown();
        }
        boolean done = false;
        try {
            done = executor.awaitTermination((poolSize + taskQueueSize) * this.taskTime.longValue(), TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            System.out.println("Await termination Interrupted => " + ex.getMessage());
            executor.shutdownNow();
        }
        if (!done) {
            System.out.println("\nApplication stopped by timeout. Some tasks stay inProgress. Exit.");
        }
    }
}
