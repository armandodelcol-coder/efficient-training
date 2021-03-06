package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.TaskSummaryModelAssembler;
import br.com.armando.efficienttraining.api.model.TaskSummaryModel;
import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.repository.TaskRepository;
import br.com.armando.efficienttraining.domain.service.ProjectRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class ProjectTaskController {

    @Autowired
    private ProjectRegisterService projectRegisterService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskSummaryModelAssembler taskSummaryModelAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskSummaryModel> list(@PathVariable Long projectId) {
        Project project = projectRegisterService.findByIdOrFail(projectId);
        return taskSummaryModelAssembler.toCollectionModel(taskRepository.findAllByProject(project));
    }

}
