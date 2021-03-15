package br.com.armando.efficienttraining.domain.exception;

public class TaskResourceReferenceNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public TaskResourceReferenceNotFoundException(String message) {
        super(message);
    }

    public TaskResourceReferenceNotFoundException(Long id, Long resourceId) {
        super(String.format("Não foi encontrada uma referência de código %d do recurso com o código %d", id, resourceId));
    }
}
