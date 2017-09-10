package com.harish.streaming.content.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.harish.streaming.content.api.response.ContentData;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import com.harish.streaming.content.service.ContentDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import static org.springframework.http.HttpStatus.OK;

/** Created by Harish Puthran on 09/09/17. */
@Service
@Primary
@Slf4j
public class GoogleDriveContentDataService implements ContentDataService {

  private static final String CONTENT_DATA_ERROR_TXT = "CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data due to {}";
  private static final String CONTENT_DATA_PROCESS_ERROR = "Content Data could not be processed due to ";

  @Value("${content-data.download.url}")
  private String streamingContentDataURL;

  @Value("${cache.expiry.value}")
  private int cacheExpiryValue;

  @Value("${cache.expiry.unit}")
  private String cacheExpiryUnit;

  @Value("${cache.entries}")
  private int cacheEntries;

  @Autowired @Qualifier("contentDataAPIClient")
  private RestTemplate streamingContentAPIClient;

  private LoadingCache<String, ContentData> contentDataCache;

  @PostConstruct
  private void loadCache() {
    contentDataCache =
        CacheBuilder.newBuilder()
            .maximumSize(cacheEntries)
            .expireAfterWrite(cacheExpiryValue, TimeUnit.valueOf(cacheExpiryUnit))
            .build(
                new CacheLoader<String, ContentData>() {
                  @Override
                  public ContentData load(String key) throws ContentNotFoundException {
                    try {
                      return fetchContentData(key);
                    } catch (Exception exception) {
                      throw new ContentNotFoundException(
                          "Content Data not available for " + key, exception);
                    }
                  }
                });
  }

  /**
   * Get Content Data from Cache.
   * @param contentFileID
   * @return ContentData
   * @throws ContentProcessException
   * @throws ContentNotFoundException
   */
  public ContentData getContentData(String contentFileID) throws ContentProcessException, ContentNotFoundException {
    return contentDataCache.getUnchecked(contentFileID);
  }

  /**
   * Fetch ContentData from External API.
   *
   * @param contentDataID
   * @return ContentData
   * @throws ContentProcessException
   */
  private ContentData fetchContentData(String contentDataID) throws ContentProcessException {
    try {
      ResponseEntity<ContentData> responseEntity =
              streamingContentAPIClient.getForEntity(streamingContentDataURL, ContentData.class, contentDataID);

      if (OK.equals(responseEntity.getStatusCode())) {
        return responseEntity.getBody();
      } else {
        throw new ContentNotFoundException("Content Data is not found for ID "+contentDataID);
      }
    } catch (RestClientException restClientException) {
      log.error(CONTENT_DATA_ERROR_TXT, restClientException.getMessage());
      throw new ContentProcessException(CONTENT_DATA_PROCESS_ERROR + restClientException.getMessage(),
                                                                     restClientException);

    } catch (Exception exception) {
      log.error(CONTENT_DATA_ERROR_TXT, exception.getMessage());
      throw new ContentProcessException(CONTENT_DATA_PROCESS_ERROR + exception.getMessage(), exception);
    }
  }
}