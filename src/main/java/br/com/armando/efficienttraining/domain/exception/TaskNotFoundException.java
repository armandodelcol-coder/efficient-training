package br.com.armando.efficienttraining.domain.exception;

public class TaskNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id) {
        super(String.format("Não foi encontrada uma Task com o código %d", id));
    }
}
