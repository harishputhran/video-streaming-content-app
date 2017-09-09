package com.harish.streaming.content.service;

import com.harish.streaming.content.api.response.StreamingContentResponse;
import com.harish.streaming.content.enumeration.ContentClassification;
import com.harish.streaming.content.enumeration.ContentFilter;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Service
public class ContentFilterService {

    @Autowired
    private ContentDataService contentService;

    public StreamingContentResponse filterContentData(ContentFilter filter, ContentClassification contentType) throws ContentProcessException, ContentNotFoundException {
           return contentService.getContentData();
    }
}
