package br.com.armando.efficienttraining.api.assembler;

import br.com.armando.efficienttraining.api.model.TaskResourceReferenceModel;
import br.com.armando.efficienttraining.domain.model.TaskResourceReference;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskResourceReferenceModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskResourceReferenceModel toModel(TaskResourceReference taskResourceReference) {
        return modelMapper.map(taskResourceReference, TaskResourceReferenceModel.class);
    }

    public List<TaskResourceReferenceModel> toCollectionModel(List<TaskResourceReference> taskResourceReferences) {
        return taskResourceReferences.stream()
                .map(taskResourceReference -> toModel(taskResourceReference))
                .collect(Collectors.toList());
    }

}
