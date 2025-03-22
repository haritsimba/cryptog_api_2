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
public class MifosWebclient {
    @Value("${fineract.mifos.api.url}")
    private String MIFOS_BASE_URL;
    Logger LOGGER = LoggerFactory.getLogger(MifosWebclient.class);

    @Bean(name = "mifos_webclient")
    public WebClient MifosWebClient(){
        return WebClient.builder()
                .baseUrl(MIFOS_BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter((request, next) -> {
                    LOGGER.warn("Request Mifos URI api {}",request.url());
                    LOGGER.info("Request Mifos Headers {}",request.headers());
                    LOGGER.info("Request Mifos Body {}",request.body());
                    return next.exchange(request);
                })
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setBasicAuth("mifos", "password");
                    httpHeaders.set("Fineract-Platform-TenantId", "default");
                })
                .build();
    }
}
