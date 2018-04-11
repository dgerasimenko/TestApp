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
        while (needMoreIterations.get()) {
            needMoreIterations.set(false);
            execute(needMoreIterations);
            totalRecordsSize = mergedRecords.longValue();
        }
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

    public Long resumeTasks(ExecutorService executor, List<TaskInfo> currentProcessingState, AtomicBoolean needMoreIterations) {
        System.out.println("WARN. Loading resumed.");
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
