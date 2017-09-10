package com.harish.streaming.content.api.controller;

import com.harish.streaming.content.api.ContentFilterAPI;
import com.harish.streaming.content.api.request.ContentFilterCriteria;
import com.harish.streaming.content.api.response.ContentData;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import com.harish.streaming.content.service.impl.ContentFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@RestController
@Slf4j
@Validated
public class ContentFilterController implements ContentFilterAPI {

    @Autowired
    private ContentFilterService contentFilterService;

    @Override
    public ContentData getStreamingContent(ContentFilterCriteria filterCriteria) throws ContentProcessException, ContentNotFoundException {
        return contentFilterService.filterContentData(filterCriteria.getFilter(), filterCriteria.getLevel());
    }

    @ExceptionHandler({ContentNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleContentNotFound(ContentNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler({ContentProcessException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleContentProcessException(ContentProcessException ex) {
        return ex.getMessage();
    }
}
