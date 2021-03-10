package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.exception.EntityNotFoundException;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskRegisterService {

    @Autowired
    private TaskRepository taskRepository;

    public Task findByIdOrFail(Long projectId, Long taskId) {
        Optional<Task> task = taskRepository.findById(projectId, taskId);
        return task.orElseThrow(() -> new EntityNotFoundException(
                String.format("Não foi encontrado uma Task de id %d no projeto de id %d", taskId, projectId)
        ));
    }

    @Transactional
    public void deleteById(Long projectId, Long taskId) {
        Task task = findByIdOrFail(projectId, taskId);
        taskRepository.delete(task);
    }
}
