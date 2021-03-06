package com.danil.etl.task;

import com.danil.etl.converter.FlightToDestinationData;
import com.danil.etl.dao.AbstractObjectDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.DestinationData;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.entity.TransformTaskStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class LoaderTask implements Runnable {

    private final TaskInfoDao taskInfoDao;
    private final List<Flight> chunk;
    private final AtomicLong taskTime;
    private final AbstractObjectDao destinationDataDao;
    private TaskInfo taskInfo;

    public LoaderTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao,
                      List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicLong taskTime, int scheduledChunkSize,
                      long totalHandledRecords, int iteration) {
        this.taskInfoDao = taskInfoDao;
        this.chunk = chunk;
        this.taskTime = taskTime;
        this.destinationDataDao = destinationDataDao;

        if (taskInfoPrevRun != null) {
            this.taskInfo = taskInfoPrevRun;
        } else {
            final TaskInfo newTaskInfo = new TaskInfo();
            newTaskInfo.setTaskStage(TransformTaskStatus.EXTRACT);
            newTaskInfo.setStartIndex(chunk.get(0).getId());
            newTaskInfo.setEndIndex(chunk.get(chunk.size() - 1).getId());
            newTaskInfo.setTaskType(this.getClass().getSimpleName());
            newTaskInfo.setChunkSize(scheduledChunkSize);
            newTaskInfo.setTotalHandledRecords(totalHandledRecords + chunk.size());
            newTaskInfo.setIteration(iteration);
            this.taskInfo = newTaskInfo;
        }
    }

    @Override
    public void run() {
        try {
            final long startTime = System.currentTimeMillis();
            TransformTaskStatus taskStage = this.taskInfo.getTaskStage();
            if (TransformTaskStatus.EXTRACT.equals(taskStage) && CollectionUtils.isEmpty(chunk)) {
                return;
            }
            final FlightToDestinationData converter = new FlightToDestinationData();
            final List<DestinationData> destinationData = converter.convert(chunk);
            destinationDataDao.persistAll(destinationData);
            changeTaskStatus(TransformTaskStatus.LOAD);
            final long endTime = System.currentTimeMillis();
            final long totalTimeInMilis = endTime - startTime;
            this.taskTime.set(totalTimeInMilis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void changeTaskStatus(TransformTaskStatus taskStage) {
        this.taskInfo.setTaskStage(taskStage);
        taskInfoDao.persist(this.taskInfo);
    }
}
