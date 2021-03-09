package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.api.assembler.ProjectModelAssembler;
import br.com.armando.efficienttraining.api.model.ProjectModel;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectModelAssembler projectModelAssembler;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectModel> list() {
        return projectModelAssembler.toCollectionModel(projectRepository.findAll());
    }

}
