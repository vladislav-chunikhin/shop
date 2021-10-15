package bym.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto methodArgumentNotValidErrorHandler(final MethodArgumentNotValidException ex) {
        String details = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(it -> String.format("\nValidation failed for field: %s , object: %s. Message: %s", it.getField(), it.getObjectName(), it.getDefaultMessage()))
                .collect(Collectors.joining(";"));
        return new ErrorResponseDto("Invalid input parameters. " + details);
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto entityNotFoundErrorHandler(final EntityNotFoundException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ResourceDeletedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ErrorResponseDto resourceDeletedErrorHandler(final ResourceDeletedException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto runtimeErrorHandler(final RuntimeException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

}
