package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Factura a reversar desde ATH hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PmtRollbackRequest {

    /**
     * Id de la solicitud
     */
    private String requestId;

    /**
     * Fecha y hora de la peticion
     */
    private LocalDateTime currentDateTime;

    /**
     * Fecha y hora del Canal
     */
    private LocalDateTime inqDate;

    /**
     * Lista con las facturas
     */
    private List<PaidInvoice> paidInvoices;

}
