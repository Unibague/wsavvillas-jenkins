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
 * XML de objeto para respuesta de Notificacion de pago de una factura desde el convenio hacia AvVillas
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "os_pagarFacturaEstandar" , namespace = "http://organizacion.com/wsEstandar/")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayBillAvVillasResponseXml {

    /**
     * Codigo del banco que realiza la transaccion.
     * AvVillas siempre es 1052
     */
    @XmlElement(name = "codBancoOrigen", required = true)
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
    private String officeCodeOrigin;

    /**
     * Codigo de la ciudad desde la que se realiza la transaccion
     */
    @XmlElement(name = "codCiudad")
    private String cityCode;

    /**
     * Fecha en la cual se realiza la transaccion.
     * En formato YYYYMMDD ejemplo 20110526
     */
    @XmlElement(name = "fechaTransaccion", required = true)
    private String transactionDate;

    /**
     * Hora en la cual se realiza la transaccion.
     * En formato HHMMSS 130452
     */
    @XmlElement(name = "horaTransaccion")
    private String transactionHour;

    /**
     * Fecha en la cual se hará efectiva la transacción.
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
     * 4: Factura ya pagada
     * 5: El valor no corresponde
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
     * 4: Factura ya pagada
     * 5: El valor no corresponde
     * 99: Error en el sistema
     */
    @XmlElement(name = "mensajeRespuesta")
    private String responseMessage;

    /**
     * Este campo se reporta en Cero
     */
    @XmlElement(name = "nroAutorizacionRecaudo")
    private Integer authorizationNumber;
}
