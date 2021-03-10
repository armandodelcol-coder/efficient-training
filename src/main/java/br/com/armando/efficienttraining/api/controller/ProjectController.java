package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.ProjectModelAssembler;
import br.com.armando.efficienttraining.api.disassembler.ProjectInputDisassembler;
import br.com.armando.efficienttraining.api.model.input.ProjectInput;
import br.com.armando.efficienttraining.api.model.ProjectModel;
import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
import br.com.armando.efficienttraining.domain.service.ProjectRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectModelAssembler projectModelAssembler;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRegisterService projectRegisterService;

    @Autowired
    private ProjectInputDisassembler projectInputDisassembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectModel> list() {
        return projectModelAssembler.toCollectionModel(projectRepository.findAll());
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectModel findById(@PathVariable Long projectId) {
        return projectModelAssembler.toModel(projectRegisterService.findByIdOrFail(projectId));
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectModel insert(@RequestBody @Valid ProjectInput projectInput) {
        Project project = projectInputDisassembler.toDomainObject(projectInput);
        return projectModelAssembler.toModel(projectRepository.save(project));
    }

    @Transactional
    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectModel update(@RequestBody @Valid ProjectInput projectInput, @PathVariable Long projectId) {
        Project projectToUpdate = projectRegisterService.findByIdOrFail(projectId);
        projectInputDisassembler.copyToDomainObject(projectInput, projectToUpdate);
        return projectModelAssembler.toModel(projectRepository.save(projectToUpdate));
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long projectId) {
        projectRegisterService.delete(projectId);
    }

}
