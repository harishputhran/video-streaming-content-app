package com.harish.streaming.content.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@ResponseStatus(NO_CONTENT)
public class ContentNotFoundException extends Exception {

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
