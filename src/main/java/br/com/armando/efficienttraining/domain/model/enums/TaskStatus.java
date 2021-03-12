package br.com.armando.efficienttraining.domain.model.enums;

import java.util.Arrays;
import java.util.List;

public enum TaskStatus {

    TO_DO("To-do"),
    DOING("Doing", TO_DO),
    DONE("Done", DOING);

    private String description;
    private List<TaskStatus> taskStatusList;

    TaskStatus(String description, TaskStatus... taskStatusList) {
        this.description = description;
        this.taskStatusList = Arrays.asList(taskStatusList);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean cantChangeStatusTo(TaskStatus newStatus) {
        return !newStatus.taskStatusList.contains(this);
    }

}
