package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.exception.EntityInUseException;
import br.com.armando.efficienttraining.domain.exception.EntityNotFoundException;
import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProjectRegisterService {

    private static final String PROJECT_NOT_FOUND_MSG = "Não existe um cadastro de projeto com código %d";
    private static final String PROJECT_IN_USE_MSG = "O projeto de código %d não pode ser removido, pois, está em uso.";

    @Autowired
    private ProjectRepository projectRepository;

    public Project findByIdOrFail(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new EntityNotFoundException(
                String.format(PROJECT_NOT_FOUND_MSG, id)
        ));
    }

    @Transactional
    public void delete(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format(PROJECT_NOT_FOUND_MSG, projectId)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(PROJECT_IN_USE_MSG, projectId)
            );
        }
    }
}
