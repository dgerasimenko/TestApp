package com.danil.etl.entity;


public enum TransformTaskStatus {
    LOAD(null),
    DELETE_DUPLICATES(null),
    INSERT_ONE_RECORD(DELETE_DUPLICATES),
    TRANSFORM(INSERT_ONE_RECORD),
    EXTRACT(TRANSFORM);

    private final TransformTaskStatus nextStage;
    private final boolean hasNext;

    TransformTaskStatus(TransformTaskStatus nextStage) {
        this.nextStage = nextStage;
        this.hasNext = nextStage != null;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public TransformTaskStatus next() {
        return nextStage;
    }

}
