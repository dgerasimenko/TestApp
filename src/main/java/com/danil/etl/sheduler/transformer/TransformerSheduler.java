package com.danil.etl.sheduler.transformer;

import com.danil.etl.dao.*;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.entity.TransformTaskStatus;
import com.danil.etl.sheduler.AbstractScheduler;
import com.danil.etl.task.TransformerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TransformerSheduler extends AbstractScheduler {

    private final AtomicLong taskTime = new AtomicLong();
    private final AtomicLong mergedRecords = new AtomicLong();

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

    private long totalRecordsSize;

    @Override
    public void schedule() {
        System.out.println("Transformation.... Started");
        final AtomicBoolean needMoreIterations = new AtomicBoolean();
        needMoreIterations.set(true);
        totalRecordsSize = flightDao.getApproximatedRowsCount();
        int iteration = 0;
        while (needMoreIterations.get()) {
            iteration++;
            needMoreIterations.set(false);
            execute(needMoreIterations, iteration);
            totalRecordsSize = mergedRecords.longValue();
        }
        taskInfoDao.deleteAll();
        System.out.println("\rTransformation.... Done");
    }

    @Override
    public Runnable getTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao, List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicLong taskTime, AtomicBoolean needMoreIterations) {
        return new TransformerTask(flightDao, taskInfoDao, chunk, taskInfoPrevRun, taskTime, needMoreIterations);
    }

    @Override
    public Class getTaskType() {
        return TransformerTask.class;
    }

    @Override
    public Long resumeTasks(ExecutorService executor, AtomicBoolean needMoreIterations) {
        System.out.println("WARN. Transformation resumed.");

        List<TaskInfo> currentProcessingState = taskInfoDao.getTaskInfoByStatus(getTaskType(), Arrays.asList(TransformTaskStatus.TRANSFORM));
        if (CollectionUtils.isEmpty(currentProcessingState)) {
            currentProcessingState = taskInfoDao.getTaskInfoByStatus(getTaskType(), Arrays.asList(TransformTaskStatus.DELETE_DUPLICATES));
            if (CollectionUtils.isEmpty(currentProcessingState)) {
                return 0l;
            } else {
                return currentProcessingState.get(currentProcessingState.size() - 1).getEndIndex();
            }
        } else {

            for (TaskInfo taskInfo : currentProcessingState) {
                List<Flight> chunk = null;
                if (TransformTaskStatus.TRANSFORM.equals(taskInfo.getTaskStage())) {
                    chunk = flightDao.getNextChunk(taskInfo.getStartIndex(), chunkSize);
                    taskInfo.setTaskStage(TransformTaskStatus.EXTRACT);
                }
                executor.submit(getTask(flightDao, taskInfoDao, chunk, taskInfo, taskTime, needMoreIterations));
            }
            return currentProcessingState.get(currentProcessingState.size() - 1).getEndIndex();
        }
    }
}
