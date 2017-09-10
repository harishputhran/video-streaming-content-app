package com.harish.streaming.content.exception;

import lombok.Getter;

/**
 * Created by Harish Puthran on 09/09/17.
 *
 */
public class ContentProcessException extends RuntimeException {

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
