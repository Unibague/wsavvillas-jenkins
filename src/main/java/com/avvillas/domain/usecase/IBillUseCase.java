package com.avvillas.domain.usecase;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;

/**
 * Caso de uso para la consulta de una factura
 */
public interface IBillUseCase extends ITransactionHistoryLogUseCase {

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequestXml XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    public BillResponseXml getBill(BillRequestXml billRequestXml);

}
