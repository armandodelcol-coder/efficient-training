package br.com.armando.efficienttraining.domain.exception;

public abstract class ResourceNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
