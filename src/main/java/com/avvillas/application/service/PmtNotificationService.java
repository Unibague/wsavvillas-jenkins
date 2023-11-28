package com.avvillas.application.service;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;
import com.avvillas.application.mapper.IPmtNotificationRequestMapper;
import com.avvillas.application.mapper.IPmtNotificationResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.PmtNotificationRequest;
import com.avvillas.domain.model.PmtNotificationResponse;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IPmtNotificationUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 *
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

        insertRequestHistory(pmtNotificationRequest);

        PmtNotificationResponse pmtNotificationResponseJson = atlanteFeign.sendPmtNotification(pmtNotificationRequest);

        insertResponseHistory(pmtNotificationResponseJson);

        return iPmtNotificationResponseMapper.toPmtNotificationResponseXml(pmtNotificationResponseJson);
    }

    @Override
    public <T> void insertRequestHistory(T request) {

    }

    @Override
    public <T> void insertResponseHistory(T response) {

    }
}
