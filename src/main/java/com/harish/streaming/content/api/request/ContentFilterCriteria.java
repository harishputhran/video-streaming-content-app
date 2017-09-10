package com.harish.streaming.content.api.request;

import com.harish.streaming.content.enumeration.ContentClassification;
import com.harish.streaming.content.enumeration.ContentFilter;
import com.harish.streaming.content.validators.ValidateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.GroupSequence;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Getter
@Setter
@NoArgsConstructor
@GroupSequence(value = {ContentFilterCriteria.class})
@ToString
public class ContentFilterCriteria {

    @ValidateEnum(enumClass=ContentFilter.class, message = "Invalid Content Data Filter provided.", valueExpected = "censoring")
    private ContentFilter filter;

    private ContentClassification level;
}
