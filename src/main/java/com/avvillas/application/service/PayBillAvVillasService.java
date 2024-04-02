package com.avvillas.application.service;

import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.application.mapper.IPayBillRequestMapper;
import com.avvillas.application.mapper.IPayBillResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.MessagesLog;
import com.avvillas.domain.model.PayBillAvVillasRequest;
import com.avvillas.domain.model.PayBillAvVillasResponse;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Servicio para la notificacion de pago de una factura desde AvVillas
 */
@ApplicationScoped
public class PayBillAvVillasService implements IPayBillAvVillasUseCase {

    /**
     * API de Atlante
     */
    private final IAtlanteFeign atlanteFeign;

    /**
     * Mapper para PayBillAvVillasRequest
     */
    private final IPayBillRequestMapper iPayBillRequestMapper;

    /**
     * Mapper para PayBillAvVillasResponse
     */
    private final IPayBillResponseMapper iPayBillResponseMapper;

    /**
     * Mapper para las TransactionHistory
     */
    private final ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper;

    /**
     * Repositorio para las TransactionHistory
     */
    private final ITransactionHistoryRepository iTransactionHistoryRepository;


    public PayBillAvVillasService(IAtlanteFeign atlanteFeign, IPayBillRequestMapper iPayBillRequestMapper, IPayBillResponseMapper iPayBillResponseMapper, ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper, ITransactionHistoryRepository iTransactionHistoryRepository) {
        this.atlanteFeign = atlanteFeign;
        this.iPayBillRequestMapper = iPayBillRequestMapper;
        this.iPayBillResponseMapper = iPayBillResponseMapper;
        this.iTransactionHistoryDtoMapper = iTransactionHistoryDtoMapper;
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
    }

    /**
     * Guarda la notificacion de pago que envia el banco de una factura, guardando los logs de la transaccion
     * @param payBillRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @Override
    public PayBillAvVillasResponseXml payBill(PayBillAvVillasRequestXml payBillRequestXml) {
        PayBillAvVillasRequest payBillAvVillasRequest = iPayBillRequestMapper.toPayBillAvVillasRequest(payBillRequestXml);
        payBillAvVillasRequest.setTransactionDate(LocalDateTime.now());

        insertRequestHistory(payBillAvVillasRequest);

        PayBillAvVillasResponse payBillAvVillasResponseJson = new PayBillAvVillasResponse();

        try {
            //payBillAvVillasResponseJson = atlanteFeign.payBillAvVillas(payBillAvVillasRequest);
            payBillAvVillasResponseJson.setResponseCode(0);
            payBillAvVillasResponseJson.setResponseMessage("Transacción exitosa");
            payBillAvVillasResponseJson = mapperResponse(payBillAvVillasRequest, payBillAvVillasResponseJson);
            insertResponseHistory(payBillAvVillasResponseJson, null);
        } catch (Exception e) {
            payBillAvVillasResponseJson = mapperResponse(payBillAvVillasRequest, payBillAvVillasResponseJson);
            payBillAvVillasResponseJson.setResponseCode(99);
            payBillAvVillasResponseJson.setResponseMessage("Error en el sistema");
            insertResponseHistory(payBillAvVillasResponseJson, e.getMessage());
        }

        return iPayBillResponseMapper.toPayBillAvVillasResponse(payBillAvVillasResponseJson);
    }

    @Override
    public <T> void insertRequestHistory(T request) {
        PayBillAvVillasRequest payBillAvVillasRequest = (PayBillAvVillasRequest) request;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(payBillAvVillasRequest);
        iTransactionHistoryRepository.insert(transaction).subscribe().with(
                result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" PayBillAvVillasRequestHistory")),
                failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" PayBillAvVillasRequestHistory: " + failure.getMessage()))
        );
    }

    @Override
    public <T> void insertResponseHistory(T response, String exceptionMessage) {
        PayBillAvVillasResponse payBillAvVillasResponse = (PayBillAvVillasResponse) response;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(payBillAvVillasResponse);

        if (exceptionMessage != null) {
            transaction.setException(exceptionMessage);
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()));
            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" excepcion PayBillAvVillasResponseHistory")),
                    failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" excepcion PayBillAvVillasResponseHistory: " + failure.getMessage()))
            );
            return;
        }

        if (transaction.getNumberStatus() == 0) {
            transaction.setMessageStatus("Exito: ".concat(" Factura pagada correctamente"));
        } else if (transaction.getNumberStatus() == 2) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (No se encontro la factura)"));
        } else if (transaction.getNumberStatus() == 4) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (Factura pagada en el pasado"));
        } else if (transaction.getNumberStatus() == 5) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (El valor de la factura en el sistema es diferente al enviado"));
        }

        iTransactionHistoryRepository.insert(transaction).subscribe().with(
                result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" PayBillAvVillasResponseHistory")),
                failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" PayBillAvVillasResponseHistory: " + failure.getMessage()))
        );
    }

    /**
     * Mappea los atributos homonimos de request y response PayBill
     * @param payBillAvVillasRequest Request source donde estan los datos
     * @param payBillAvVillasResponse Response target donde van los datos
     * @return Response con los atributos mappeados
     */
    private PayBillAvVillasResponse mapperResponse(PayBillAvVillasRequest payBillAvVillasRequest, PayBillAvVillasResponse payBillAvVillasResponse) {
        payBillAvVillasResponse.setBankCodeOrigin(payBillAvVillasRequest.getBankCodeOrigin());
        payBillAvVillasResponse.setChannelCode(payBillAvVillasRequest.getChannelCode());
        payBillAvVillasResponse.setProductNumber(payBillAvVillasRequest.getProductNumber());
        payBillAvVillasResponse.setOfficeCodeOrigin(payBillAvVillasRequest.getOfficeCodeOrigin());
        payBillAvVillasResponse.setCityCode(payBillAvVillasRequest.getCityCode());
        payBillAvVillasResponse.setTransactionDate(LocalDateTime.now());
        payBillAvVillasResponse.setTransactionHour(LocalTime.now());
        payBillAvVillasResponse.setCompensationDate(LocalDateTime.now());
        payBillAvVillasResponse.setReferenceOne(payBillAvVillasRequest.getReferenceOne());
        return payBillAvVillasResponse;
    }
}
