package com.danil.etl.task;

import com.danil.etl.entity.Flight;
import com.danil.etl.entity.FlightUniqueKey;

import java.util.List;
import java.util.Map;

public class TaskServiceInfo {
    private final Map<FlightUniqueKey, Flight> mergedRecords;
    private final List<Long> recordIdsToBeDeleted;

    public TaskServiceInfo(Map<FlightUniqueKey, Flight> mergedRecords, List<Long> recordIdsToBeDeleted) {
        this.mergedRecords = mergedRecords;
        this.recordIdsToBeDeleted = recordIdsToBeDeleted;
    }

    public Map<FlightUniqueKey, Flight> getMergedRecords() {
        return mergedRecords;
    }

    public List<Long> getRecordIdsToBeDeleted() {
        return recordIdsToBeDeleted;
    }
}
