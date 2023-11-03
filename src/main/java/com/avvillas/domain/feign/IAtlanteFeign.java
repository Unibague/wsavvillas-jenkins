package com.avvillas.domain.feign;

import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;

/**
 * Contrato para la API de Atlante
 */
public interface IAtlanteFeign {

    /**
     * Consume el endpoint de Atlante para consultar la factura
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    public BillResponse getBill(BillRequest billRequest);
}
