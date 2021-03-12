package br.com.armando.efficienttraining.api.controller;

import br.com.armando.efficienttraining.domain.service.TaskChangeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/{taskId}")
public class TaskStatusController {

    @Autowired
    private TaskChangeStatusService taskChangeStatusService;

    @PutMapping("/doing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void doing(@PathVariable Long taskId) {
        taskChangeStatusService.doing(taskId);
    }

    @PutMapping("/done")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void done(@PathVariable Long taskId) {
        taskChangeStatusService.done(taskId);
    }

}
