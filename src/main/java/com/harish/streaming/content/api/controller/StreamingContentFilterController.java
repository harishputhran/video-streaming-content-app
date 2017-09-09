package com.harish.streaming.content.api.controller;

import com.harish.streaming.content.api.StreamingContentFilterAPI;
import com.harish.streaming.content.api.request.ContentFilterCriteria;
import com.harish.streaming.content.api.response.StreamingContentResponse;
import com.harish.streaming.content.service.ContentFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@RestController
public class StreamingContentFilterController implements StreamingContentFilterAPI {

    @Autowired
    private ContentFilterService contentFilterService;

    @Override
    public StreamingContentResponse getStreamingContent(ContentFilterCriteria filterCriteria) {
        contentFilterService.filterContentData(filterCriteria.getFilter(), filterCriteria.getLevel());
        return null;
    }
}
