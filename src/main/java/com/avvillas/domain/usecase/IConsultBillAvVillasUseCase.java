package com.avvillas.domain.usecase;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;

/**
 * Caso de uso para la consulta de una factura desde AvVillas
 */
public interface IConsultBillAvVillasUseCase extends ITransactionHistoryLogUseCase {

    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequestXml XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    public ConsultBillAvVillasResponseXml consultBill(ConsultBillAvVillasRequestXml consultBillRequestXml);
}
