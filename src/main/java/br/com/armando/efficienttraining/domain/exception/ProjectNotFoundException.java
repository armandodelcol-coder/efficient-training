package br.com.armando.efficienttraining.domain.exception;

public class ProjectNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException(Long id) {
        super(String.format("Não existe um cadastro de projeto com id %d", id));
    }
}
