package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    /**
     * Identificador unico de la transaccion
     */
    private Long id;

    /**
     * Identificador de la solicitud
     */
    private String requestId;

    /**
     * Cadena representativa del tipo de busqueda de la factura
     */
    private String searchType;

    /**
     * Numero de factura o cedula de la solicitud
     */
    private String invoiceId;

    /**
     * Tipo de busqueda cuando se consulta una factura o numero de respuesta a la solicitud
     */
    private Integer numberStatus;

    /**
     * Mensaje de respuesta a la solicitud
     */
    private String messageStatus;

    /**
     * Fecha de la solicitud y respuesta
     */
    private LocalDateTime requestDate;

    /**
     * Guarda las excepciones de ser necesario
     */
    private String exception;


}
