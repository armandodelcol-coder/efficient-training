package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.exception.EntityInUseException;
import br.com.armando.efficienttraining.domain.exception.TaskNotFoundException;
import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskRegisterService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRegisterService projectRegisterService;

    public Task findByIdOrFail(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Transactional
    public Task save(Task task) {
        Long projectId = task.getProject().getId();
        Project project = projectRegisterService.findByIdOrFail(projectId);
        task.setProject(project);
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteById(Long taskId) {
        try {
            taskRepository.deleteById(taskId);
            taskRepository.flush();
        }
        catch (EmptyResultDataAccessException e) {
            throw new TaskNotFoundException(taskId);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("A task de código %d não pode ser removida, pois, está em uso.", taskId)
            );
        }
    }

}
