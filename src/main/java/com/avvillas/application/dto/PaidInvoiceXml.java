package com.avvillas.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Factura pagada del {@link PmtNotificationRequestXml} o reverso del {@link PmtRollbackRequestXml}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "PaidInvoices")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaidInvoiceXml {

    /**
     * Numero del convenio de la factura (En caso de ser multifacturador)
     */
    @XmlElement(name = "AgreementId", required = false, namespace = "")
    private String agreementId;

    /**
     * Numero de factura
     */
    @XmlElement(name = "InvoiceId", required = true, namespace = "")
    private String invoiceId;

    /**
     * Valor total pagado
     */
    @XmlElement(name = "PaidValue", required = true, namespace = "")
    private Double paidValue;

    /**
     * Banco origen del pago
     */
    @XmlElement(name = "BankSrc", required = true, namespace = "")
    private String bankSrc;

    /**
     * Numero de autorizacion del banco
     */
    @XmlElement(name = "BankAuthCode", required = true, namespace = "")
    private Integer bankAuthCode;

    /**
     * Detalle de valores de la factura pagada
     */
    @XmlElement(name = "ValuesDetail", required = false, namespace = "")
    private List<ValueDetailXml> valuesDetails;
}
