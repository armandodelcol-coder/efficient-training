package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.exception.TaskResourceNotFoundException;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import br.com.armando.efficienttraining.domain.repository.TaskResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskResourceRegisterService {

    @Autowired
    private TaskResourceRepository taskResourceRepository;

    @Autowired
    private TaskRegisterService taskRegisterService;

    public TaskResource findByIdOrFail(Long taskResourceId) {
        Optional<TaskResource> taskResource = taskResourceRepository.findById(taskResourceId);
        return taskResource.orElseThrow(() -> new TaskResourceNotFoundException(taskResourceId));
    }

    @Transactional
    public TaskResource save(TaskResource taskResource) {
        Long taskId = taskResource.getTask().getId();
        Task task = taskRegisterService.findByIdOrFail(taskId);
        taskResource.setTask(task);
        return taskResourceRepository.save(taskResource);
    }

    @Transactional
    public void deleteById(Long taskResourceId) {
        try {
            taskResourceRepository.deleteById(taskResourceId);
            taskResourceRepository.flush();
        }
        catch (EmptyResultDataAccessException e) {
            throw new TaskResourceNotFoundException(taskResourceId);
        }
    }

}
