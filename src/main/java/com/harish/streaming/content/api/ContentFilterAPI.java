package com.harish.streaming.content.api;

import com.harish.streaming.content.api.request.ContentFilterCriteria;
import com.harish.streaming.content.api.response.ContentData;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@RequestMapping(path="/media", produces = APPLICATION_JSON_VALUE)
public interface ContentFilterAPI {

    /**
     * Method to retrieve the Streaming Content based on the following query parameters:
     * 1. Filter -
     * 2. Level
     *
     * @param filterCriteria - containing the query parameters - filter and level
     * @return ContentData
     */
    @GetMapping
    @ResponseStatus(OK)
    ContentData getStreamingContent(ContentFilterCriteria filterCriteria) throws ContentProcessException, ContentNotFoundException;
}
