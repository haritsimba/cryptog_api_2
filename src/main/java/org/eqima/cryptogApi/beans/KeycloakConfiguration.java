package org.eqima.cryptogApi.beans;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@PropertySource(value = "classpath:application.yml")
public class KeycloakConfiguration {

    @Value("${keycloak.configuration.auth-server-url}")
    private String ROOT_URL;
    @Value("${keycloak.configuration.realm}")
    private String REALM_NAME;
    @Value("${keycloak.configuration.client-id}")
    private String CLIENT_ID;
    @Value("${keycloak.configuration.client-secret}")
    private String CLIENT_SECRET;
    @Value("${keycloak.configuration.username}")
    private String USERNAME;
    @Value("${keycloak.configuration.password}")
    private String PASSWORD;
    @Value("${keycloak.configuration.grant-type}")
    private String GRANT_TYPE;

    @Bean(name = "keycloakConfig")
    public RealmResource keycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(ROOT_URL) // URL de Keycloak
                .realm(REALM_NAME) // Utiliser "master" pour les actions admin
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(USERNAME)
                .password(PASSWORD)
                .grantType(GRANT_TYPE)
                .build();
        if(!keycloak.isClosed()){
            return keycloak.realm(REALM_NAME);
        }
        return null;
    }


}
