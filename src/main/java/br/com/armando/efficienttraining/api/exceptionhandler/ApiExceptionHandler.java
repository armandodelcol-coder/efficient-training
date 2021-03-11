package br.com.armando.efficienttraining.api.exceptionhandler;

import br.com.armando.efficienttraining.domain.exception.BusinessException;
import br.com.armando.efficienttraining.domain.exception.EntityInUseException;
import br.com.armando.efficienttraining.domain.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERROR_GENERIC = "Internal Server Error, Try again or contact the system administrator.";

    //My Custom Exception Classes

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException e,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(
            EntityInUseException e,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException e,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_RULE;
        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.INVALID_PARAM;
        String detail = String.format(
                "O parâmetro de url '%s' recebeu o valor '%s' que é um tipo inválido. " +
                        "Corrija e informe um valor compátivel com o tipo '%s'",
                e.getName(), e.getValue(), e.getRequiredType().getSimpleName()
        );
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return super.handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        String detail = "Ocorreu um erro interno no sistema. Tente novamente ou contate um administrador do sistema.";
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return super.handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    // OVERRIDES


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = String.format("O recurso '%s' que você tentou acessar não existe.", e.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return super.handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.MEDIA_NOT_SUPPORTED;
        String detail = String.format(
                "O tipo de mídia '%s' solicitado não é suportado pela aplicação.",
                e.getContentType().getSubtype()
        );
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return super.handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.BAD_INPUT_MESSAGE;
        String detail = "O corpo da requisição está inválido.";
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return super.handleExceptionInternal(e, problem, headers, status, request);
    }

    // Main Handler
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println(e.getClass());
        if (body == null) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    // PRIVATE/UTILS

    private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException rootCause, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(rootCause.getPath());
        ProblemType problemType = ProblemType.BAD_INPUT_MESSAGE;
        String detail = String.format(
                "A propriedade '%s' informada não existe.",
                path
        );
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_ERROR_GENERIC)
                .build();

        return handleExceptionInternal(rootCause, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException rootCause, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(rootCause.getPath());
        ProblemType problemType = ProblemType.BAD_INPUT_MESSAGE;
        String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s', esse valor é inválido para o tipo %s.",
                path, rootCause.getValue(), rootCause.getTargetType().getSimpleName()
        );
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_ERROR_GENERIC)
                .build();

        return handleExceptionInternal(rootCause, problem, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .detail(detail)
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle());
    }
}
