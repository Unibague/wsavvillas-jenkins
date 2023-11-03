package com.avvillas.application.service;

import com.avvillas.domain.dto.BillRequestDto;
import com.avvillas.domain.dto.BillResponseDto;
import com.avvillas.domain.dto.BillResponseDtoJson;
import com.avvillas.domain.feign.IAtlante;
import com.avvillas.domain.usecase.IBillUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Servicio para la consulta de una factura
 */
@ApplicationScoped
public class BillService implements IBillUseCase {

    @Inject
    IAtlante atlanteFeign;

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponseDto getBill(BillRequestDto billRequest) {
        System.out.println("voy a entrar el servicio asincronico: ");
        BillResponseDto billJson = atlanteFeign.getBill();
        System.out.println(billJson.getStatus());
        System.out.println(billJson.getMessage());
        System.out.println("salí del servicio asincronico: ");
        return null;
    }



}
