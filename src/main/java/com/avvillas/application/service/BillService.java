package com.avvillas.application.service;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.application.mapper.IBillRequestMapper;
import com.avvillas.application.mapper.IBillResponseMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.usecase.IBillUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
     * Mapper para BillRequest
     */
    private final IBillRequestMapper iBillRequestMapper;

    /**
     * Mapper para BillResponse
     */
    private final IBillResponseMapper iBillResponseMapper;

    @Inject
    public BillService(IAtlanteFeign atlanteFeign, IBillRequestMapper iBillRequestMapper, IBillResponseMapper iBillResponseMapper) {
        this.atlanteFeign = atlanteFeign;
        this.iBillRequestMapper = iBillRequestMapper;
        this.iBillResponseMapper = iBillResponseMapper;
    }

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequestXml Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponseXml getBill(BillRequestXml billRequestXml) {
        BillResponse billJson = atlanteFeign.getBill(iBillRequestMapper.toBillRequest(billRequestXml));
        billJson.setRequestId(billRequestXml.getRequestId());
        return iBillResponseMapper.toBillResponseXml(billJson);
    }


}
