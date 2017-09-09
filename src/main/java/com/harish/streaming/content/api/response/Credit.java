package com.harish.streaming.content.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Credit {
    private String characterName;
    private String creditType;
    private boolean isInactive;
    private int order;
    private String personid;
    private String personName;
}
