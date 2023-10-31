package com.avvillas.domain.dto;

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
 * Factura de {@link BillResponseDto}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceDto {

    /**
     * Numero de factura
     */
    @XmlElement(name = "InvoiceId", required = true, namespace = "")
    private String invoiceId;

    /**
     * Valor total de la factura
     */
    @XmlElement(name = "TotalValue", required = true, namespace = "")
    private Double totalValue;

    /**
     * Fecha de expiracion de la factura
     */
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "ExpirationDate", required = true, namespace = "")
    private LocalDateTime expirationDate;

    /**
     * Fecha de ultimo pago de la factura
     */
    @XmlElement(name = "EndPaymentDate", required = false, namespace = "")
    private LocalDateTime endPaymentDate;

    /**
     * Detalles de los valores de la factura
     */
    @XmlElement(name = "ValuesDetail", required = false, namespace = "")
    private List<ValueDetailDto> valuesDetail;

    /**
     * Detalles adicionales de la factura
     */
    @XmlElement(name = "AdditionalData", required = false, namespace = "")
    private List<AdditionalDataDto> additionalData;
}
