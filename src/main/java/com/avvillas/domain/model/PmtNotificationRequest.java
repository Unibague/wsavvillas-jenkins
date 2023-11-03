package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Pago de una factura desde ATH hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PmtNotificationRequest {

    /**
     * Id de la solicitud
     */
    private String requestId;

    /**
     * Fecha y hora de la petición
     */
    private LocalDateTime currentDateTime;

    /**
     * Fecha y hora del Canal
     */
    private LocalDateTime inqDate;

    /**
     * Lista con las facturas pagadas
     */
    private List<PaidInvoice> paidInvoices;
}
