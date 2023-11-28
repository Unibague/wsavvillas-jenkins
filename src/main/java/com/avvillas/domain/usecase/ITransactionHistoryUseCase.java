package com.avvillas.domain.usecase;


/**
 * Caso de uso para el historial de transacciones
 */
public interface ITransactionHistoryUseCase {

    /**
     * Guarda un log de la peticion en base de datos
     * @param request Peticion a guardar
     */
    public <T> void insertRequestHistory(T request);

    /**
     * Guarda un log de la respuesta en base de datos
     * @param response Respuesta a guardar
     */
    public <T> void insertResponseHistory(T response);
}
