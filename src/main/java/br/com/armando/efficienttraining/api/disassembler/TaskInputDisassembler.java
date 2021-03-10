package br.com.armando.efficienttraining.api.disassembler;

import br.com.armando.efficienttraining.api.model.input.TaskInput;
import br.com.armando.efficienttraining.domain.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Task toDomainObject(TaskInput taskInput) {
        return modelMapper.map(taskInput, Task.class);
    }

    public void copyToDomainObject(TaskInput taskInput, Task task) {
        modelMapper.map(taskInput, task);
    }

}
