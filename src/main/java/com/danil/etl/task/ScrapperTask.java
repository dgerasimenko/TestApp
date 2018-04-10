package com.danil.etl.task;

import com.danil.etl.ChunkTransformNotNeededException;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.*;
import com.danil.etl.collector.FlightCollector;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ScrapperTask implements Runnable {

    private FlightDao flightDao;
    private final TaskInfoDao taskInfoDao;
    private final List<Flight> chunk;
    private final AtomicInteger counter;
    private final AtomicLong taskTime;

    private TaskInfo taskInfo;
    private TaskServiceInfo serviceInfo;
    private Map<TaskStage, ITaskStageProcessor> TASK_STAGE_2_PROCESSOR = new HashMap<>();

    public ScrapperTask(FlightDao flightDao, TaskInfoDao taskInfoDao, List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicInteger counter, AtomicLong taskTime) {
        this.flightDao = flightDao;
        this.taskInfoDao = taskInfoDao;
        this.chunk = chunk;
        this.counter = counter;
        this.taskTime = taskTime;
        if (taskInfoPrevRun != null) {
            this.taskInfo = taskInfoPrevRun;
        } else {
            final TaskInfo newTaskInfo = new TaskInfo();
            newTaskInfo.setTaskStage(TaskStage.EXTRACT);
            newTaskInfo.setStartIndex(chunk.get(0).getId());
            newTaskInfo.setEndIndex(chunk.get(chunk.size() - 1).getId());
            this.taskInfo = newTaskInfo;
        }
        initTaskStrageProcessors();
    }

    private void initTaskStrageProcessors() {
        TASK_STAGE_2_PROCESSOR.put(TaskStage.TRANSFORM,
                () -> {
                    final FlightCollector collector = new FlightCollector();
                    serviceInfo = collector.orderBy(chunk);
                    if (CollectionUtils.isEmpty(serviceInfo.getRecordIdsToBeDeleted())) {
                        throw new ChunkTransformNotNeededException();
                    } else {
                        counter.incrementAndGet();
                    }
                });
        TASK_STAGE_2_PROCESSOR.put(TaskStage.INSERT_ONE_RECORD,
                () -> {
                    flightDao.persist(serviceInfo.getMergedRecords().values());
                    changeTaskStatus(TaskStage.INSERT_ONE_RECORD);
                });
        TASK_STAGE_2_PROCESSOR.put(TaskStage.DELETE_DUPLICATES,
                () -> {
                   // flightDao.deleteByIds(serviceInfo.getRecordIdsToBeDeleted());
                    changeTaskStatus(TaskStage.DELETE_DUPLICATES);
                });
        TASK_STAGE_2_PROCESSOR.put(TaskStage.LOAD,
                () -> {
                    changeTaskStatus(TaskStage.LOAD);
                });
    }

    @Override
    public void run() {
        try {

            final long startTime = System.currentTimeMillis();

            TaskStage taskStage = this.taskInfo.getTaskStage();

            if (TaskStage.EXTRACT.equals(taskStage) && CollectionUtils.isEmpty(chunk)) {
                return;
            }
            try {
                while (taskStage.hasNext()) {
                    taskStage = taskStage.next();
                    TASK_STAGE_2_PROCESSOR.get(taskStage).process();
                }
                taskInfoDao.deleteById(this.taskInfo.getId());
            } catch (ChunkTransformNotNeededException ex) {
                return;
            }

            final long endTime = System.currentTimeMillis();
            final long totalTimeInMilis = endTime - startTime;
            taskTime.set(totalTimeInMilis);
            System.out.println(
                    String.format("Total spent time: %02d min %02d sec",
                            TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis),
                            TimeUnit.MILLISECONDS.toSeconds(totalTimeInMilis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis))));
        } catch (Exception ex) {
            System.out.println("Exception happened in Scrapper Task. start_index=" + chunk.get(0).getId() + " end_index=" + chunk.get(chunk.size() - 1).getId());
            ex.printStackTrace();
        }
    }

    private void changeTaskStatus(TaskStage taskStage) {
        this.taskInfo.setTaskStage(taskStage);
        this.taskInfo = taskInfoDao.merge(this.taskInfo);
    }

    private interface ITaskStageProcessor {
        void process() throws ChunkTransformNotNeededException;
    }
}
