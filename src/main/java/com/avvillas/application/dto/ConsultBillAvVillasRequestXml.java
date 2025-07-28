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
 * XML de objeto para Consulta de una factura desde AvVillas hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "oe_consultarFacturaEstandar", namespace = "http://organizacion.com/wsEstandar/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultBillAvVillasRequestXml {
    @XmlElement(name = "codBancoOrigen")
    private Integer bankCodeOrigin;
    @XmlElement(name = "codCanal")
    private String channelCode;
    @XmlElement(name = "nroProducto")
    private String productNumber;
    @XmlElement(name = "codOficinaOrigen")
    private String officeCodeOrigin;
    @XmlElement(name = "codCiudad")
    private String cityCode;
    @XmlElement(name = "fechaTransaccion")
    private String transactionDate;
    @XmlElement(name = "horaTransaccion")
    private String transactionHour;
    @XmlElement(name = "fechaCompensacion")
    private String compensationDate;
    @XmlElement(name = "referencia1")
    private String referenceOne;
    @XmlElement(name = "referencia2")
    private String referenceTwo;
    @XmlElement(name = "referencia3")
    private String referenceThree;
    @XmlElement(name = "referencia4")
    private String referenceFour;
}
