package br.com.armando.efficienttraining.api.disassembler;

import br.com.armando.efficienttraining.api.model.input.TaskResourceInput;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskResourceInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskResource toDomainObject(TaskResourceInput taskResourceInput) {
        return modelMapper.map(taskResourceInput, TaskResource.class);
    }

    public void copyToDomainObject(TaskResourceInput taskResourceInput, TaskResource taskResource) {
        taskResource.setTask(new Task());

        modelMapper.map(taskResourceInput, taskResource);
    }

}
