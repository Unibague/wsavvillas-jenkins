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
     * Cedula a la que pertenece una factura
     */
    public String invoiceCard;

    /**
     * Valor de la factura
     */
    public Double paidValue;

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
     * Campo para indicar si la peticion la hace la universidad o el banco
     */
    public String whoSendPetition;

    /**
     * Campo que indica a que web service pertenece la transaccion
     */
    public String strWebService;

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
        newTransaction.invoiceCard = row.getString("invoice_card");
        newTransaction.paidValue = row.getDouble("paid_value");
        newTransaction.numberStatus = row.getInteger("number_status");
        newTransaction.messageStatus = row.getString("message_status");
        newTransaction.requestDate = row.getLocalDateTime("request_date");
        newTransaction.whoSendPetition = row.getString("who_send_petition");
        newTransaction.strWebService = row.getString("str_web_service");
        newTransaction.exception = row.getString("exception");

        return newTransaction;
    }

}
