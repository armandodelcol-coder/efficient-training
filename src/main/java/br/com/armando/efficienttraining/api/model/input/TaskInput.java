package br.com.armando.efficienttraining.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskInput {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Max(value = 3)
    @Min(value = 1)
    @NotNull
    private Integer complexityLevel;

    private String observation;

    @Valid
    @NotNull
    private ProjectIdInput project;

}
