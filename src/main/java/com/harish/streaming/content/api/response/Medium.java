package com.harish.streaming.content.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harish.streaming.content.enumeration.AvailabilityState;
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
public class Medium {
    private String id;
    private String title;
    private String guid;
    private String ownerId;
    private String availableDate;
    private String expirationDate;
    private List<String> ratings = new ArrayList<>();

    @JsonProperty("content")
    private List<Content> contents = new ArrayList();
    private String restrictionId;
    private AvailabilityState availabilityState;

}
