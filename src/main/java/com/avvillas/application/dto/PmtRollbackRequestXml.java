package com.avvillas.application.dto;


import com.avvillas.infrastructure.util.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
@XmlRootElement(name = "PmtRollbackRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class PmtRollbackRequestXml {

    /**
     * Id de la solicitud
     */
    @XmlElement(name = "RequestId", required = true, namespace = "")
    private String requestId;

    /**
     * Fecha y hora de la peticion
     */
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "CurrentDatetime", required = false, namespace = "")
    private LocalDateTime currentDateTime;

    /**
     * Fecha y hora del Canal
     */
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "InqDate", required = true, namespace = "")
    private LocalDateTime inqDate;

    /**
     * Lista con las facturas
     */
    @XmlElement(name = "PaidInvoices", required = true, namespace = "")
    private List<PaidInvoiceXml> paidInvoices;

}
