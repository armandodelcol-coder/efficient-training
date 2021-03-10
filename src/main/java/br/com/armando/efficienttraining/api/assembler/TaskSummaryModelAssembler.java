package br.com.armando.efficienttraining.api.assembler;

import br.com.armando.efficienttraining.api.model.TaskSummaryModel;
import br.com.armando.efficienttraining.domain.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskSummaryModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TaskSummaryModel toModel(Task task) {
        return modelMapper.map(task, TaskSummaryModel.class);
    }

    public List<TaskSummaryModel> toCollectionModel(List<Task> tasks) {
        return tasks.stream()
                .map(task -> toModel(task))
                .collect(Collectors.toList());
    }

}
