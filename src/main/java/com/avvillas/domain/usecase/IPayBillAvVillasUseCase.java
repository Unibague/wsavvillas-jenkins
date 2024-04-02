package com.avvillas.domain.usecase;

import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;

/**
 * Caso de uso para el pago de una factura desde Av Villas
 */
public interface IPayBillAvVillasUseCase extends ITransactionHistoryLogUseCase {

    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param payBillRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    public PayBillAvVillasResponseXml payBill(PayBillAvVillasRequestXml payBillRequestXml);
}
