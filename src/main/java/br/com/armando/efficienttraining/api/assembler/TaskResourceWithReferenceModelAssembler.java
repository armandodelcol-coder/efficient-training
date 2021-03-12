package br.com.armando.efficienttraining.api.assembler;

import br.com.armando.efficienttraining.api.model.TaskResourceWithReferencesModel;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskResourceWithReferenceModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskResourceWithReferencesModel toModel(TaskResource taskResource) {
        return modelMapper.map(taskResource, TaskResourceWithReferencesModel.class);
    }

}
