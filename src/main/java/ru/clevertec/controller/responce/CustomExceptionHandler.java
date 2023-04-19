package ru.clevertec.controller.responce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exception.TagNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(TagNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(1231231, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGiftCertificateNotFoundException(TagNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(112233, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAdd(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(234567, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
