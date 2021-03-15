package br.com.armando.efficienttraining.api.disassembler;

import br.com.armando.efficienttraining.api.model.input.TaskResourceReferenceInput;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import br.com.armando.efficienttraining.domain.model.TaskResourceReference;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskResourceReferenceInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskResourceReference toDomainObject(TaskResourceReferenceInput taskResourceReferenceReferenceInput) {
        return modelMapper.map(taskResourceReferenceReferenceInput, TaskResourceReference.class);
    }

    public void copyToDomainObject(TaskResourceReferenceInput taskResourceReferenceReferenceInput, TaskResourceReference taskResourceReference) {
        taskResourceReference.setTaskResource(new TaskResource());

        modelMapper.map(taskResourceReferenceReferenceInput, taskResourceReference);
    }

}
