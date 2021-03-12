package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.TaskModelAssembler;
import br.com.armando.efficienttraining.api.assembler.TaskSummaryModelAssembler;
import br.com.armando.efficienttraining.api.disassembler.TaskInputDisassembler;
import br.com.armando.efficienttraining.api.model.TaskModel;
import br.com.armando.efficienttraining.api.model.TaskSummaryModel;
import br.com.armando.efficienttraining.api.model.input.TaskInput;
import br.com.armando.efficienttraining.domain.exception.BusinessException;
import br.com.armando.efficienttraining.domain.exception.ResourceNotFoundException;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.TaskRepository;
import br.com.armando.efficienttraining.domain.service.TaskRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

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
    public List<TaskSummaryModel> list() {
        return taskSummaryModelAssembler.toCollectionModel(taskRepository.findAll());
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskModel findById(@PathVariable Long taskId) {
        return taskModelAssembler.toModel(taskRegisterService.findByIdOrFail(taskId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskModel insert(@RequestBody @Valid TaskInput taskInput) {
        try {
            Task task = taskInputDisassembler.toDomainObject(taskInput);
            return taskModelAssembler.toModel(taskRegisterService.save(task));
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskModel update(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskInput taskInput
    ) {
        Task taskToUpdate = taskRegisterService.findByIdOrFail(taskId);
        taskInputDisassembler.copyToDomainObject(taskInput, taskToUpdate);
        try {
            return taskModelAssembler.toModel(taskRegisterService.save(taskToUpdate));
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long taskId) {
        taskRegisterService.deleteById(taskId);
    }



}
