package com.avvillas.infrastructure.security;

import com.avvillas.domain.feign.IGoogleAuthFeign;
import com.avvillas.domain.model.GoogleAuthDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Proveedor que verifica el token oauth2 de Google
 */
@ApplicationScoped
public class GoogleAuthProvider {

    /**
     * Api de Google
     */
    private final IGoogleAuthFeign googleAuthFeign;

    @Inject
    public GoogleAuthProvider(IGoogleAuthFeign googleAuthFeign) {
        this.googleAuthFeign = googleAuthFeign;
    }

    /**
     * Obtiene el objeto enviado por la api de google y valida si el token no es invalido
     * @param token Token a validar
     * @return True si el token es valido, false de lo contrario
     */
    public boolean validateToken(String token) {
        GoogleAuthDto contentToken = googleAuthFeign.verifyAccessToken(token);
        return contentToken.getError() != null || contentToken.getEmail().contains("@unibague.edu.co");
    }
}
