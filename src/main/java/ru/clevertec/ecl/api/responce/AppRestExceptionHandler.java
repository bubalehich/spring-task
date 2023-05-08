package ru.clevertec.ecl.api.responce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.exception.ServiceException;

@ControllerAdvice
public class AppRestExceptionHandler {

    private static final int ITEM_NOT_FOUND = 100404;
    private static final int REQUEST_PARAMS_NOT_VALID = 100400;
    private static final int SERVER_ERR = 100500;
    private static final int COMMON_ERR = 666;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(ITEM_NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRequestParamsNotValidException(RequestParamsNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(REQUEST_PARAMS_NOT_VALID, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        ErrorResponse errorResponse = new ErrorResponse(SERVER_ERR, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(COMMON_ERR, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
