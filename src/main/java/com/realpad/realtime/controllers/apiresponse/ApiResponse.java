package com.realpad.realtime.controllers.apiresponse;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ApiResponse
 */
@Getter
@Setter
public class ApiResponse<T> {

  private HttpStatus status;
  private String message;
  private String error;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String debugMessage;
  private List<ApiValidationError> validationErrors;
  private T data;

  private ApiResponse() {
    timestamp = LocalDateTime.now();
  }

  public ApiResponse(HttpStatus status) {
    this();
    this.status = status;
  }

  public ApiResponse(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.error = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ApiResponse(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ApiResponse(T data, String message, HttpStatus status) {
    this();
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public ResponseEntity<ApiResponse<T>> toResponseEntity() {
    return new ResponseEntity<>(this, this.status);
  }

  private void addValidationError(ApiValidationError validationError) {
    if (validationErrors == null) {
      validationErrors = new ArrayList<>();
    }
    validationErrors.add(validationError);
  }

  private void addValidationError(String object, String field, Object rejectedValue, String message) {
    addValidationError(new ApiValidationError(object, field, rejectedValue, message));
  }

  private void addValidationError(String object, String message) {
    addValidationError(new ApiValidationError(object, message));
  }

  private void addValidationError(FieldError fieldError) {
    this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
        fieldError.getDefaultMessage());
  }

  public void addValidationErrors(List<FieldError> fieldErrors) {
    fieldErrors.forEach(this::addValidationError);
  }

  private void addValidationError(ObjectError objectError) {
    this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
  }

  public void addValidationError(List<ObjectError> globalErrors) {
    globalErrors.forEach(this::addValidationError);
  }
}
