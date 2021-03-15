package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.TaskResourceReferenceModelAssembler;
import br.com.armando.efficienttraining.api.disassembler.TaskResourceReferenceInputDisassembler;
import br.com.armando.efficienttraining.api.model.TaskResourceReferenceModel;
import br.com.armando.efficienttraining.api.model.input.TaskResourceReferenceInput;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import br.com.armando.efficienttraining.domain.model.TaskResourceReference;
import br.com.armando.efficienttraining.domain.repository.TaskResourceReferenceRepository;
import br.com.armando.efficienttraining.domain.service.TaskResourceReferenceRegisterService;
import br.com.armando.efficienttraining.domain.service.TaskResourceRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/task-resources/{taskResourceId}/references")
public class TaskResourceReferenceController {

    @Autowired
    TaskResourceRegisterService taskResourceRegisterService;

    @Autowired
    TaskResourceReferenceInputDisassembler taskResourceReferenceInputDisassembler;

    @Autowired
    TaskResourceReferenceRepository taskResourceReferenceRepository;

    @Autowired
    TaskResourceReferenceModelAssembler taskResourceReferenceModelAssembler;

    @Autowired
    TaskResourceReferenceRegisterService taskResourceReferenceRegisterService;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResourceReferenceModel insert(
            @PathVariable Long taskResourceId,
            @RequestBody @Valid TaskResourceReferenceInput taskResourceReferenceInput
    ) {
        TaskResource taskResource = taskResourceRegisterService.findByIdOrFail(taskResourceId);
        TaskResourceReference taskResourceReference = taskResourceReferenceInputDisassembler.toDomainObject(taskResourceReferenceInput);
        taskResourceReference.setTaskResource(taskResource);
        return  taskResourceReferenceModelAssembler.toModel(taskResourceReferenceRepository.save(taskResourceReference));
    }

    @Transactional
    @PutMapping("/{referenceId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResourceReferenceModel update(
            @PathVariable Long taskResourceId,
            @PathVariable Long referenceId,
            @RequestBody @Valid TaskResourceReferenceInput taskResourceReferenceInput
    ) {
        TaskResource taskResource = taskResourceRegisterService.findByIdOrFail(taskResourceId);
        TaskResourceReference taskResourceReferenceToUpdate = taskResourceReferenceRegisterService.findByIdAndResourceIdOrFail(referenceId, taskResourceId);
        taskResourceReferenceInputDisassembler.copyToDomainObject(taskResourceReferenceInput, taskResourceReferenceToUpdate);
        taskResourceReferenceToUpdate.setTaskResource(taskResource);
        return  taskResourceReferenceModelAssembler.toModel(taskResourceReferenceRepository.save(taskResourceReferenceToUpdate));
    }

    @DeleteMapping("/{referenceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long taskResourceId,
            @PathVariable Long referenceId
    ) {
       taskResourceReferenceRegisterService.delete(referenceId, taskResourceId);
    }

}
