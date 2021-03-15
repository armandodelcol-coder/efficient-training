package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.model.TaskResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskResourceChangeStatusService {

    @Autowired
    TaskResourceRegisterService taskResourceRegisterService;

    @Transactional
    public void messy(Long id) {
        TaskResource taskResource = taskResourceRegisterService.findByIdOrFail(id);
        taskResource.messy();
    }

    @Transactional
    public void learning(Long id) {
        TaskResource taskResource = taskResourceRegisterService.findByIdOrFail(id);
        taskResource.learning();
    }

    @Transactional
    public void learned(Long id) {
        TaskResource taskResource = taskResourceRegisterService.findByIdOrFail(id);
        taskResource.learned();
    }

}
