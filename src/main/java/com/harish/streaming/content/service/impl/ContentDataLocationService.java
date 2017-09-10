package com.harish.streaming.content.service.impl;

import com.harish.streaming.content.exception.ContentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

/**
 * The class is to fetch the File ID from Redirect URL to retrieve Content Data.
 * This class can be removed once the actual external URL is configured.
 */
@Service
@Slf4j
public class ContentDataLocationService {

    private static final int INDEX_2 = 2;
    private static final String URL_PATH_SEPARATOR = "/";

    @Value("${content-location.url}")
    private String streamingContentApiURL;

    @Value("${content-location.timeout.read}")
    private int contentLocationReadTimeout;

    public String getContentDataId() throws ContentNotFoundException {
        try {
            URL contentDataURL = getContentDataLocation();
            log.debug("CONTENT DATA LOCATION - {}", contentDataURL);
            return getContentDataId(contentDataURL);
        } catch (IOException ioException) {
            log.error("CONTENT DATA RETRIEVAL - Error occurred during retrieval of Content Data ID due to {}", ioException.getMessage());
            throw new ContentNotFoundException("Content Data ID not available", ioException);
        }
    }

    /**
     * Method invokes streaming-content API which returns the location of Content Data file.
     * Response is converted to String for further processing.
     *
     * @return URL - redirected URL where Content Data file is present.
     * @throws IOException
     */
    private URL getContentDataLocation() throws IOException {
        Connection connection = Jsoup.connect(streamingContentApiURL);
        connection.timeout(contentLocationReadTimeout);
        Connection.Response contentLocationDoc = connection.execute();
        return contentLocationDoc.url();
    }

    /**
     * For URL String : /file/d/<<FileId>>/view
     * Method splits the Content Data URL using / to <<FileId>>
     *
     * @param contentDataURL
     * @return String - fileId or Empty
     */
    private String getContentDataId(URL contentDataURL) {
        String path = contentDataURL.getPath();
        log.info("REDIRECTED URL PATH - {}", path);
        if(StringUtils.contains(path, URL_PATH_SEPARATOR)){
            String[] splitStrings = StringUtils.split(path, URL_PATH_SEPARATOR);
            return splitStrings.length == 4 ? splitStrings[INDEX_2] : StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }
}
