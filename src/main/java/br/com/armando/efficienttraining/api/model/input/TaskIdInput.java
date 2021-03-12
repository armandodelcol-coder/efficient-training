package br.com.armando.efficienttraining.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskIdInput {

    @NotNull
    private Long id;

}
