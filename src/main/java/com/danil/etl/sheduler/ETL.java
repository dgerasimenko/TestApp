package com.danil.etl.sheduler;

import com.danil.etl.dao.AenaflightDao;
import com.danil.etl.dao.DestinationDataDao;
import com.danil.etl.dao.AenaflightTmpDao;
import com.danil.etl.entity.Aenaflight;
import com.danil.etl.entity.AenaflightTmp;
import com.danil.etl.entity.DestinationData;
import com.danil.etl.task.ScrapperTask;
import com.danil.etl.task.collector.TmpFlightCollector;
import com.danil.etl.task.converter.FlightTmpToFlight;
import com.danil.etl.task.converter.FlightToDestinationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;
import java.util.concurrent.*;

@Component
public class ETL {

    private final int ESTIMATED_TIME_TO_COMPLETE_ONE_TASK_MILIS = 200;
    @Autowired
    private AenaflightDao aenaflightDao;
    @Autowired
    private AenaflightTmpDao aenaflightTmpDao;
    @Autowired
    private DestinationDataDao destinationDataDao;

    @Value("${thread.pool.size}")
    private int poolSize;

    @Value("${task.queue.size}")
    private int taskQueueSize;

    @Value("${chunk.size}")
    private int chunkSize;

    public void doJob() {
        System.out.println("Starting...");
        final long startTime = System.currentTimeMillis();
        int exitCode = extractAndTransform();
        if (exitCode == 0) {
            load(rollUpTmpReords());
            System.out.println(".Done.");
        } else {
            System.out.println("WARN. Transformation interrupted! Please start jar to resume transformation.");
        }
        final long endTime = System.currentTimeMillis();
        final long totalTimeInMilis = endTime - startTime;
        System.out.println(
                String.format("Total spent time: %02d min %02d sec",
                        TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis),
                        TimeUnit.MILLISECONDS.toSeconds(totalTimeInMilis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis))));
    }

    /**
     * Extract data from source table by chanks and transform in parallel.
     * Each task save rolled up/transformed data to temporary table aenaflight_tmp.
     * @return 0 in case of success
     */
    public int extractAndTransform() {
        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(taskQueueSize);
        final ExecutorService executor = new ThreadPoolExecutor(1, poolSize, 0L, TimeUnit.MILLISECONDS, queue);

        final List<AenaflightTmp> currentProcessingState = aenaflightTmpDao.getOrderedByStart();
        final Long prevRecordId;
        if (CollectionUtils.isEmpty(currentProcessingState)) {
            prevRecordId = 0l;
        } else {
            System.out.println("WARN. Transformation resumed.");
            prevRecordId = currentProcessingState.get(currentProcessingState.size() - 1).getEndIndex();
        }

        try {
            List<Aenaflight> chunk = aenaflightDao.getNextChunk(prevRecordId, chunkSize);
            int tmpChunkSize = chunk.size();
            String progressAnimation = "|/-\\";
            int animationCounter = 0;
            while (tmpChunkSize > 0) {
                Long startIndex = chunk.get(tmpChunkSize - 1).getId();
                executor.submit(new ScrapperTask(aenaflightTmpDao, chunk));
                chunk = aenaflightDao.getNextChunk(startIndex, chunkSize);
                tmpChunkSize = chunk.size();

                while (queue.size() == taskQueueSize) {
                    Thread.sleep(ESTIMATED_TIME_TO_COMPLETE_ONE_TASK_MILIS);
                }
                if (tmpChunkSize > 0) {
                    System.out.print("\rTransformation..." + progressAnimation.charAt(animationCounter % progressAnimation.length()));
                    animationCounter++;
                } else {
                    System.out.print("\rTransformation....");
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Someone tries to stop me => " + ex.getMessage());
        } finally {
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
            return -1;
        }
        return 0;
    }

    /**
     * Rollup temporary flight records from tmp table. Clear source table and tmp table.
     * Insert cleared record to source table.
     *
     * @return cleared record
     */
    private Aenaflight rollUpTmpReords() {
        final List<AenaflightTmp> tmpFlights = aenaflightTmpDao.getOrderedByStart();
        final TmpFlightCollector collector = new TmpFlightCollector();
        final AenaflightTmp finalTmpFlight = collector.collect(tmpFlights);
        final FlightTmpToFlight flightTmpToFlight = new FlightTmpToFlight();
        final Aenaflight finalFlight = flightTmpToFlight.convert(finalTmpFlight);
dumpFinalrecord(finalFlight);
        aenaflightDao.deleteAll();
        aenaflightDao.save(finalFlight);
        aenaflightTmpDao.deleteAll();
        return finalFlight;
    }

    private void dumpFinalrecord(Aenaflight finalFlight) {

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("d:/dumpFinalRecord.txt", true)));
            out.println(finalFlight.toString());
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }

    /**
     * Load processed information into destination table.
     *
     * @param rolledUpRecord
     */
    private void load(Aenaflight rolledUpRecord) {
        final FlightToDestinationData sourceConverter = new FlightToDestinationData();
        final DestinationData destinationData = sourceConverter.convert(rolledUpRecord);

        destinationDataDao.save(destinationData);
    }
}
