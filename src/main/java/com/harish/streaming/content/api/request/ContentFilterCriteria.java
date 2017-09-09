package com.harish.streaming.content.api.request;

import com.harish.streaming.content.enumeration.ContentClassification;
import com.harish.streaming.content.enumeration.ContentFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class ContentFilterCriteria {

    private ContentFilter filter;
    private ContentClassification level;
}
