package com.danil.etl.sheduler;

import com.danil.etl.dao.AenaflightDao;
import com.danil.etl.dao.AenaflightTmpDao;
import com.danil.etl.entity.Aenaflight;
import com.danil.etl.task.ScrapperTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Component
public class TaskSheduler {

    @Autowired
    private AenaflightDao aenaflightDao;
    @Autowired
    private AenaflightTmpDao aenaflightTmpDao;

    @Value("${thread.pool.size}")
    private int poolSize;

    @Value("${task.queue.size}")
    private int taskQueueSize;

    @Value("${chunk.size}")
    private int chunkSize;

    public void start() {

        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(taskQueueSize);
        final ExecutorService executor = new ThreadPoolExecutor(1, poolSize, 0L, TimeUnit.MILLISECONDS, queue);

        try {
            List<Aenaflight> chunk = aenaflightDao.getNextChunk(0l, chunkSize);

            while (chunk.size() == chunkSize) {
                executor.submit(new ScrapperTask(aenaflightTmpDao, chunk));
                chunk = aenaflightDao.getNextChunk(chunk.get(chunk.size() - 1).getId(), chunkSize);
                if (queue.size() == taskQueueSize) {
                    while (queue.size() == taskQueueSize) {
                        Thread.sleep(100);
                    }
                }
            }

        } catch (InterruptedException ex){
            System.out.println("Someone tries to stop me => " + ex.getMessage());
        } finally {
            executor.shutdown();
        }

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            System.out.println("Await termination Interrupted => " + ex.getMessage());
        }
    }

}
