package br.com.armando.efficienttraining.domain.service;

import br.com.armando.efficienttraining.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskChangeStatusService {

    @Autowired
    TaskRegisterService taskRegisterService;

    @Transactional
    public void doing(Long id) {
        Task task = taskRegisterService.findByIdOrFail(id);
        task.doing();
    }

    @Transactional
    public void done(Long id) {
        Task task = taskRegisterService.findByIdOrFail(id);
        task.done();
    }

}
