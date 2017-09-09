package com.harish.streaming.content.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
public class StreamingContentService {

    @Value("${api.streaming-content.url}")
    private String streamingContentApiURL;

    @Value("${api.content-data.download.url}")
    private String streamingContentDataURL;

    @Autowired
    @Qualifier("streamingContentAPIClient")
    private RestTemplate streamingContentAPIClient;

    public void getContentData() throws IOException {
        try {
            URL url = new URL(streamingContentApiURL);
            try (ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
                 FileOutputStream buffOS = new FileOutputStream("content-location.html")) {
                    buffOS.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
                }
            Path path = Paths.get("content-location.html");
            StringBuilder builder = new StringBuilder();
            Files.lines(path).forEach(line -> builder.append(line));
            String data = builder.toString();
            String contentDataURL = Jsoup.parse(data).body().select("a").attr("href").toString();
            log.info("HREF : {}", contentDataURL);

            String contentFileID = StringUtils.split(contentDataURL, "?")[1];
            log.info("ID : {}", contentFileID);
            ResponseEntity<String> responseEntity = streamingContentAPIClient.getForEntity(streamingContentDataURL, String.class, contentFileID);
            log.info("RESPONSE - {}", responseEntity);

        } catch (Exception e) {
            log.error("", e);
        }
    }
}
