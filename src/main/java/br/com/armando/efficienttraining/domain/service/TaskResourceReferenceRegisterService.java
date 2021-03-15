package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.exception.TaskResourceReferenceNotFoundException;
import br.com.armando.efficienttraining.domain.model.TaskResourceReference;
import br.com.armando.efficienttraining.domain.repository.TaskResourceReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskResourceReferenceRegisterService {

    @Autowired
    private TaskResourceReferenceRepository taskResourceReferenceRepository;

    public TaskResourceReference findByIdOrFail(Long referenceId) {
        Optional<TaskResourceReference> taskResourceReference = taskResourceReferenceRepository.findById(referenceId);
        return taskResourceReference.orElseThrow(() -> new TaskResourceReferenceNotFoundException(referenceId));
    }

    public TaskResourceReference findByIdAndResourceIdOrFail(Long referenceId, Long resourceId) {
        Optional<TaskResourceReference> taskResourceReference = taskResourceReferenceRepository.findByIdAndTaskResourceId(referenceId, resourceId);
        return taskResourceReference.orElseThrow(() -> new TaskResourceReferenceNotFoundException(referenceId, resourceId));
    }

    @Transactional
    public void delete(Long referenceId, Long resourceId) {
        TaskResourceReference taskResourceReference = findByIdAndResourceIdOrFail(referenceId, resourceId);
        taskResourceReferenceRepository.deleteById(taskResourceReference.getId());
    }
}
