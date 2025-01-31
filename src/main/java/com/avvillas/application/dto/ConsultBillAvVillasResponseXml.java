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
@XmlRootElement(name = "os_consultarFacturaEstandar")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultBillAvVillasResponseXml {

    /**
     * Codigo del banco que realiza la transaccion.
     * AvVillas siempre es 1052
     */
    @XmlElement(name = "codBancoOrigen")
    private Integer bankCodeOrigin;

    /**
     * Codigo que indica el canal por el cual se realiza la transaccion
     */
    @XmlElement(name = "codCanal")
    private String channelCode;

    /**
     * Numero de cuenta con la que se realiza la transaccion
     */
    @XmlElement(name = "nroProducto")
    private String productNumber;

    /**
     * Codigo de la oficina en la cual se realiza la transaccion.
     */
    @XmlElement(name = "codOficinaOrigen")
    private Integer officeCodeOrigin;

    /**
     * Codigo de la ciudad desde la que se realiza la transaccion
     */
    @XmlElement(name = "codCiudad")
    private String cityCode;

    /**
     * Fecha en la cual se realiza la transaccion.
     * En formato YYYYMMDD ejemplo 20110526
     */
    @XmlElement(name = "fechaTransaccion")
    private String transactionDate;

    /**
     * Hora en la cual se realiza la transaccion.
     * En formato HHMMSS 130452
     */
    @XmlElement(name = "horaTransaccion")
    private String transactionHour;

    /**
     * Fecha en la cual se hará efectiva la transaccion.
     * En formato YYYYMMDD ejemplo 20110526
     */
    @XmlElement(name = "fechaCompensacion")
    private String compensationDate;

    /**
     * Codigo de respuesta
     * 0: Transaccion exitosa
     * 1: Formato referencia invalido
     * 2: Factura no existe
     * 3: Factura vencida
     * 99: Error en el sistema
     */
    @XmlElement(name = "codRespuesta")
    private Integer responseCode;

    /**
     * Mensaje de respuesta
     * 0: Transaccion exitosa
     * 1: Formato referencia invalido
     * 2: Factura no existe
     * 3: Factura vencida
     * 99: Error en el sistema
     */
    @XmlElement(name = "mensajeRespuesta")
    private String responseMessage;

    /**
     * Valor total de la factura.
     */
    @XmlElement(name = "valorTotal")
    private String totalValue;

    /**
     * Fecha en la cual la factura expira.
     * En formato YYYYMMDD ejemplo 20110526.
     */
    @XmlElement(name = "fechaVencimiento")
    private String expirationDate;
}
