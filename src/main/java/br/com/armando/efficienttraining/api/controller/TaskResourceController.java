package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.TaskResourceModelAssembler;
import br.com.armando.efficienttraining.api.disassembler.TaskResourceInputDisassembler;
import br.com.armando.efficienttraining.api.model.TaskResourceModel;
import br.com.armando.efficienttraining.api.model.input.TaskResourceInput;
import br.com.armando.efficienttraining.domain.exception.BusinessException;
import br.com.armando.efficienttraining.domain.exception.ResourceNotFoundException;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import br.com.armando.efficienttraining.domain.repository.TaskResourceRepository;
import br.com.armando.efficienttraining.domain.service.TaskResourceRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task-resources")
public class TaskResourceController {

    @Autowired
    TaskResourceModelAssembler taskResourceModelAssembler;

    @Autowired
    TaskResourceRepository taskResourceRepository;

    @Autowired
    TaskResourceRegisterService taskResourceRegisterService;

    @Autowired
    TaskResourceInputDisassembler taskResourceInputDisassembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResourceModel> list() {
        return taskResourceModelAssembler.toCollectionModel(taskResourceRepository.findAll());
    }

    @GetMapping("/{taskResourceId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResourceModel findById(@PathVariable Long taskResourceId) {
        return taskResourceModelAssembler.toModel(taskResourceRegisterService.findByIdOrFail(taskResourceId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResourceModel insert(@RequestBody @Valid TaskResourceInput taskResourceInput) {
        try {
            TaskResource taskResource = taskResourceInputDisassembler.toDomainObject(taskResourceInput);
            return taskResourceModelAssembler.toModel(taskResourceRegisterService.save(taskResource));
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{taskResourceId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResourceModel update(
            @PathVariable Long taskResourceId,
            @RequestBody @Valid TaskResourceInput taskResourceInput
    ) {
        TaskResource taskResourceToUpdate = taskResourceRegisterService.findByIdOrFail(taskResourceId);
        taskResourceInputDisassembler.copyToDomainObject(taskResourceInput, taskResourceToUpdate);
        try {
            return taskResourceModelAssembler.toModel(taskResourceRegisterService.save(taskResourceToUpdate));
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{taskResourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long taskResourceId) {
        taskResourceRegisterService.deleteById(taskResourceId);
    }

}
