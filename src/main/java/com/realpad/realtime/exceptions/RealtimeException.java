package com.realpad.realtime.exceptions;

import org.springframework.http.HttpStatus;

/**
 * CustomException
 */
public class RealtimeException extends RuntimeException {
  /**
   * For serialization: if any changes is made to this class, update the
   * serialversionID
   */
  private static final long serialVersionUID = 1L;

  private String message;
  private HttpStatus status;

  public RealtimeException(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
