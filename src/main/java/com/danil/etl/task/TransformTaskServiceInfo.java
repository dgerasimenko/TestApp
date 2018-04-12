package com.danil.etl.task;

import com.danil.etl.entity.Flight;

import java.util.List;

public class TransformTaskServiceInfo {
    private final List<Flight> mergedRecords;
    private final List<Long> recordIdsToBeDeleted;

    public TransformTaskServiceInfo(List<Flight> mergedRecords, List<Long> recordIdsToBeDeleted) {
        this.mergedRecords = mergedRecords;
        this.recordIdsToBeDeleted = recordIdsToBeDeleted;
    }

    public List<Flight> getMergedRecords() {
        return mergedRecords;
    }

    public List<Long> getRecordIdsToBeDeleted() {
        return recordIdsToBeDeleted;
    }
}
