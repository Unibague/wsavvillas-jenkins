package com.avvillas.application.service;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;
import com.avvillas.application.mapper.IPmtNotificationRequestMapper;
import com.avvillas.application.mapper.IPmtNotificationResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.MessagesLog;
import com.avvillas.domain.model.PmtNotificationRequest;
import com.avvillas.domain.model.PmtNotificationResponse;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IPmtNotificationUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

/**
 * Servicio para el pago de las facturas desde ATH
 */
@ApplicationScoped
public class PmtNotificationService implements IPmtNotificationUseCase {

    /**
     * API de Atlante
     */
    private final IAtlanteFeign atlanteFeign;

    /**
     * Mapper para PmtNotificationRequest
     */
    private final IPmtNotificationRequestMapper iPmtNotificationRequestMapper;

    /**
     * Mapper para PmtNotificationResponse
     */
    private final IPmtNotificationResponseMapper iPmtNotificationResponseMapper;

    /**
     * Repositorio para las TransactionHistory
     */
    private final ITransactionHistoryRepository iTransactionHistoryRepository;

    /**
     * Mapper para las TransactionHistory
     */
    private final ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper;

    @Inject
    public PmtNotificationService(IAtlanteFeign atlanteFeign, IPmtNotificationRequestMapper iPmtNotificationRequestMapper, IPmtNotificationResponseMapper iPmtNotificationResponseMapper, ITransactionHistoryRepository iTransactionHistoryRepository, ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper) {
        this.atlanteFeign = atlanteFeign;
        this.iPmtNotificationRequestMapper = iPmtNotificationRequestMapper;
        this.iPmtNotificationResponseMapper = iPmtNotificationResponseMapper;
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
        this.iTransactionHistoryDtoMapper = iTransactionHistoryDtoMapper;
    }

    /**
     * Guarda la notificacion de pago que envia el banco de una factura, guardando los logs de la transaccion
     * @param pmtNotificationRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @Override
    public PmtNotificationResponseXml sendPmtNotification(PmtNotificationRequestXml pmtNotificationRequestXml) {
        PmtNotificationRequest pmtNotificationRequest = iPmtNotificationRequestMapper.toPmtNotificationRequest(pmtNotificationRequestXml);
        pmtNotificationRequest.setCurrentDateTime(LocalDateTime.now());
        insertRequestHistory(pmtNotificationRequest);

        PmtNotificationResponse pmtNotificationResponseJson = new PmtNotificationResponse();

        try {
            pmtNotificationResponseJson = atlanteFeign.sendPmtNotification(pmtNotificationRequest);
            pmtNotificationResponseJson.setRequestId(pmtNotificationRequest.getRequestId());
            insertResponseHistory(pmtNotificationResponseJson, null);
        } catch (Exception e) {
            pmtNotificationResponseJson.setRequestId(pmtNotificationRequest.getRequestId());
            pmtNotificationResponseJson.setStatus("1");
            pmtNotificationResponseJson.setMessage("Error inesperado");
            insertResponseHistory(pmtNotificationResponseJson, e.getMessage());
        }

        return iPmtNotificationResponseMapper.toPmtNotificationResponseXml(pmtNotificationResponseJson);
    }

    /**
     * Guarda un log de la peticion en base de datos
     * @param request PmtNotificationRequest a guardar
     */
    @Override
    public <T> void insertRequestHistory(T request) {
        PmtNotificationRequest pmtNotificationRequest = (PmtNotificationRequest) request;

        for (int i = 0; i < pmtNotificationRequest.getPaidInvoices().size(); i++) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(pmtNotificationRequest);
            transaction.setInvoiceId(pmtNotificationRequest.getPaidInvoices().get(i).getInvoiceId());
            transaction.setPaidValue(pmtNotificationRequest.getPaidInvoices().get(i).getPaidValue());

            iTransactionHistoryRepository.insert(transaction)
                    .subscribe().with(
                            result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" PmtNotificationRequestHistory")),
                            failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" PmtNotificationRequestHistory: " + failure.getMessage()))
                    );
        }

    }

    /**
     * Guarda un log de la respuesta en base de datos
     * @param response PmtNotificationResponse a guardar
     */
    @Override
    public <T> void insertResponseHistory(T response, String exceptionMessage) {
        PmtNotificationResponse pmtNotificationResponse = (PmtNotificationResponse) response;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(pmtNotificationResponse);
        transaction.setRequestDate(LocalDateTime.now());

        if (exceptionMessage != null) {
            transaction.setException(exceptionMessage);
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()));
            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" excepcion PmtNotificationResponse")),
                    failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" excepcion PmtNotificationResponse: " + failure.getMessage()))
            );
            return;
        }

        if (transaction.getNumberStatus() == 0) {
            transaction.setMessageStatus("Exito: ".concat(" Factura pagada correctamente"));
        } else if (transaction.getNumberStatus() == 1) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()));
        } else if (transaction.getNumberStatus() == 82) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (No se encontro la factura)"));
        } else if (transaction.getNumberStatus() == 84) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (Factura pagada en el pasado"));
        }

        iTransactionHistoryRepository.insert(transaction)
                .subscribe().with(
                        result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" PmtNotificationResponseHistory")),
                        failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" PmtNotificationResponseHistory: " + failure.getMessage()))
                );
    }
}
