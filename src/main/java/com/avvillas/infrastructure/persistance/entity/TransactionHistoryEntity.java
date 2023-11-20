package com.avvillas.infrastructure.persistance.entity;

import io.vertx.mutiny.sqlclient.Row;

import java.time.LocalDateTime;

/**
 * Entidad de TransactionHistory
 */
public class TransactionHistoryEntity {

    /**
     * Identificador unico de la transaccion
     */
    public Long id;

    /**
     * Identificador de la solicitud
     */
    public String requestId;

    /**
     * Tipo de busqueda de la factura
     */
    public String searchType;

    /**
     * Numero de factura o cedula de la solicitud
     */
    public String invoiceId;

    /**
     * Numero de solicitud cuando se consulta una factura o numero de respuesta a la solicitud
     */
    public Integer numberStatus;

    /**
     * Mensaje de respuesta a la solicitud
     */
    public String messageStatus;

    /**
     * Fecha de la solicitud y respuesta
     */
    public LocalDateTime requestDate;

    /**
     * Guarda las excepciones de ser necesario
     */
    public String exception;


    /**
     * Crea una entidad con base en una fila que llega desde base de datos
     * @param row Fila consultada desde base de datos
     * @return Entidad Transaction creada
     */
    public static TransactionHistoryEntity from(Row row) {
        TransactionHistoryEntity newTransaction = new TransactionHistoryEntity();
        newTransaction.id = row.getLong("id");
        newTransaction.requestId = row.getString("request_id");
        newTransaction.searchType = row.getString("search_type");
        newTransaction.invoiceId = row.getString("invoice_id");
        newTransaction.numberStatus = row.getInteger("number_status");
        newTransaction.messageStatus = row.getString("message_status");
        newTransaction.requestDate = row.getLocalDateTime("request_date");
        newTransaction.exception = row.getString("exception");

        return newTransaction;
    }

}
