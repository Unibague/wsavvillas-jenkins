package com.avvillas.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Detalles de los valores de una factura de {@link InvoiceDto} o {@link PaidInvoice}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ValuesDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValueDetailDto {

    /**
     * Descripcion del valor detallado
     */
    @XmlElement(name = "Description", required = true, namespace = "")
    private String description;

    /**
     * Valor al que corresponde la descripción
     */
    @XmlElement(name = "Value", required = true, namespace = "")
    private Double value;

    /**
     * Convenio en caso de que el valor sea de subservicio
     */
    @XmlElement(name = "Class", required = false, namespace = "")
    private String classDescription;
}
