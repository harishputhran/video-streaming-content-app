package com.harish.streaming.content.service;

import com.harish.streaming.content.enumeration.ContentClassification;
import com.harish.streaming.content.enumeration.ContentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Service
public class ContentFilterService {

    @Autowired
    private StreamingContentService contentService;

    public void filterContentData(ContentFilter filter, ContentClassification contentType){
        try {
            contentService.getContentData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
