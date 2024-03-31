package com.avvillas.application.service;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.mapper.IConsultBillRequestMapper;
import com.avvillas.application.mapper.IConsultBillResponseMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.ConsultBillAvVillasRequest;
import com.avvillas.domain.model.ConsultBillAvVillasResponse;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Inject
    public ConsultBillAvVillasService(IAtlanteFeign atlanteFeign, IConsultBillRequestMapper iConsultBillRequestMapper, IConsultBillResponseMapper iConsultBillResponseMapper) {
        this.atlanteFeign = atlanteFeign;
        this.iConsultBillRequestMapper = iConsultBillRequestMapper;
        this.iConsultBillResponseMapper = iConsultBillResponseMapper;
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
        // return new ConsultBillAvVillasResponseXml(consultBillRequestXml.getBankCodeOrigin(), "asd", "asd", 1234, "3423", "12223", "12r134", "123123", 423, "asfas", "43534", "234234");
        return iConsultBillResponseMapper.toConsultBillAvVillasResponseXml(billAvVillasResponseJson);
    }

    @Override
    public <T> void insertRequestHistory(T request) {

    }

    @Override
    public <T> void insertResponseHistory(T response, String exceptionMessage) {

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
        return billAvVillasResponse;
    }
}
