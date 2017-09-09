package com.harish.streaming.content.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Thumbnail {
    private String url;
    private double width;
    private double height;
    private String title;
    private List<String> assetTypes = new ArrayList<>();
}
