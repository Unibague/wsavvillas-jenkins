package com.avvillas.application.service;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.mapper.IConsultBillRequestMapper;
import com.avvillas.application.mapper.IConsultBillResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.ConsultBillAvVillasRequest;
import com.avvillas.domain.model.ConsultBillAvVillasResponse;
import com.avvillas.domain.model.MessagesLog;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio para la consulta de una factura desde AvVillas
 */
@ApplicationScoped
public class ConsultBillAvVillasService implements IConsultBillAvVillasUseCase {

    /**
     * API de Atlante
     */
    private final IAtlanteFeign atlanteFeign;

    /**
     * Mapper para ConsultBillRequest
     */
    private final IConsultBillRequestMapper iConsultBillRequestMapper;

    /**
     * Mapper para ConsultBillResponse
     */
    private final IConsultBillResponseMapper iConsultBillResponseMapper;

    /**
     * Mapper para las TransactionHistory
     */
    private final ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper;

    /**
     * Repositorio para las TransactionHistory
     */
    private final ITransactionHistoryRepository iTransactionHistoryRepository;


    @Inject
    public ConsultBillAvVillasService(IAtlanteFeign atlanteFeign, IConsultBillRequestMapper iConsultBillRequestMapper, IConsultBillResponseMapper iConsultBillResponseMapper, ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper, ITransactionHistoryRepository iTransactionHistoryRepository) {
        this.atlanteFeign = atlanteFeign;
        this.iConsultBillRequestMapper = iConsultBillRequestMapper;
        this.iConsultBillResponseMapper = iConsultBillResponseMapper;
        this.iTransactionHistoryDtoMapper = iTransactionHistoryDtoMapper;
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
    }

    /**
     * Devuelve la información de una factura solicitada, guardando los logs de la transaccion
     * @param consultBillRequestXml XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @Override
    public ConsultBillAvVillasResponseXml consultBill(ConsultBillAvVillasRequestXml consultBillRequestXml) {
        ConsultBillAvVillasRequest billAvVillasRequest = iConsultBillRequestMapper.toConsultBillAvVillasRequest(consultBillRequestXml);
        billAvVillasRequest.setTransactionDate(LocalDateTime.now());

        insertRequestHistory(billAvVillasRequest);

        ConsultBillAvVillasResponse billAvVillasResponseJson = new ConsultBillAvVillasResponse();

        try {
            billAvVillasResponseJson = atlanteFeign.consultBillAvVillas(billAvVillasRequest);

            billAvVillasResponseJson = mapperResponse(billAvVillasRequest, billAvVillasResponseJson);
            insertResponseHistory(billAvVillasResponseJson, null);
        } catch (Exception e) {
            billAvVillasResponseJson = mapperResponse(billAvVillasRequest, billAvVillasResponseJson);
            billAvVillasResponseJson.setResponseCode(99);
            billAvVillasResponseJson.setResponseMessage("Error en el sistema");
            insertResponseHistory(billAvVillasResponseJson, e.getMessage());
        }
        return iConsultBillResponseMapper.toConsultBillAvVillasResponseXml(billAvVillasResponseJson);
    }

    /**
     * Guarda un log de la peticion en base de datos
     * @param request ConsultBillAvVillasRequest a guardar
     */
    @Override
    public <T> void insertRequestHistory(T request) {
        ConsultBillAvVillasRequest consultRequest = (ConsultBillAvVillasRequest) request;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(consultRequest);
        iTransactionHistoryRepository.insert(transaction).subscribe().with(
                result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" ConsultBillAvVillasRequestHistory")),
                failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" ConsultBillAvVillasRequestHistory: " + failure.getMessage()))
        );
    }

    /**
     * Guarda un log de la respuesta en base de datos
     * @param response BillResponse a guardar
     * @param exceptionMessage Mensaje de excepcion en caso de error
     */
    @Override
    public <T> void insertResponseHistory(T response, String exceptionMessage) {
        ConsultBillAvVillasResponse consultResponse = (ConsultBillAvVillasResponse) response;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(consultResponse);


        if (exceptionMessage != null) {
            transaction.setException(exceptionMessage);
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()));
            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" excepcion ConsultBillAvVillasResponseHistory")),
                    failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" excepcion ConsultBillAvVillasResponseHistory: " + failure.getMessage()))
            );
            return;
        }

        if (transaction.getNumberStatus() == 0) {
            transaction.setMessageStatus("Exito: ".concat("Factura consultada correctamente"));
        } else if (transaction.getNumberStatus() == 2) {
            transaction.setMessageStatus("Error: ".concat(transaction.getMessageStatus()).concat(" (No se encontro la factura)"));
        }

        iTransactionHistoryRepository.insert(transaction).subscribe().with(
                result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" ConsultBillAvVillasResponseHistory")),
                failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" ConsultBillAvVillasResponseHistory: " + failure.getMessage()))
        );

    }

    /**
     * Mappea los atributos homonimos de request y response ConsultBill
     * @param billAvVillasRequest Request source donde estan los datos
     * @param billAvVillasResponse Response target donde van los datos
     * @return Response con los atributos mappeados
     */
    private ConsultBillAvVillasResponse mapperResponse(ConsultBillAvVillasRequest billAvVillasRequest, ConsultBillAvVillasResponse billAvVillasResponse) {
        billAvVillasResponse.setBankCodeOrigin(billAvVillasRequest.getBankCodeOrigin());
        billAvVillasResponse.setChannelCode(billAvVillasRequest.getChannelCode());
        billAvVillasResponse.setProductNumber(billAvVillasRequest.getProductNumber());
        billAvVillasResponse.setOfficeCodeOrigin(billAvVillasRequest.getOfficeCodeOrigin());
        billAvVillasResponse.setCityCode(billAvVillasRequest.getCityCode());
        billAvVillasResponse.setTransactionDate(LocalDateTime.now());
        billAvVillasResponse.setTransactionHour(LocalTime.now());
        billAvVillasResponse.setCompensationDate(LocalDateTime.now());
        billAvVillasResponse.setReferenceOne(billAvVillasRequest.getReferenceOne());
        return billAvVillasResponse;
    }
}
