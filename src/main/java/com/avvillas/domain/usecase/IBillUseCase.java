package com.avvillas.domain.usecase;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;

/**
 * Caso de uso para la consulta de una factura
 */
public interface IBillUseCase {

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequestXml Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    public BillResponseXml getBill(BillRequestXml billRequestXml);

    /**
     * Guarda un log de la peticion en base de datos
     * @param billRequest Peticion a guardar
     */
    public void insertBillRequestHistory(BillRequest billRequest);

    /**
     * Guarda un log de la respuesta en base de datos
     * @param billResponse Respuesta a guardar
     */
    public void insertBillResponseHistory(BillResponse billResponse);
}
