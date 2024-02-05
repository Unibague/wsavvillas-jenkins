package com.avvillas.application.service;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.application.mapper.IBillRequestMapper;
import com.avvillas.application.mapper.IBillResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.MessagesLog;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IBillUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.cxf.annotations.UseAsyncMethod;

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
        billDto.setCurrentDateTime(LocalDateTime.now());
        insertRequestHistory(billDto);

        BillResponse billResponseJson = new BillResponse();

        try {
            billResponseJson = atlanteFeign.getBill(billDto);
            billResponseJson.setRequestId(billDto.getRequestId());
            billResponseJson.setCurrentDateTime(LocalDateTime.now());
            // Si la busqueda es por cedula, la agrega en la respuesta para enlazar las facturas a esa cedula
            if (billDto.getSearchType() == 1 || billDto.getSearchType() == 3) {
                billResponseJson.setInvoicesCard(billDto.getInvoiceId());
            }
            insertResponseHistory(billResponseJson, null);
        } catch (Exception e) {
            billResponseJson.setRequestId(billDto.getRequestId());
            billResponseJson.setCurrentDateTime(LocalDateTime.now());
            billResponseJson.setStatus("1");
            billResponseJson.setMessage("Error inesperado");
            insertResponseHistory(billResponseJson, e.getMessage());
        }

        return iBillResponseMapper.toBillResponseXml(billResponseJson);
    }

    /**
     * Guarda un log de la peticion en base de datos
     * @param request BillRequest a guardar
     */
    @Override
    public <T> void insertRequestHistory(T request) {
        BillRequest billRequest = (BillRequest) request;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billRequest);
        iTransactionHistoryRepository.insert(transaction).subscribe().with(
                result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" BillRequestHistory")),
                failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" BillRequestHistory: " + failure.getMessage()))
        );
    }

    /**
     * Guarda un log de la respuesta en base de datos
     * @param response BillResponse a guardar
     */
    @Override
    public <T> void insertResponseHistory(T response, String exceptionMessage) {
        BillResponse billResponse = (BillResponse) response;

        if (exceptionMessage != null) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billResponse);
            transaction.setException(exceptionMessage);
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()));
            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" excepcion BillResponseHistory")),
                    failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" excepcion BillResponseHistory: " + failure.getMessage()))
            );
            return;
        }

        // Si no hay facturas guarda el mensaje de Factura no existe
        if (billResponse.getInvoices().isEmpty()) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billResponse);
            if (transaction.getNumberStatus() == 82) {
                transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (No se encontro la factura)"));
            }
            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" BillResponseHistory")),
                    failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" BillResponseHistory: " + failure.getMessage()))
            );
            return;
        }

        // Guarda un log por cada factura recibida
        for (int i = 0; i < billResponse.getInvoices().size(); i++) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billResponse);
            transaction.setInvoiceId(billResponse.getInvoices().get(i).getInvoiceId());
            transaction.setInvoiceCard(billResponse.getInvoicesCard());
            transaction.setMessageStatus("Exito: ".concat(" Factura consultada correctamente"));
            transaction.setPaidValue(billResponse.getInvoices().get(i).getTotalValue());

            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" BillResponseHistory")),
                    failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" BillResponseHistory: " + failure.getMessage()))
            );
        }
    }

}
