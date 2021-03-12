package br.com.armando.efficienttraining.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskResourceWithReferencesModel {

    private Long id;
    private String name;
    private String description;
    private String status;
    private TaskSummaryModel task;
    private List<TaskResourceReferenceModel> references;

}
