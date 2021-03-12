package br.com.armando.efficienttraining.domain.exception;

public class TaskResourceNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public TaskResourceNotFoundException(String message) {
        super(message);
    }

    public TaskResourceNotFoundException(Long id) {
        super(String.format("Não foi encontrada uma Recurso de Task com o código %d", id));
    }
}
