package br.com.armando.efficienttraining.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResourceModel {

    private Long id;
    private String name;
    private String description;
    private String status;

}
