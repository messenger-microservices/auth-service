package ru.pulsarmn.messenger.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import ru.pulsarmn.messenger.auth.config.properties.RestClientConfigurationProperties;


@Configuration
@EnableConfigurationProperties(RestClientConfigurationProperties.class)
public class WebConfiguration {

    private final RestClientConfigurationProperties restClientConfigurationProperties;

    public WebConfiguration(RestClientConfigurationProperties restClientConfigurationProperties) {
        this.restClientConfigurationProperties = restClientConfigurationProperties;
    }

    @Bean
    RestClient userServiceRestClient() {
        String userServiceUrl = restClientConfigurationProperties.getUserService().getUrl();
        return RestClient.builder()
                .baseUrl(userServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
