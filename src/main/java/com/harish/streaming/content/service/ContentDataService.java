package com.harish.streaming.content.service;

import com.harish.streaming.content.api.response.StreamingContentResponse;
import com.harish.streaming.content.exception.ContentNotFoundException;
import com.harish.streaming.content.exception.ContentProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Service
@Slf4j
public class ContentDataService {

    private static final String ELEMENT_A = "a";
    private static final String HREF_ATTRIBUTE = "href";
    private static final int INDEX_1 = 1;
    private static final String QUESTION_MARK_LITERAL = "?";

    @Value("${content-location.url}")
    private String streamingContentApiURL;

    @Value("${content-data.download.url}")
    private String streamingContentDataURL;

    @Autowired
    @Qualifier("contentDataAPIClient")
    private RestTemplate streamingContentAPIClient;

    public StreamingContentResponse getContentData() throws ContentProcessException, ContentNotFoundException {
        try {
            String data = getContentDataLocation();
            String contentDataURL = getContentDataURL(data);
            log.info("CONTENT DATA LOCATION : {}", contentDataURL);
            String contentFileID = getContentDataFileId(contentDataURL);
            log.info("CONTENT FILE ID : {}", contentFileID);

            ResponseEntity<StreamingContentResponse> responseEntity = streamingContentAPIClient.getForEntity(streamingContentDataURL + contentFileID, StreamingContentResponse.class);
            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                return responseEntity.getBody();
            } else {
                throw new ContentNotFoundException("Content Data is not found.");
            }
        } catch (RestClientException restClientException) {
            log.error("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data due to {}", restClientException.getMessage(), restClientException);
            throw new ContentProcessException("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data", restClientException);
        } catch (IOException ioException) {
            log.error("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data due to {}", ioException.getMessage(), ioException);
            throw new ContentProcessException("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data", ioException);
        } catch (Exception exception) {
            log.error("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data due to {}", exception.getMessage(), exception);
            throw new ContentProcessException("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data", exception);
        }
    }

    /**
     * Method invokes streaming-content API which returns the location of Content Data file.
     * Response is converted to String for further processing.
     *
     * @return String
     * @throws IOException
     */
    private String getContentDataLocation() throws IOException {
        URL url = new URL(streamingContentApiURL);
        String fileName = "content-location-"+Thread.currentThread().getId()+".html";
        Path path = !Files.exists(Paths.get(fileName)) ? Files.createFile(Paths.get(fileName)) : Paths.get(fileName);
        try (ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
             FileOutputStream buffOS = new FileOutputStream(path.toFile())) {
            buffOS.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        }
        StringBuilder builder = new StringBuilder();
        Files.lines(path).forEach(line -> builder.append(line));
        Files.deleteIfExists(path);
        return builder.toString();
    }

    /**
     * Response from streaming-content API returns the location of Content Data file.
     * Response is an html with URL mapped to <body><a href="Content Data JSON File URL"></a> </body>
     * This method parses the HTML and retrurns the Content Data URL.
     *
     * @param data
     * @return String - URL for Content Data JSON File
     */
    private String getContentDataURL(String data) {
        return Jsoup.parse(data).body().select(ELEMENT_A).attr(HREF_ATTRIBUTE).toString();
    }

    /**
     * For URL String : https://drive.google.com/open?id=fileId
     * Method splits the Content Data URL using ? to get id=fileId
     */
    private String getContentDataFileId(String contentDataURL) {
        return StringUtils.contains(contentDataURL, QUESTION_MARK_LITERAL)
                ? StringUtils.split(contentDataURL, QUESTION_MARK_LITERAL)[INDEX_1]
                : StringUtils.EMPTY;
    }
}
