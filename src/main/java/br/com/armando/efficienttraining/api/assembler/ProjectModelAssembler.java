package br.com.armando.efficienttraining.api.assembler;

import br.com.armando.efficienttraining.api.model.ProjectModel;
import br.com.armando.efficienttraining.domain.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProjectModel toModel(Project project) {
        return modelMapper.map(project, ProjectModel.class);
    }

    public List<ProjectModel> toCollectionModel(List<Project> projectList) {
        return projectList.stream()
                .map(project -> toModel(project))
                .collect(Collectors.toList());
    }

}
