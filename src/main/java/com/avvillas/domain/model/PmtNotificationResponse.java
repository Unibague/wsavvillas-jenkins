package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta del pago de una factura desde el convenio hacia ATH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PmtNotificationResponse {

    /**
     * Codigo de aceptacion 0,1,82,83,84
     */
    private String status;

    /**
     * Id de la solicitud (la misma de {@link PmtNotificationRequest})
     */
    private String requestId;

    /**
     * Descripcion del {@link #status}
     */
    private String message;

    /**
     * Numero de autorizacion del convenio
     */
    private String partnerAuthCode;
}
