package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;
import com.avvillas.infrastructure.api.soap.IPmtNotificationController;
import jakarta.jws.WebService;

/**
 * Controlador para la notificacion de una factura
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IPmtNotificationController", targetNamespace = "http://biller.com/onlinebilling")
public class PmtNotificationController implements IPmtNotificationController {

    /**
     * Guarda la notificacion de pago de una factura
     * @param pmtNotificationRequest Dto con la notificacion de la factura pagada
     * @return Dto con la confirmacion de guardado de la notificacion
     */
    @Override
    public PmtNotificationResponseXml sendPmtNotification(PmtNotificationRequestXml pmtNotificationRequest) {
        return new PmtNotificationResponseXml("0", pmtNotificationRequest.getRequestId(), "Fue exitosa la notificación", "98765");
    }
}
