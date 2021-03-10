package br.com.armando.efficienttraining.api.disassembler;

import br.com.armando.efficienttraining.api.model.input.ProjectInput;
import br.com.armando.efficienttraining.domain.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Project toDomainObject(ProjectInput projectInput) {
        return modelMapper.map(projectInput, Project.class);
    }

    public void copyToDomainObject(ProjectInput projectInput, Project project) {
        modelMapper.map(projectInput, project);
    }

}
