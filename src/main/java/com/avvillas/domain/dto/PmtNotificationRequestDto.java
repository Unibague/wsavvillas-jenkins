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
 * Pago de una factura desde ATH hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "PmtNotificationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class PmtNotificationRequestDto {

    /**
     * Id de la solicitud
     */
    @XmlElement(name = "RequestId", required = true, namespace = "")
    private String requestId;

    /**
     * Fecha y hora de la petición
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
     * Lista con las facturas pagadas
     */
    @XmlElement(name = "PaidInvoices", required = true, namespace = "")
    private List<PaidInvoiceDto> paidInvoices;
}