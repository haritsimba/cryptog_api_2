package org.eqima.cryptogApi.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties
@PropertySource(value = "classpath:application.yml")
public class VkaWebclient {
    @Value("${anchor.cryptog.api.url}")
    private String WALLET_BASE_URL;

    Logger LOGGER = LoggerFactory.getLogger(VkaWebclient.class);
    @Bean(name = "anchor_webclient")
    public WebClient AnchorWebClient(){
        System.out.println(WALLET_BASE_URL);
        return WebClient.builder()
                .baseUrl(WALLET_BASE_URL+"/wallet")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter((request, next) -> {
                    LOGGER.warn("Request Anchor URI api {}",request.url());
                    return next.exchange(request);
                })
                .build();
    }
}
