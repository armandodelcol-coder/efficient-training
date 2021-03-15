package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.domain.service.TaskResourceChangeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-resources/references/{referenceId}")
public class TaskResourceStatusController {

    @Autowired
    private TaskResourceChangeStatusService taskResourceChangeStatusService;

    @PutMapping("/messy")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void messy(@PathVariable Long referenceId) {
        taskResourceChangeStatusService.messy(referenceId);
    }

    @PutMapping("/learning")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void learning(@PathVariable Long referenceId) {
        taskResourceChangeStatusService.learning(referenceId);
    }

    @PutMapping("/learned")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void learned(@PathVariable Long referenceId) {
        taskResourceChangeStatusService.learned(referenceId);
    }

}
