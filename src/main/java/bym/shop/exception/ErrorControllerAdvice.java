package bym.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidErrorHandler(final MethodArgumentNotValidException ex) {
        String details = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(it -> String.format("\nValidation failed for field: %s , object: %s. Message: %s", it.getField(), it.getObjectName(), it.getDefaultMessage()))
                .collect(Collectors.joining(";"));
        return new ErrorResponse("Invalid input parameters. " + details);
    }

}
