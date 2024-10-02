package com.realpad.realtime.config;

import com.realpad.realtime.controllers.apiresponse.ApiResponse;
import com.realpad.realtime.exceptions.RealtimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RequestExceptionHandler
 */
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(RealtimeException.class)
  public ResponseEntity<Object> handleRealtimeException(RealtimeException ce) {
    ApiResponse<?> ar = new ApiResponse<>(ce.getStatus());
    ar.setError(ce.getMessage());
    return buildResponseEntity(ar);
  }


  private ResponseEntity<Object> buildResponseEntity(ApiResponse<?> apiResponse) {
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }
}
