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
 * Campos de busqueda adicionales de {@link BillRequestXml}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "References")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReferenceXml {

    /**
     * Nombre de la caracteristica adicional
     */
    @XmlElement(name = "Name", required = true, namespace = "")
    private String name;

    /**
     * Descripcion de la caracteristica adicional
     */
    @XmlElement(name = "Message", required = true, namespace = "")
    private String message;
}
