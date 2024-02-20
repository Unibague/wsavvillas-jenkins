package com.avvillas.domain.feign;

import com.avvillas.domain.model.GoogleAuthDto;

/**
 * Contrato para la API OAUTH2 de Google
 */
public interface IGoogleAuthFeign {

    /**
     * Consume el endpoint de Google para validar un token de Acceso
     * @return Objeto del token validado
     */
    public GoogleAuthDto verifyAccessToken(String accessToken);
}
