package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Objeto de dominio para la peticion de Notificacion de pago de una factura desde AvVillas hacia el convenio
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayBillAvVillasRequest {

    /**
     * Codigo del banco que realiza la transaccion.
     * AvVillas siempre es 1052
     */
    private Integer bankCodeOrigin;

    /**
     * Codigo que indica el canal por el cual se realiza la transaccion
     */
    private String channelCode;

    /**
     * Numero de cuenta con la que se realiza la transaccion
     */
    private String productNumber;

    /**
     * Codigo de la oficina en la cual se realiza la transaccion.
     */
    private Integer officeCodeOrigin;

    /**
     * Codigo de la ciudad desde la que se realiza la transaccion
     */
    private String cityCode;

    /**
     * Fecha en la cual se realiza la transaccion.
     * En formato YYYYMMDD ejemplo 20110526
     */
    private LocalDateTime transactionDate;

    /**
     * Hora en la cual se realiza la transaccion.
     * En formato HHMMSS 130452
     */
    private LocalTime transactionHour;

    /**
     * Fecha en la cual se hará efectiva la transacción.
     * En formato YYYYMMDD ejemplo 20110526
     */
    private LocalDateTime compensationDate;

    /**
     * Primera referencia que identifica la factura con la que se está realizando la transaccion.
     */
    private String referenceOne;

    /**
     * Segunda referencia que identifica la factura con la que se está realizando la transaccion.
     */
    private String referenceTwo;

    /**
     * Tercera referencia que identifica la factura con la que se está realizando la transaccion.
     */
    private String referenceThree;

    /**
     * Cuarta referencia que identifica la factura con la que se está realizando la transaccion.
     */
    private String referenceFour;

    /**
     * Valor en efectivo por el cual se hace la transaccion.
     */
    private Double cashValue;

    /**
     * Valor en cheque por el cual se hace la transaccion.
     */
    private Double chequeValue;

    /**
     * Valor total por el cual se hace la transaccion. Suma entre el valor en efectivo y el valor en cheque.
     */
    private Double totalValue;

    /**
     * Este campo se reporta en Cero
     */
    private Integer authorizationNumber;

}
