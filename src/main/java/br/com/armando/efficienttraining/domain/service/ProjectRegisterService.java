package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProjectRegisterService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project findByIdOrFail(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new EntityNotFoundException("Todo"));
    }

}
