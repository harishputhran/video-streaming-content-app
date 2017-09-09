package com.harish.streaming.content.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamingContentResponse {

    @JsonProperty("$xmlns")
    private Map<String, String> namespaces = new HashMap<>();
    private String startIndex;
    private long itemsPerPage;
    private long entryCount;
    private String title;
    private List<Entry> entries = new ArrayList();

}
