package com.avvillas.domain.dto;

import com.avvillas.util.LocalDateTimeAdapter;
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
 * Consulta de una factura desde ATH hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "BillRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillRequestDto {

    /**
     * Id de la solicitud
     */
    @XmlElement(name = "RequestId", required = true, namespace = "")
    private String requestId;

    /**
     * Tipo de busqueda de la factura
     */
    @XmlElement(name = "SearchType", required = false, namespace = "")
    private Integer searchType;

    /**
     * Numero de factura o Numero de identificacion cuando {@link #searchType} sea 3
     */
    @XmlElement(name = "InvoiceId", required = true, namespace = "")
    private String invoiceId;

    /**
     * Id del convenio
     */
    @XmlElement(name = "AgreementId", required = false, namespace = "")
    private Integer agreementId;

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
     * Periodo de consulta
     */
    @XmlElement(name = "InqPeriod", required = false, namespace = "")
    private String inqPeriod;

    /**
     * Lista con más campos de búsqueda
     */
    @XmlElement(name = "References", required = false, namespace = "")
    private List<ReferenceDto> references;
}
