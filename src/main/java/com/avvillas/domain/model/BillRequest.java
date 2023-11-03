package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Consulta de una factura desde ATH hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {

    /**
     * Id de la solicitud
     */
    private String requestId;

    /**
     * Tipo de busqueda de la factura
     */
    private Integer searchType;

    /**
     * Numero de factura o Numero de identificacion cuando {@link #searchType} sea 3
     */
    private String invoiceId;

    /**
     * Id del convenio
     */
    private Integer agreementId;

    /**
     * Fecha y hora de la peticion
     */
    private LocalDateTime currentDateTime;

    /**
     * Fecha y hora del Canal
     */
    private LocalDateTime inqDate;

    /**
     * Periodo de consulta
     */
    private String inqPeriod;

    /**
     * Lista con más campos de búsqueda
     */
    private List<Reference> references;
}
