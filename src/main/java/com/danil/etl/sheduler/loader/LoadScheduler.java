package com.danil.etl.sheduler.loader;

import com.danil.etl.dao.AbstractObjectDao;
import com.danil.etl.dao.DestinationDataDao;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.sheduler.AbstractScheduler;
import com.danil.etl.task.LoaderTask;
import com.danil.etl.task.TaskServiceInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class LoadScheduler extends AbstractScheduler {


    @Value("${chunk.size}")
    private int chunkSize;

    private static final String WARN_MESSAGE = "WARN. Loading resumed.";

    @Override
    public int transform() {
        System.out.println("Loading. Started");
        final long startTime = System.currentTimeMillis();
        int exitCode = execute(null, 1, new TaskServiceInfoHolder(chunkSize, 0l, 0l, 0));
        if (exitCode == 0) {
            taskInfoDao.deleteAll();
            final long totalIterationTime = System.currentTimeMillis() - startTime;
            final String finishIterationMessage = String.format("Total time for %s: %02d h %02d min. chunk.size: %d",
                    getTaskType().getSimpleName(),
                    TimeUnit.MILLISECONDS.toHours(totalIterationTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalIterationTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalIterationTime)),
                    this.chunkSize);
            System.out.println("\r" + finishIterationMessage);
            System.out.println("Loading. Done");
        } else {
            System.out.println("Loading. Failed");
        }
        return exitCode;
    }

    @Override
    public void resumeTasks(ExecutorService executor, TaskServiceInfoHolder serviceInfoHolder, AtomicBoolean needMoreIterations, int defaultIteration) {

        final List<TaskInfo> taskInfos = taskInfoDao.getTaskInfo(getTaskType());
        if (!CollectionUtils.isEmpty(taskInfos)) {
            System.out.println(WARN_MESSAGE);
            final TaskInfo lastTaskInfo = taskInfos.get(taskInfos.size() - 1);
            final List<Flight> chunk = flightDao.getNextChunk(lastTaskInfo.getEndIndex(), this.chunkSize);
            executor.submit(getTask(flightDao, taskInfoDao, chunk, null, taskTime, needMoreIterations,
                    lastTaskInfo.getTotalHandledRecords(), lastTaskInfo.getIteration()));
            serviceInfoHolder.setChunkSize(lastTaskInfo.getChunkSize());
            serviceInfoHolder.setPrevRecordId(lastTaskInfo.getEndIndex());
            serviceInfoHolder.setIteration(lastTaskInfo.getIteration());
            serviceInfoHolder.setTotalHandledRecords(lastTaskInfo.getTotalHandledRecords());

            System.out.println(CHUNK_SIZE_MESSAGE + serviceInfoHolder.getChunkSize());
        }
    }

    @Override
    public Runnable getTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao, List<Flight> chunk,
                            TaskInfo taskInfoPrevRun, AtomicLong taskTime, AtomicBoolean needMoreIterations, long totalhandledRecords, int iteration) {
        return new LoaderTask(destinationDataDao, taskInfoDao, chunk, taskInfoPrevRun, taskTime, this.chunkSize, totalhandledRecords, iteration);
    }

    @Override
    public Class getTaskType() {
        return LoaderTask.class;
    }
}
