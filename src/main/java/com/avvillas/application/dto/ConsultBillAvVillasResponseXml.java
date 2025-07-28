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
 * XML de objeto para Respuesta de una factura desde el convenio hacia AvVillas
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "os_consultarFacturaEstandarResponse", namespace = "http://organizacion.com/wsEstandar/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultBillAvVillasResponseXml {
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
    @XmlElement(name = "codRespuesta")
    private Integer responseCode;
    @XmlElement(name = "mensajeRespuesta")
    private String responseMessage;
    @XmlElement(name = "valorTotal")
    private String totalValue;
    @XmlElement(name = "fechaVencimiento")
    private String expirationDate;
}
