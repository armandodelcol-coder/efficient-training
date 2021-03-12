package br.com.armando.efficienttraining.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class TaskModel {

    private Long id;
    private String name;
    private String description;
    private Integer complexityLevel;
    private String observation;
    private OffsetDateTime createdAt;
    private String status;
    private List<TaskResourceModel> resources;

}
