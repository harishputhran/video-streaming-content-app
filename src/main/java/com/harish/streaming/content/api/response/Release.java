package com.harish.streaming.content.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Getter
@Setter
@AllArgsConstructor
public class Release {
    private String pid;
    private String url;
    private String restrictionid;
}
