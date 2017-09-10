package com.harish.streaming.content.exception;

import lombok.Getter;

/**
 * Created by Harish Puthran on 09/09/17.
 */
public class ContentNotFoundException extends RuntimeException {

    @Getter
    private String message;

    public ContentNotFoundException(){
        super();
    }

    public ContentNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public ContentNotFoundException(String message, Throwable error){
        super(message, error);
        this.message = message;
    }
}
