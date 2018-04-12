package com.danil.etl.entity;

import javax.persistence.*;

@Entity
@Table(name = "task_info")
public class TaskInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "start_index")
    private Long startIndex;
    @Column(name = "end_index")
    private Long endIndex;
    @Column(name = "task_type")
    private String taskType;
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TransformTaskStatus taskStage;
    @Column(name = "service_information")
    private String serviceInformation;
    @Column(name = "total_handled_records")
    private Long totalHandledRecords;
    @Column(name = "chunk_size")
    private int chunkSize;
    @Column(name = "iteration")
    private int iteration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    public Long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Long endIndex) {
        this.endIndex = endIndex;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public TransformTaskStatus getTaskStage() {
        return taskStage;
    }

    public void setTaskStage(TransformTaskStatus taskStage) {
        this.taskStage = taskStage;
    }

    public String getServiceInformation() {
        return serviceInformation;
    }

    public void setServiceInformation(String serviceInformation) {
        this.serviceInformation = serviceInformation;
    }

    public Long getTotalHandledRecords() {
        return totalHandledRecords;
    }

    public void setTotalHandledRecords(Long totalHandledRecords) {
        this.totalHandledRecords = totalHandledRecords;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}
