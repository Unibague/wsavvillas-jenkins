package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Factura pagada del {@link PmtNotificationRequest} o reverso del {@link PmtRollbackRequest}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaidInvoice {

    /**
     * Numero del convenio de la factura (En caso de ser multifacturador)
     */
    private String agreementId;

    /**
     * Numero de factura
     */
    private String invoiceId;

    /**
     * Valor total pagado
     */
    private Double paidValue;

    /**
     * Banco origen del pago
     */
    private String bankSrc;

    /**
     * Numero de autorizacion del banco
     */
    private Integer bankAuthCode;

    /**
     * Detalle de valores de la factura pagada
     */
    private List<ValueDetail> valuesDetails;
}
