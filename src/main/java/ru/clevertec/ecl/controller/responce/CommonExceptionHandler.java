package ru.clevertec.ecl.controller.responce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.exception.ServiceException;

@ControllerAdvice
public class CommonExceptionHandler {
    private static final int ITEM_NOT_FOUND = 100404;
    private static final int REQUEST_PARAMS_NOT_VALID = 100400;
    private static final int SERVER_ERR = 100500;
    private static final int COMMON_ERR = 666;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(ITEM_NOT_FOUND);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(RequestParamsNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(REQUEST_PARAMS_NOT_VALID);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(ServiceException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(SERVER_ERR);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAdd(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(COMMON_ERR);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}