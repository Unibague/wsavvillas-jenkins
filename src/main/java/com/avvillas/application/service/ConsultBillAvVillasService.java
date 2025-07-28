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

import java.time.LocalDateTime;
import java.time.LocalTime;

@ApplicationScoped
public class ConsultBillAvVillasService implements IConsultBillAvVillasUseCase {

    private final IAtlanteFeign atlanteFeign;
    private final IConsultBillRequestMapper iConsultBillRequestMapper;
    private final IConsultBillResponseMapper iConsultBillResponseMapper;
    private final ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper;
    private final ITransactionHistoryRepository iTransactionHistoryRepository;

    @Inject
    public ConsultBillAvVillasService(
        IAtlanteFeign atlanteFeign,
        IConsultBillRequestMapper iConsultBillRequestMapper,
        IConsultBillResponseMapper iConsultBillResponseMapper,
        ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper,
        ITransactionHistoryRepository iTransactionHistoryRepository
    ) {
        this.atlanteFeign = atlanteFeign;
        this.iConsultBillRequestMapper = iConsultBillRequestMapper;
        this.iConsultBillResponseMapper = iConsultBillResponseMapper;
        this.iTransactionHistoryDtoMapper = iTransactionHistoryDtoMapper;
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
    }

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

    @Override
    public <T> void insertRequestHistory(T request) {
        ConsultBillAvVillasRequest consultRequest = (ConsultBillAvVillasRequest) request;
        TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(consultRequest);
        iTransactionHistoryRepository.insert(transaction).subscribe().with(
            result -> Log.info(MessagesLog.SUCCESSFULLY_SAVED.getDescription().concat(" ConsultBillAvVillasRequestHistory")),
            failure -> Log.error(MessagesLog.ERROR_SAVED.getDescription().concat(" ConsultBillAvVillasRequestHistory: " + failure.getMessage()))
        );
    }

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

    private ConsultBillAvVillasResponse mapperResponse(
        ConsultBillAvVillasRequest billAvVillasRequest,
        ConsultBillAvVillasResponse billAvVillasResponse
    ) {
        billAvVillasResponse.setBankCodeOrigin(billAvVillasRequest.getBankCodeOrigin());
        billAvVillasResponse.setChannelCode(billAvVillasRequest.getChannelCode());
        billAvVillasResponse.setProductNumber(billAvVillasRequest.getProductNumber());

        // ✅ Formato de 3 dígitos para codOficinaOrigen
        String formattedOfficeCode = formatToThreeDigits(billAvVillasRequest.getOfficeCodeOrigin());
        billAvVillasResponse.setOfficeCodeOrigin(formattedOfficeCode);

        billAvVillasResponse.setCityCode(billAvVillasRequest.getCityCode());
        billAvVillasResponse.setTransactionDate(LocalDateTime.now());
        billAvVillasResponse.setTransactionHour(LocalTime.now());
        billAvVillasResponse.setCompensationDate(LocalDateTime.now());
        billAvVillasResponse.setReferenceOne(billAvVillasRequest.getReferenceOne());

        return billAvVillasResponse;
    }

    private String formatToThreeDigits(String value) {
        if (value == null || value.trim().isEmpty()) return "000";
        try {
            return String.format("%03d", Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return "000";
        }
    }
}
