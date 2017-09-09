package com.harish.streaming.content.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Harish Puthran on 09/09/17.
 */
@Configuration
public class RestTemplateConfig {

    @Value("${api.streaming-content.timeout.connect}")
    private int streamingContentApiConnectTimeout;

    @Value("${api.streaming-content.timeout.read}")
    private int streamingContentApiReadTimeout;

    @Bean
    public RestTemplate streamingContentAPIClient(){
        HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientRequestFactory.setConnectTimeout(streamingContentApiConnectTimeout);
        clientRequestFactory.setReadTimeout(streamingContentApiReadTimeout);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientRequestFactory);
        return restTemplate;
    }
}
