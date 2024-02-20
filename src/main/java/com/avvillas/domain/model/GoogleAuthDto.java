package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto del formato de la respuesta de google al verificar un accessToken
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAuthDto {

    /**
     * Email de la persona del token
     */
    private String email;

    /**
     * Expiracion del token en segundos
     */
    private Integer expires_in;

    /**
     * Error del token
     */
    private String error;

    /**
     * Descripcion del error del token
     */
    private String error_description;
}
