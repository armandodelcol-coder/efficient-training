package br.com.armando.efficienttraining.api.model.input;

import br.com.armando.efficienttraining.domain.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

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

}
