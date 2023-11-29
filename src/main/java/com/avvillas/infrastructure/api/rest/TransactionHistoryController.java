package com.avvillas.infrastructure.api.rest;

import com.avvillas.domain.usecase.ITransactionHistoryUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

/**
 * Controlador para los registros del historial de transacciones
 */
@Path("/transactionHistory")
public class TransactionHistoryController {

    /**
     * Caso de uso para el historial de transacciones
     */
    private final ITransactionHistoryUseCase iTransactionHistoryUseCase;

    @Inject
    public TransactionHistoryController(ITransactionHistoryUseCase iTransactionHistoryUseCase) {
        this.iTransactionHistoryUseCase = iTransactionHistoryUseCase;
    }

    /**
     * Devuelve todos los registros del historial de transacciones
     * @return Response con JSON que incluye los registros
     */
    @GET()
    public Response getAll() {
        ResponseBuilder builder = Response.status(200).entity(iTransactionHistoryUseCase.getAll());
        return builder.build();
    }
}
