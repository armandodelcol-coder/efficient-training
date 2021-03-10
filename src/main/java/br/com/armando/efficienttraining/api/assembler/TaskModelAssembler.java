package br.com.armando.efficienttraining.api.assembler;

import br.com.armando.efficienttraining.api.model.TaskModel;
import br.com.armando.efficienttraining.domain.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskModel toModel(Task task) {
        return modelMapper.map(task, TaskModel.class);
    }

    public List<TaskModel> toCollectionModel(List<Task> tasks) {
        return tasks.stream()
                .map(task -> toModel(task))
                .collect(Collectors.toList());
    }

}
