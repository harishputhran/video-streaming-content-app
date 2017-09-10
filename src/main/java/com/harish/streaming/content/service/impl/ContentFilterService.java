package com.harish.streaming.content.service.impl;

import com.harish.streaming.content.api.response.ContentEntry;
import com.harish.streaming.content.api.response.Medium;
import com.harish.streaming.content.api.response.ContentData;
import com.harish.streaming.content.enumeration.ContentClassification;
import com.harish.streaming.content.enumeration.ContentFilter;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import com.harish.streaming.content.service.ContentDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.harish.streaming.content.enumeration.ContentClassification.censored;
import static java.util.Map.Entry;
import static java.util.stream.Collectors.groupingBy;

/** Created by Harish Puthran on 09/09/17. */
@Service
@Slf4j
public class ContentFilterService {

  public static final String CENSORED_GUID_SUFFIX = "C";

  @Autowired private ContentDataService contentDataService;

  @Autowired private ContentDataLocationService contentDataLocationService;

  public ContentData filterContentData(ContentFilter filter, ContentClassification contentClassification)
      throws ContentProcessException, ContentNotFoundException {

    String contentDataId = contentDataLocationService.getContentDataId();
    log.debug("CONTENT DATA ID - {}", contentDataId);
    ContentData contentResponse = contentDataService.getContentData(contentDataId);
    log.debug("CONTENT DATA BEFORE FILTERING - {}", contentResponse);

    Map<String, List<ContentEntry>> groupedMap =
        contentResponse.getEntries()
            .stream()
            .collect(groupingBy(ContentEntry::getPeg$contentClassification));

    List<ContentEntry> filteredEntries = new ArrayList();

    filterMediaForCensoredClassification(contentClassification, groupedMap, filteredEntries);
    filterMediaForNonCensoredClassification(groupedMap, filteredEntries);
    contentResponse.setEntries(filteredEntries);
    log.debug("CONTENT DATA AFTER FILTERING - {}", contentResponse);
    return contentResponse;
  }

  private void filterMediaForCensoredClassification(ContentClassification contentClassification,
    Map<String, List<ContentEntry>> groupedMap, List<ContentEntry> filteredEntries) {
    groupedMap.entrySet()
        .stream()
        .filter(mapEntry -> StringUtils.equalsIgnoreCase(censored.name(), mapEntry.getKey()))
        .map(Entry::getValue)
        .forEach(contentEntries -> contentEntries.stream()
                                   .forEach(entry -> {
                                          if(filterMediaBasedOnLevel(entry, contentClassification)){
                                              filteredEntries.add(entry);
                                          }
                                        }));
  }

    /**
     * Filters the ContentEntry.Media against Level query parameter.
     * Returns true on successful filtering Media based on Level.
     * Else returns false.
     * @param contentEntry
     * @param contentClassification
     * @return
     * @throws ContentProcessException
     */
  private boolean filterMediaBasedOnLevel(ContentEntry contentEntry, ContentClassification contentClassification)
          throws ContentProcessException {
    if(contentClassification == null){
       log.error("No Filter provided for filtering Censored Classification Entries for {}", contentEntry.getGuid());
       return false;
    }else {
        Medium filteredMedia = contentEntry.getMedia()
                .stream()
                .filter(medium ->
                        StringUtils.isNotBlank(medium.getGuid())
                                && getClassification(medium.getGuid()).equals(contentClassification))
                .findFirst()
                .orElse(null);

        contentEntry.getMedia().clear();
        if (filteredMedia != null) {
           return contentEntry.getMedia().add(filteredMedia);
        }
        return false;
    }
  }

  private ContentClassification getClassification(String guid) {
    String guidSuffix = guid.substring(guid.length() - 1);
    return CENSORED_GUID_SUFFIX.equals(guidSuffix)
        ? ContentClassification.censored
        : ContentClassification.uncensored;
  }

  private void filterMediaForNonCensoredClassification(Map<String, List<ContentEntry>> groupedMap,
                                                       List<ContentEntry> filteredEntries) {
    groupedMap.entrySet().stream()
        .filter(mapEntry -> !StringUtils.equalsIgnoreCase(censored.name(), mapEntry.getKey()))
        .map(Entry::getValue)
        .forEach(contentEntries -> contentEntries
                    .stream()
                    .forEach(
                        entry -> {
                          if (entry.getMedia().size() > 1) {
                            Medium medium = entry.getMedia().get(0);
                            entry.getMedia().clear();
                            entry.getMedia().add(medium);
                          }
                          filteredEntries.add(entry);
                        }));
  }
}
