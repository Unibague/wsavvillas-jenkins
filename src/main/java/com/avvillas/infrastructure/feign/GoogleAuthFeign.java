package com.avvillas.infrastructure.feign;

import com.avvillas.domain.feign.IGoogleAuthFeign;
import com.avvillas.domain.model.GoogleAuthDto;
import io.quarkus.logging.Log;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Implementacion para consumir la API OAUTH2 de Google
 */
@ApplicationScoped
public class GoogleAuthFeign implements IGoogleAuthFeign {

    /**
     * URL OAUTH2 Google
     */
    private static final String URL = "https://oauth2.googleapis.com/tokeninfo?access_token=";

    /**
     * Libreria para realizar peticiones REST
     */
    private final WebClient client;

    @Inject
    public GoogleAuthFeign(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    /**
     * Consume el endpoint de Google para validar un token de Acceso
     * @return Objeto del token validado
     */
    @Override
    public GoogleAuthDto verifyAccessToken(String accessToken) {
        try {
            return client.getAbs(URL+accessToken)
                    .send().await().indefinitely()
                    .bodyAsJson(GoogleAuthDto.class);
        } catch (Exception e) {
            Log.error("Fallo Google OAuth2: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
