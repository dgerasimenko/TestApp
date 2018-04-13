package com.danil.etl.sheduler.transformer;

import com.danil.etl.dao.*;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.entity.TransformTaskStatus;
import com.danil.etl.sheduler.AbstractScheduler;
import com.danil.etl.task.TaskServiceInfoHolder;
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

    private static final String WARN_MESSAGE = "WARN. Transformation resumed.";

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

    @Override
    public int transform() {
        System.out.println("Transformation. Started");
        final AtomicBoolean needMoreIterations = new AtomicBoolean();
        int exitCode = 0;
        int tmpChunkSize = this.chunkSize;
        long totalRecordsSize = flightDao.getApproximatedRowsCount();
        while(notAllRecordsTransformed(tmpChunkSize / 2, totalRecordsSize)) {
            final TaskServiceInfoHolder taskServiceInfoHolder = new TaskServiceInfoHolder(tmpChunkSize, 0l, 0l, 0);
            needMoreIterations.set(true);
            while (needMoreIterations.get()) {
                final int iteration = taskServiceInfoHolder.getIteration();
                taskServiceInfoHolder.setIteration(iteration + 1);
                taskServiceInfoHolder.setTotalHandledRecords(0l);
                taskServiceInfoHolder.setPrevRecordId(0l);
                needMoreIterations.set(false);

                exitCode = execute(needMoreIterations, iteration, taskServiceInfoHolder);

                if (exitCode == 0) {
                    taskInfoDao.deleteAll();
                } else {
                    break;
                }
            }
            if (exitCode != 0) {
                break;
            }
            tmpChunkSize = taskServiceInfoHolder.getChunkSize() * 2;
        }
        if (exitCode == 0) {
            System.out.println("Transformation. Done");
        } else {
            System.out.println("Transformation. Failed");
        }
        return exitCode;

    }

    private boolean notAllRecordsTransformed(int chunkSize, long totalRecordsSize) {
        return chunkSize < totalRecordsSize;
    }

    @Override
    public Runnable getTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao, List<Flight> chunk,
                            TaskInfo taskInfoPrevRun, AtomicLong taskTime, AtomicBoolean needMoreIterations, long totalHandledRecords, int iteration) {
        return new TransformerTask(flightDao, taskInfoDao, chunk, taskInfoPrevRun, taskTime,
                needMoreIterations, this.chunkSize, totalHandledRecords, iteration);
    }

    @Override
    public Class getTaskType() {
        return TransformerTask.class;
    }

    @Override
    public void resumeTasks(ExecutorService executor, TaskServiceInfoHolder serviceInfoHolder, AtomicBoolean needMoreIterations, int defaultIteration) {
        List<TaskInfo> taskInfos = taskInfoDao.getTaskInfoByStatus(getTaskType(), Arrays.asList(TransformTaskStatus.TRANSFORM, TransformTaskStatus.INSERT_ONE_RECORD));
        if (CollectionUtils.isEmpty(taskInfos)) {
            taskInfos = taskInfoDao.getTaskInfoByStatus(getTaskType(), Arrays.asList(TransformTaskStatus.DELETE_DUPLICATES));
            if (!CollectionUtils.isEmpty(taskInfos)){
                System.out.println(WARN_MESSAGE);

                final TaskInfo lastTaskInfo = taskInfos.get(taskInfos.size() - 1);
                final List<Flight> chunk = flightDao.getNextChunk(lastTaskInfo.getEndIndex(), chunkSize);

                executor.submit(getTask(flightDao, taskInfoDao, chunk, null, taskTime, needMoreIterations,
                        lastTaskInfo.getTotalHandledRecords(), lastTaskInfo.getIteration()));

                serviceInfoHolder.setPrevRecordId(lastTaskInfo.getEndIndex());
                serviceInfoHolder.setIteration(lastTaskInfo.getIteration());
                serviceInfoHolder.setTotalHandledRecords(lastTaskInfo.getTotalHandledRecords());
            }
        } else {
            System.out.println(WARN_MESSAGE);
            for (TaskInfo taskInfo : taskInfos) {
                final List<Flight> chunk = flightDao.getNextChunk(taskInfo.getStartIndex(), chunkSize);
                    taskInfo.setTaskStage(TransformTaskStatus.EXTRACT);
               executor.submit(getTask(flightDao, taskInfoDao, chunk, taskInfo, taskTime, needMoreIterations, taskInfo.getTotalHandledRecords(), taskInfo.getIteration()));
            }
            final TaskInfo lastTaskInfo = taskInfos.get(taskInfos.size() - 1);
            serviceInfoHolder.setPrevRecordId(lastTaskInfo.getEndIndex());
            serviceInfoHolder.setIteration(lastTaskInfo.getIteration());
            serviceInfoHolder.setTotalHandledRecords(lastTaskInfo.getTotalHandledRecords());
        }
    }
}
