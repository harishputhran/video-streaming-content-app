package com.harish.streaming.content.api.controller;

import com.harish.streaming.content.api.ContentFilterAPI;
import com.harish.streaming.content.api.request.ContentFilterCriteria;
import com.harish.streaming.content.api.response.StreamingContentResponse;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import com.harish.streaming.content.service.ContentFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@RestController
public class ContentFilterController implements ContentFilterAPI {

    @Autowired
    private ContentFilterService contentFilterService;

    @Override
    public StreamingContentResponse getStreamingContent(ContentFilterCriteria filterCriteria) throws ContentProcessException, ContentNotFoundException {
        return contentFilterService.filterContentData(filterCriteria.getFilter(), filterCriteria.getLevel());
    }
}
