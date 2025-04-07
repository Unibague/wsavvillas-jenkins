package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Objeto de dominio para Respuesta de una factura desde el convenio hacia AvVillas
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultBillAvVillasResponse {

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
    private String officeCodeOrigin;

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
     * Fecha en la cual se hará efectiva la transaccion.
     * En formato YYYYMMDD ejemplo 20110526
     */
    private LocalDateTime compensationDate;

    /**
     * Codigo de respuesta
     * 0: Transaccion exitosa
     * 1: Formato referencia invalido
     * 2: Factura no existe
     * 3: Factura vencida
     * 99: Error en el sistema
     */
    private Integer responseCode;

    /**
     * Mensaje de respuesta
     * 0: Transaccion exitosa
     * 1: Formato referencia invalido
     * 2: Factura no existe
     * 3: Factura vencida
     * 99: Error en el sistema
     */
    private String responseMessage;

    /**
     * Referencia que identifica la factura con la que se está realizando la transaccion.
     */
    private String referenceOne;

    /**
     * Valor total de la factura.
     */
    private Double totalValue;

    /**
     * Fecha en la cual la factura expira.
     * En formato YYYYMMDD ejemplo 20110526.
     */
    private LocalDateTime expirationDate;
}
