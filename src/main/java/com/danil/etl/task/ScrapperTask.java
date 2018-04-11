package com.danil.etl.task;

import com.danil.etl.ChunkTransformNotNeededException;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.*;
import com.danil.etl.collector.FlightCollector;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ScrapperTask implements Runnable {
    private static final char SEPARATOR = ',';
    private FlightDao flightDao;
    private final TaskInfoDao taskInfoDao;
    private final List<Flight> chunk;
    private final AtomicInteger chunkWithDuplicates;
    private final AtomicLong taskTime;
    private final AtomicLong totalHandledRecords;

    private TaskInfo taskInfo;
    private TaskServiceInfo serviceInfo;
    private Map<TransformTaskStatus, ITaskStageProcessor> TASK_STAGE_2_PROCESSOR = new HashMap<>();

    public ScrapperTask(FlightDao flightDao, TaskInfoDao taskInfoDao, List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicLong totalHandledRecords, AtomicInteger chunkWithDuplicates, AtomicLong taskTime) {
        this.flightDao = flightDao;
        this.taskInfoDao = taskInfoDao;
        this.totalHandledRecords = totalHandledRecords;
        this.chunk = chunk;
        this.chunkWithDuplicates = chunkWithDuplicates;
        this.taskTime = taskTime;
        if (taskInfoPrevRun != null) {
            this.taskInfo = taskInfoPrevRun;
        } else {
            final TaskInfo newTaskInfo = new TaskInfo();
            newTaskInfo.setTaskStage(TransformTaskStatus.EXTRACT);
            newTaskInfo.setStartIndex(chunk.get(0).getId());
            newTaskInfo.setEndIndex(chunk.get(chunk.size() - 1).getId());
            this.taskInfo = newTaskInfo;
        }
        initTaskStrageProcessors();
    }

    private void initTaskStrageProcessors() {

        TASK_STAGE_2_PROCESSOR.put(TransformTaskStatus.TRANSFORM,
                () -> {
                    final FlightCollector collector = new FlightCollector();
                    serviceInfo = collector.orderBy(chunk);
                    if (CollectionUtils.isEmpty(serviceInfo.getRecordIdsToBeDeleted())) {
                        throw new ChunkTransformNotNeededException();
                    } else {
                        chunkWithDuplicates.incrementAndGet();
                    }
                    changeTaskStatus(TransformTaskStatus.TRANSFORM);
                });
        TASK_STAGE_2_PROCESSOR.put(TransformTaskStatus.INSERT_ONE_RECORD,
                () -> {
                    flightDao.mergeAll(serviceInfo.getMergedRecords().values());
                    taskInfo.setServiceInformation(StringUtils.join(serviceInfo.getRecordIdsToBeDeleted(), SEPARATOR));
                    changeTaskStatus(TransformTaskStatus.INSERT_ONE_RECORD);
                });
        TASK_STAGE_2_PROCESSOR.put(TransformTaskStatus.DELETE_DUPLICATES,
                () -> {
                    final List<Long> idsToBeDeleted = serviceInfo == null || CollectionUtils.isEmpty(serviceInfo.getRecordIdsToBeDeleted()) ?
                            Arrays.asList(StringUtils.split(taskInfo.getServiceInformation(), SEPARATOR)).stream().map(str -> Long.valueOf(str)).collect(Collectors.toList())
                            : serviceInfo.getRecordIdsToBeDeleted();
                    flightDao.deleteByIds(idsToBeDeleted);
                    changeTaskStatus(TransformTaskStatus.DELETE_DUPLICATES);
                });
    }

    @Override
    public void run() {
            final long startTime = System.currentTimeMillis();
            TransformTaskStatus taskStage = this.taskInfo.getTaskStage();
            if (TransformTaskStatus.EXTRACT.equals(taskStage) && CollectionUtils.isEmpty(chunk)) {
                return;
            }
            try {
                while (taskStage.hasNext()) {
                    taskStage = taskStage.next();
                    TASK_STAGE_2_PROCESSOR.get(taskStage).process();
                }
                this.totalHandledRecords.addAndGet(this.serviceInfo.getMergedRecords().size());
            } catch (ChunkTransformNotNeededException ex) {
            }
            finally {
                if (this.taskInfo.getId() != null) {
                    this.taskInfoDao.deleteById(this.taskInfo.getId());
                }
            }
            final long endTime = System.currentTimeMillis();
            final long totalTimeInMilis = endTime - startTime;
            this.taskTime.set(totalTimeInMilis);
    }

    private void changeTaskStatus(TransformTaskStatus taskStage) {
        this.taskInfo.setTaskStage(taskStage);
        this.taskInfo = taskInfoDao.merge(this.taskInfo);
    }

    private interface ITaskStageProcessor {
        void process() throws ChunkTransformNotNeededException;
    }
}
