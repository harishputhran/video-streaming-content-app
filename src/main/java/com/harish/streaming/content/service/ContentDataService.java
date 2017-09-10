package com.harish.streaming.content.service;

import com.harish.streaming.content.api.response.ContentData;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;

/**
 * Created by Harish Puthran on 09/09/17.
 */
public interface ContentDataService {

    ContentData getContentData(String contentDataId) throws ContentNotFoundException, ContentProcessException;
}
