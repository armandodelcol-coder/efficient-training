package br.com.armando.efficienttraining.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskResourceReferenceInput {

    @NotBlank
    private String link;

    @NotBlank
    private String description;

}
