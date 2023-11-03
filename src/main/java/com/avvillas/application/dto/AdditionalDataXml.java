package com.avvillas.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Detalles adicionales de una factura de {@link InvoiceXml}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "AdditionalData")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalDataXml {

    /**
     * Nombre del detalle adicional
     */
    @XmlElement(name = "Name", required = true, namespace = "")
    private String name;

    /**
     * Descripción del detalle adicional
     */
    @XmlElement(name = "Message", required = true, namespace = "")
    private String message;
}
