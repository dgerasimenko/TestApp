package com.danil.etl.entity;


public enum TaskStage {
    LOAD(null),
    DELETE_DUPLICATES(LOAD),
    INSERT_ONE_RECORD(DELETE_DUPLICATES),
    TRANSFORM(INSERT_ONE_RECORD),
    EXTRACT(TRANSFORM);

    private final TaskStage nextStage;
    private final boolean hasNext;

    TaskStage(TaskStage nextStage) {
        this.nextStage = nextStage;
        this.hasNext = nextStage != null;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public TaskStage next() {
        return nextStage;
    }

}
