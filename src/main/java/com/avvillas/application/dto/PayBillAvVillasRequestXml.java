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
 * XML de objeto para la peticion de Notificacion de pago de una factura desde AvVillas hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "oe_pagarFacturaEstandar" , namespace = "http://organizacion.com/wsEstandar/")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayBillAvVillasRequestXml {

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
     * Fecha en la cual se hará efectiva la transaccion.
     * En formato YYYYMMDD ejemplo 20110526
     */
    @XmlElement(name = "fechaCompensacion")
    private String compensationDate;

    /**
     * Primera referencia que identifica la factura con la que se está realizando la transaccion.
     */
    @XmlElement(name = "referencia1", required = true)
    private String referenceOne;

    /**
     * Segunda referencia que identifica la factura con la que se está realizando la transaccion.
     */
    @XmlElement(name = "referencia2")
    private String referenceTwo;

    /**
     * Tercera referencia que identifica la factura con la que se está realizando la transaccion.
     */
    @XmlElement(name = "referencia3")
    private String referenceThree;

    /**
     * Cuarta referencia que identifica la factura con la que se está realizando la transaccion.
     */
    @XmlElement(name = "referencia4")
    private String referenceFour;

    /**
     * Valor en efectivo por el cual se hace la transaccion.
     * Este valor se envía con 2 ceros al final que son valores decimales.
     */
    @XmlElement(name = "valorEfectivo")
    private String cashValue;

    /**
     * Valor en cheque por el cual se hace la transaccion.
     * Este valor se envía con 2 ceros al final que son valores decimales.
     */
    @XmlElement(name = "valorCheque")
    private String chequeValue;

    /**
     * Valor total por el cual se hace la transaccion. Suma entre el valor en efectivo y el valor en cheque.
     * Este valor se envía con 2 ceros al final que son valores decimales.
     */
    @XmlElement(name = "valorTotal")
    private String totalValue;

    /**
     * Este campo se reporta en Cero
     */
    @XmlElement(name = "nroAutorizacion")
    private Integer authorizationNumber;

}
