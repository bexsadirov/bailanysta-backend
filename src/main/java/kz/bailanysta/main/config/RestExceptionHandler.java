package kz.bailanysta.main.config;

import kz.bailanysta.main.exception.ForbiddenException;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.exception.UnauthorizedException;
import kz.bailanysta.main.util.MessageResponse;
import kz.bailanysta.main.util.MessagesResource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, Throwable.class})
    protected ResponseEntity<Object> handleServiceException(Exception exception,
                                                            WebRequest request) {
        HttpStatus responseStatus;
        String message = exception.getMessage();

        if (exception instanceof InputException) {
            responseStatus = HttpStatus.BAD_REQUEST;
            message = MessagesResource.message((InputException) exception);
        } else if (exception instanceof ForbiddenException) {
            responseStatus = HttpStatus.FORBIDDEN;
            message = MessagesResource.message((ForbiddenException) exception);
        } else if (exception instanceof UnauthorizedException) {
            responseStatus = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof HttpClientErrorException) {
            log.error("exception = " + exception.getMessage());
            responseStatus = ((HttpClientErrorException) exception).getStatusCode();
            message = ((HttpClientErrorException) exception).getStatusText();
        } else {
            log.error("exception = " + exception.getMessage());
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return handleExceptionInternal(exception, new MessageResponse(message), new HttpHeaders(), responseStatus, request);
    }

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Dto validation exception handler
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return dtoExceptionResponse(fieldErrors);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return dtoExceptionResponse(fieldErrors);
    }

    /*
     * Helpers
     */
    private ResponseEntity<Object> dtoExceptionResponse(List<FieldError> fieldErrors) {
        /*Throws front-end exception in monolith*/
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        FieldError fieldError = fieldErrors.get(0);
        String errorMessage;
        if (fieldError.getCode() != null
            && fieldError.getCode().equals("NotNull")) {
            errorMessage = "`" + fieldErrors.get(0).getField() + "` is not presented";
        } else {
            errorMessage = fieldErrors.get(0).getDefaultMessage();
        }

        return new ResponseEntity<>(new MessageResponse(errorMessage), responseStatus);
    }
}
