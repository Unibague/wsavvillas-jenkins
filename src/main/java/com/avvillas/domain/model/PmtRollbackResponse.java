package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta del reverso desde el convenio hacia ATH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PmtRollbackResponse {

    /**
     * Codigo de aceptacion 0,1,82,83,84
     */
    private String status;

    /**
     * Id de la solicitud
     */
    private String requestId;

    /**
     * Descripcion del {@link #status}
     */
    private String message;

    /**
     * Número de autorización del convenio
     */
    private String partnerAuthCode;
}
