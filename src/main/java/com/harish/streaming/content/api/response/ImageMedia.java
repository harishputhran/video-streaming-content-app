package com.harish.streaming.content.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageMedia {
    private String mediaId;
    private boolean isPrimary;
    private String imageType;
}
