package com.avvillas.domain.usecase;

import com.avvillas.domain.dto.BillRequestDto;
import com.avvillas.domain.dto.BillResponseDto;

/**
 * Caso de uso para la consulta de una factura
 */
public interface IBillUseCase {

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    public BillResponseDto getBill(BillRequestDto billRequest);
}
