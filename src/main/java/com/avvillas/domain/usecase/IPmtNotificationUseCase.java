package com.avvillas.domain.usecase;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;

/**
 * Caso de uso para el pago de una factura desde ATH
 */
public interface IPmtNotificationUseCase extends ITransactionHistoryLogUseCase {

    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param pmtNotificationRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    public PmtNotificationResponseXml sendPmtNotification(PmtNotificationRequestXml pmtNotificationRequestXml);

}
