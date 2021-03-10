package br.com.armando.efficienttraining.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProjectInput {

    @NotBlank
    private String name;

    private String description;

}
