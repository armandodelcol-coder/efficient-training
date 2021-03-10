package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.TaskModelAssembler;
import br.com.armando.efficienttraining.api.assembler.TaskSummaryModelAssembler;
import br.com.armando.efficienttraining.api.disassembler.TaskInputDisassembler;
import br.com.armando.efficienttraining.api.model.TaskModel;
import br.com.armando.efficienttraining.api.model.TaskSummaryModel;
import br.com.armando.efficienttraining.api.model.input.TaskInput;
import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.TaskRepository;
import br.com.armando.efficienttraining.domain.service.ProjectRegisterService;
import br.com.armando.efficienttraining.domain.service.TaskRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class ProjectTaskController {

    @Autowired
    private ProjectRegisterService projectRegisterService;

    @Autowired
    private TaskSummaryModelAssembler taskSummaryModelAssembler;

    @Autowired
    private TaskRegisterService taskRegisterService;

    @Autowired
    private TaskModelAssembler taskModelAssembler;

    @Autowired
    private TaskInputDisassembler taskInputDisassembler;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskSummaryModel> list(@PathVariable Long projectId) {
        Project project = projectRegisterService.findByIdOrFail(projectId);
        return taskSummaryModelAssembler.toCollectionModel(project.getTasks());
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskModel findById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskModelAssembler.toModel(taskRegisterService.findByIdOrFail(projectId, taskId));
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskModel insert(@PathVariable Long projectId, @RequestBody @Valid TaskInput taskInput) {
        Project project = projectRegisterService.findByIdOrFail(projectId);
        Task task = taskInputDisassembler.toDomainObject(taskInput);
        task.setProject(project);
        return taskModelAssembler.toModel(taskRepository.save(task));
    }

    @Transactional
    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskModel update(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody @Valid TaskInput taskInput
    ) {
        Task taskToUpdate = taskRegisterService.findByIdOrFail(projectId, taskId);
        taskInputDisassembler.copyToDomainObject(taskInput, taskToUpdate);
        return taskModelAssembler.toModel(taskRepository.save(taskToUpdate));
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskRegisterService.deleteById(projectId, taskId);
    }

}
