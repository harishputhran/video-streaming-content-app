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

    @Value("${content-data.download.timeout.connect}")
    private int streamingContentApiConnectTimeout;

    @Value("${content-data.download.timeout.read}")
    private int streamingContentApiReadTimeout;

    @Bean
    public RestTemplate contentDataAPIClient(){
        HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientRequestFactory.setConnectTimeout(streamingContentApiConnectTimeout);
        clientRequestFactory.setReadTimeout(streamingContentApiReadTimeout);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientRequestFactory);
        return restTemplate;
    }
}
