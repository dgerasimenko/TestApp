package com.danil.etl.sheduler;

import com.danil.etl.dao.AbstractObjectDao;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.task.TaskServiceInfoHolder;
import com.danil.etl.utils.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractScheduler {

    protected final AtomicLong taskTime = new AtomicLong();

    protected static final String CHUNK_SIZE_MESSAGE = "Chunk size = ";

    @Autowired
    protected FlightDao flightDao;

    @Autowired
    protected TaskInfoDao taskInfoDao;

    @Value("${thread.pool.size}")
    protected int poolSize;

    @Value("${task.queue.size}")
    protected int taskQueueSize;

    public abstract int transform();

    public abstract void resumeTasks(ExecutorService executor, TaskServiceInfoHolder serviceInfoHolder,
                                     AtomicBoolean needMoreIterations, int defaultIteration);

    public abstract Runnable getTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao,
                                     List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicLong taskTime,
                                     AtomicBoolean needMoreIterations, long totalHandledRecords, int iteration);

    public abstract Class getTaskType();

    protected int execute(AtomicBoolean needMoreIterations, int iteration, TaskServiceInfoHolder serviceInfoHolder) {
        int exitCode = 0;

        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(this.taskQueueSize);
        final ExecutorService executor = new ThreadPoolExecutor(1, this.poolSize, 0L, TimeUnit.MILLISECONDS, queue);
        resumeTasks(executor, serviceInfoHolder, needMoreIterations, iteration);
        long totalHandledRecords = serviceInfoHolder.getTotalHandledRecords();
        long approximatedRecordsAmount = flightDao.getApproximatedRowsCount();
        long remainingRecordsSize = totalHandledRecords != 0 ? approximatedRecordsAmount - totalHandledRecords : approximatedRecordsAmount;

        final long startIterationTime = System.currentTimeMillis();
        try {
            List<Flight> chunk = this.flightDao.getNextChunk(serviceInfoHolder.getPrevRecordId(), serviceInfoHolder.getChunkSize());
            int tmpChunkSize = chunk.size();
            int animationCounter = 0;
            String animation = "";
            final Cache<Long> cache = new Cache<>(20);
            while (tmpChunkSize > 0) {

                Long startIndex = chunk.get(tmpChunkSize - 1).getId();
                final Runnable task = getTask(this.flightDao, this.taskInfoDao, chunk, null, this.taskTime,
                        needMoreIterations, totalHandledRecords, serviceInfoHolder.getIteration());
                executor.submit(task);
                chunk = flightDao.getNextChunk(startIndex, serviceInfoHolder.getChunkSize());
                tmpChunkSize = chunk.size();
                totalHandledRecords += tmpChunkSize;

                while (queue.size() == this.taskQueueSize) {
                    Thread.sleep(this.taskTime.longValue());
                }
                if (tmpChunkSize > 0) {
                    remainingRecordsSize -= tmpChunkSize;
                    final long chunkAmount = remainingRecordsSize / serviceInfoHolder.getChunkSize();
                    long timeEstimation = updateTime(cache, chunkAmount * this.taskTime.longValue());

                    final String estimationMessage = String.format("%s %d. Remaining: %02d h %02d min %02d sec.",
                            getTaskType().getSimpleName(),
                            serviceInfoHolder.getIteration(),
                            TimeUnit.MILLISECONDS.toHours(timeEstimation),
                            TimeUnit.MILLISECONDS.toMinutes(timeEstimation) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeEstimation)),
                            TimeUnit.MILLISECONDS.toSeconds(timeEstimation) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeEstimation)));

                    System.out.print("\r" + estimationMessage + animation + "    ");
                    animation += ".";
                    animationCounter++;
                    if (animationCounter > 4) {
                        animationCounter = 0;
                        animation = "";
                    }
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Someone tries to stop me => " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exceprion happened in iteration:");
            exitCode = -1;
            ex.printStackTrace();
        } finally {
            executor.shutdown();
        }
        boolean done = false;
        try {
            done = executor.awaitTermination((poolSize + taskQueueSize) * 10, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Await termination Interrupted " + ex.getMessage());
            executor.shutdownNow();
            exitCode = -1;
        }

        if (!done) {
            System.out.println("\nApplication stopped by timeout. Some tasks stay inProgress. Exit.");
            exitCode = 1;
        }
        return exitCode;
    }

    private long updateTime(Cache<Long> timeCache, long newTime) {
        final long result;
        int timeCacheSize = timeCache.size();
        if (timeCache.size() > 0) {
            long resTime = 0;
            for (long time : timeCache) {
                resTime += time;
            }
            result = resTime / timeCacheSize;
        } else {
            result = newTime;
        }
        timeCache.put(newTime);
        return result;
    }
}
