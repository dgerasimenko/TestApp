package com.danil.etl.task;

public class TaskServiceInfoHolder {
    private int chunkSize;
    private long prevRecordId;
    private Long totalHandledRecords;
    private int iteration;

    public TaskServiceInfoHolder(int chunkSize, long prevRecordId) {
        this.chunkSize = chunkSize;
        this.prevRecordId = prevRecordId;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public long getPrevRecordId() {
        return prevRecordId;
    }

    public void setPrevRecordId(long prevRecordId) {
        this.prevRecordId = prevRecordId;
    }

    public Long getTotalHandledRecords() {
        return totalHandledRecords;
    }

    public void setTotalHandledRecords(Long totalHandledRecords) {
        this.totalHandledRecords = totalHandledRecords;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}
