package com.avvillas.application.service;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.application.mapper.IBillRequestMapper;
import com.avvillas.application.mapper.IBillResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IBillUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

/**
 * Servicio para la consulta de una factura
 */
@ApplicationScoped
public class BillService implements IBillUseCase {

    /**
     * API de Atlante
     */
    private final IAtlanteFeign atlanteFeign;

    /**
     * Repositorio para las TransactionHistory
     */
    private final ITransactionHistoryRepository iTransactionHistoryRepository;

    /**
     * Mapper para BillRequest
     */
    private final IBillRequestMapper iBillRequestMapper;

    /**
     * Mapper para BillResponse
     */
    private final IBillResponseMapper iBillResponseMapper;

    /**
     * Mapper para las TransactionHistory
     */
    private final ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper;

    @Inject
    public BillService(IAtlanteFeign atlanteFeign, IBillRequestMapper iBillRequestMapper, IBillResponseMapper iBillResponseMapper, ITransactionHistoryRepository iTransactionHistoryRepository, ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper) {
        this.atlanteFeign = atlanteFeign;
        this.iBillRequestMapper = iBillRequestMapper;
        this.iBillResponseMapper = iBillResponseMapper;
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
        this.iTransactionHistoryDtoMapper = iTransactionHistoryDtoMapper;
    }

    /**
     * Devuelve la información de una factura solicitada, guardando los logs de la transaccion
     * @param billRequestXml XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @Override
    public BillResponseXml getBill(BillRequestXml billRequestXml) {
        BillRequest billDto = iBillRequestMapper.toBillRequest(billRequestXml);

        insertRequestHistory(billDto);

        BillResponse billResponseJson = atlanteFeign.getBill(billDto);
        billResponseJson.setRequestId(billDto.getRequestId());
        billResponseJson.setCurrentDateTime(LocalDateTime.now());

        insertResponseHistory(billResponseJson);

        return iBillResponseMapper.toBillResponseXml(billResponseJson);
    }

    /**
     * Guarda un log de la peticion en base de datos
     * @param request BillRequest a guardar
     */
    @Override
    public <T> void insertRequestHistory(T request) {
        BillRequest billRequest = (BillRequest) request;
        iTransactionHistoryRepository.insert(iTransactionHistoryDtoMapper.toTransaction(billRequest)).subscribe().with(
                result -> Log.info("Resultado exitoso BillRequestHistory"),
                failure -> Log.error("Fallo al guardar BillRequestHistory: " + failure.getMessage())
        );
    }

    /**
     * Guarda un log de la respuesta en base de datos
     * @param response BillResponse a guardar
     */
    @Override
    public <T> void insertResponseHistory(T response) {
        BillResponse billResponse = (BillResponse) response;

        // Si no hay facturas guarda el mensaje de Factura no existe
        if (billResponse.getInvoices().isEmpty()) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billResponse);

            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info("Resultado exitoso BillResponseHistory"),
                    failure -> Log.error("Fallo al guardar BillResponseHistory: " + failure.getMessage())
            );
            return;
        }

        // Guarda un log por cada factura recibida
        for (int i = 0; i < billResponse.getInvoices().size(); i++) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billResponse);
            transaction.setInvoiceId(billResponse.getInvoices().get(i).getInvoiceId());

            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info("Resultado exitoso BillResponseHistory"),
                    failure -> Log.error("Fallo al guardar BillResponseHistory: " + failure.getMessage())
            );
        }
    }

}
