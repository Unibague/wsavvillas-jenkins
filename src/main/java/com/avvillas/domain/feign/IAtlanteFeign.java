package com.avvillas.domain.feign;

import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.PmtNotificationRequest;
import com.avvillas.domain.model.PmtNotificationResponse;

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

    /**
     * Consume el endpoint de Atlante para pagar facturas
     * @param pmtNotificationRequest Dto con los datos de las facturas a pagar
     * @return Dto con la confirmacion de pago de las facturas
     */
    public PmtNotificationResponse sendPmtNotification(PmtNotificationRequest pmtNotificationRequest);
}
