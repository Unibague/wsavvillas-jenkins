package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Factura de {@link BillResponse}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    /**
     * Numero de factura
     */
    private String invoiceId;

    /**
     * Valor total de la factura
     */
    private Double totalValue;

    /**
     * Fecha de expiracion de la factura
     */
    private LocalDate expirationDate;

    /**
     * Fecha de ultimo pago de la factura
     */
    private LocalDate endPaymentDate;

    /**
     * Detalles de los valores de la factura
     */
    private List<ValueDetail> valuesDetail;

    /**
     * Detalles adicionales de la factura
     */
    private List<AdditionalData> additionalData;
}
