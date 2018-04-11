package com.danil.etl.transformer;

import com.danil.etl.dao.*;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.entity.TransformTaskStatus;
import com.danil.etl.task.ScrapperTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Transformer {

    private final int ESTIMATED_TIME_TO_COMPLETE_ONE_TASK_MILIS = 6000;

    private final AtomicLong taskTime = new AtomicLong();
    private final AtomicLong totalHandledRecords = new AtomicLong();

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private TaskInfoDao taskInfoDao;

    @Value("${thread.pool.size}")
    private int poolSize;

    @Value("${task.queue.size}")
    private int taskQueueSize;

    @Value("${chunk.size}")
    private int chunkSize;

    @Value("${total.records.size}")
    private long totalRecordsSize;

    public void extractAndTransform() {
        final AtomicInteger chunkWithDuplicates = new AtomicInteger();
        chunkWithDuplicates.incrementAndGet();

        while (chunkWithDuplicates.intValue() != 0) {
            chunkWithDuplicates.set(0);
            extractAndTransform(chunkWithDuplicates);
            totalRecordsSize = totalHandledRecords.longValue();
        }
        System.out.println("\rTransformation....Done");
    }

    public void extractAndTransform(AtomicInteger chunkWithDuplicatesCounter) {

        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(taskQueueSize);
        final ExecutorService executor = new ThreadPoolExecutor(1, poolSize, 0L, TimeUnit.MILLISECONDS, queue);

        final List<TaskInfo> currentProcessingState = taskInfoDao.getOrderedByStart();
        final Long prevRecordId;

        if (CollectionUtils.isEmpty(currentProcessingState)) {
            prevRecordId = 0l;
        } else {
            System.out.println("WARN. Transformation resumed.");
            resumeTasks(executor, currentProcessingState, chunkWithDuplicatesCounter);
            prevRecordId = currentProcessingState.get(currentProcessingState.size() - 1).getEndIndex();
        }

        try {
            List<Flight> chunk = flightDao.getNextChunk(prevRecordId, chunkSize);
            int tmpChunkSize = chunk.size();
            final long chunkAmount = totalRecordsSize / chunkSize;
            String progressAnimation = "|/-\\";
            int animationCounter = 0;
            while (tmpChunkSize > 0) {
                Long startIndex = chunk.get(tmpChunkSize - 1).getId();
                executor.submit(new ScrapperTask(flightDao, taskInfoDao, chunk, null, totalHandledRecords, chunkWithDuplicatesCounter, taskTime));
                chunk = flightDao.getNextChunk(startIndex, chunkSize);
                tmpChunkSize = chunk.size();

                while (queue.size() == taskQueueSize) {
                    Thread.sleep(ESTIMATED_TIME_TO_COMPLETE_ONE_TASK_MILIS);
                }
                if (tmpChunkSize > 0) {
                    long timeEstimation = chunkAmount * taskTime.get();

                    final String estimationMessage = String.format("Estimated time: %02d min.",
                            TimeUnit.MILLISECONDS.toMinutes(timeEstimation));

                    System.out.print("\r" + estimationMessage + " Transformation..." + progressAnimation.charAt(animationCounter % progressAnimation.length()));
                    animationCounter++;
                }
            }

        } catch (InterruptedException ex) {
            System.out.println("Someone tries to stop me => " + ex.getMessage());
        }
        finally {
            executor.shutdown();
        }
        boolean done = false;
        try {
            done = executor.awaitTermination((poolSize + taskQueueSize) * ESTIMATED_TIME_TO_COMPLETE_ONE_TASK_MILIS, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            System.out.println("Await termination Interrupted => " + ex.getMessage());
            executor.shutdownNow();
        }
        if (!done) {
            System.out.println("Application stopped by timeout. Some tasks stay inProgress. Exit.");
        }
    }
    private void resumeTasks(ExecutorService executor, List<TaskInfo> currentProcessingState, AtomicInteger chunkWithDuplicatesCounter) {
        for(TaskInfo taskInfo : currentProcessingState) {
            List<Flight> chunk = null;
            if (TransformTaskStatus.TRANSFORM.equals(taskInfo.getTaskStage())) {
                chunk = flightDao.getNextChunk(taskInfo.getStartIndex(), chunkSize);
                taskInfo.setTaskStage(TransformTaskStatus.EXTRACT);
            }
            executor.submit(new ScrapperTask(flightDao, taskInfoDao, chunk, taskInfo, totalHandledRecords, chunkWithDuplicatesCounter, taskTime));
        }

    }

}
