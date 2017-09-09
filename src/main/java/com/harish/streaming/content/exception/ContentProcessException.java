package com.harish.streaming.content.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Created by Harish Puthran on 09/09/17.
 *
 */
@ResponseStatus(INTERNAL_SERVER_ERROR)
public class ContentProcessException extends Exception {

  @Getter private String message;

  public ContentProcessException() {
    super();
  }

  public ContentProcessException(String message) {
    super(message);
    this.message = message;
  }

  public ContentProcessException(String message, Throwable error) {
    super(message, error);
    this.message = message;
  }
}
