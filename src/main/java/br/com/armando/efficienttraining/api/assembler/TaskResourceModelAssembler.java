package br.com.armando.efficienttraining.api.assembler;

import br.com.armando.efficienttraining.api.model.TaskResourceModel;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskResourceModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskResourceModel toModel(TaskResource taskResource) {
        return modelMapper.map(taskResource, TaskResourceModel.class);
    }

    public List<TaskResourceModel> toCollectionModel(List<TaskResource> taskResources) {
        return taskResources.stream()
                .map(taskResource -> toModel(taskResource))
                .collect(Collectors.toList());
    }

}
