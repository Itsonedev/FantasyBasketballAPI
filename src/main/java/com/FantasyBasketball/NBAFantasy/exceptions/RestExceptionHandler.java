package com.FantasyBasketball.NBAFantasy.exceptions;

import com.FantasyBasketball.NBAFantasy.error.ErrorDetail;
import com.FantasyBasketball.NBAFantasy.error.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Object> handleElementNotFound(ElementNotFoundException enfe){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Element Not Found");
        errorDetail.setDetail(enfe.getMessage());
        errorDetail.setDeveloperMessage(enfe.getClass().getName());

        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Method Argument Not Valid");
        errorDetail.setDetail("Input Validation Failed");
        errorDetail.setDeveloperMessage(manve.getClass().getName());
        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
        for(FieldError fe : fieldErrors) {
            List<ValidationError> validationErrorList = errorDetail.getErrors().
                    get(fe.getField());
            if(validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
                errorDetail.getErrors().put(fe.getField(),
                        validationErrorList);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(fe.getDefaultMessage());
            validationErrorList.add(validationError);

        }
        return handleExceptionInternal(manve, errorDetail, headers, status, request);
    }
}
