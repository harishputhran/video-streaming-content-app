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
public class Content {

    private long bitrate;
    private double duration;
    private String format;
    private long height;
    private String language;
    private double width;
    private String id;
    private List<String> assetTypeIds = new ArrayList<>();
    private List<String> assetTypes = new ArrayList<>();
    private String downloadUrl;
    private List<Release> releases = new ArrayList();
    private String serverId;
    private String streamingUrl;
    private String protectionScheme;
}
