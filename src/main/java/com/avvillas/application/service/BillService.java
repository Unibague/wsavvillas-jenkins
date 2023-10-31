package com.avvillas.application.service;

import com.avvillas.domain.dto.BillRequestDto;
import com.avvillas.domain.dto.BillResponseDto;
import com.avvillas.domain.usecase.IBillUseCase;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servicio para la consulta de una factura
 */
@ApplicationScoped
public class BillService implements IBillUseCase {

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponseDto getBill(BillRequestDto billRequest) {
        return null;
    }



}
