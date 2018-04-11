package com.danil.etl.sheduler.loader;

import com.danil.etl.dao.AbstractObjectDao;
import com.danil.etl.dao.DestinationDataDao;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.dao.TaskInfoDao;
import com.danil.etl.entity.Flight;
import com.danil.etl.entity.TaskInfo;
import com.danil.etl.sheduler.AbstractScheduler;
import com.danil.etl.task.LoaderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class LoadScheduler extends AbstractScheduler {

    @Override
    public void schedule() {
        System.out.println("Loading.... Started");
        execute(null, 1);
        taskInfoDao.deleteAll();
        System.out.println("\rLoading.... Done");
    }

    @Override
    public Long resumeTasks(ExecutorService executor, AtomicBoolean needMoreIterations) {
        System.out.println("WARN. Loading resumed.");

        final List<TaskInfo> currentProcessingState = taskInfoDao.getTaskInfo(getTaskType());
        if (CollectionUtils.isEmpty(currentProcessingState)) {
            return 0l;
        } else {
            return currentProcessingState.get(currentProcessingState.size() - 1).getEndIndex();
        }
    }

    @Override
    public Runnable getTask(AbstractObjectDao destinationDataDao, TaskInfoDao taskInfoDao, List<Flight> chunk, TaskInfo taskInfoPrevRun, AtomicLong taskTime, AtomicBoolean needMoreIterations) {
        return new LoaderTask(destinationDataDao, taskInfoDao, chunk, taskInfoPrevRun, taskTime);
    }

    @Override
    public Class getTaskType() {
        return LoaderTask.class;
    }
}
