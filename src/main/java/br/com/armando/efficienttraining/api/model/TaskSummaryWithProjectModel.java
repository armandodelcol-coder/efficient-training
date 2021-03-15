package br.com.armando.efficienttraining.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskSummaryWithProjectModel {

    private Long id;
    private String name;
    private String status;
    private ProjectSummaryModel project;

}
