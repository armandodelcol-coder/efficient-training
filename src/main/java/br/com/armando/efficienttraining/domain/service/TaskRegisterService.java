package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TaskRegisterService {

    @Autowired
    private TaskRepository taskRepository;

    public Task findByIdOrFail(Long projectId, Long taskId) {
        Optional<Task> task = taskRepository.findById(projectId, taskId);
        return task.orElseThrow(() -> new EntityNotFoundException("Todo CustomEntityNotFound"));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
