package com.danil.etl.task;

import com.danil.etl.dao.AenaflightTmpDao;
import com.danil.etl.entity.Aenaflight;
import com.danil.etl.task.collector.FlightCollector;

import java.util.List;

public class ScrapperTask implements Runnable {

    private final AenaflightTmpDao aenaflightTmpDao;
    private final List<Aenaflight> chunk;

    public ScrapperTask(AenaflightTmpDao aenaflightTmpDao, List<Aenaflight> chunk) {
        this.aenaflightTmpDao = aenaflightTmpDao;
        this.chunk = chunk;
    }

    @Override
    public void run() {
        try {
            final FlightCollector flightCollector = new FlightCollector();
            aenaflightTmpDao.save(flightCollector.collect(chunk));
        } catch (Exception ex) {
            System.out.println("Exception happened in Scrapper Task. start_index=" + chunk.get(0).getId() + " end_index=" + chunk.get(chunk.size() - 1).getId());
        }
    }
}
