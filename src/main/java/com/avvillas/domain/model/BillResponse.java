package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Respuesta de una factura desde el convenio hacia ATH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {

    /**
     * Código de aceptación
     */
    private String status;

    /**
     * Id de la solicitud (el mismo del {@link BillRequest} recibido)
     */
    private String requestId;

    /**
     * Descripcion o mensaje del {@link #status}
     */
    private String message;

    /**
     * Lista con las facturas
     * Opcional Si {@link #status} es diferente a 0
     */
    private List<Invoice> invoices;
}
