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
}
