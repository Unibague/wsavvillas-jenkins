package com.avvillas.web.controller;

import com.avvillas.domain.dto.PmtNotificationRequestDto;
import com.avvillas.domain.dto.PmtNotificationResponseDto;
import com.avvillas.web.IPmtNotificationController;
import jakarta.jws.WebService;

/**
 * Controlador para la notificacion de una factura
 */
@WebService(endpointInterface = "com.avvillas.web.IPmtNotificationController", targetNamespace = "http://biller.com/onlinebilling")
public class PmtNotificationController implements IPmtNotificationController {

    /**
     * Guarda la notificacion de pago de una factura
     * @param pmtNotificationRequest Dto con la notificacion de la factura pagada
     * @return Dto con la confirmacion de guardado de la notificacion
     */
    @Override
    public PmtNotificationResponseDto sendPmtNotification(PmtNotificationRequestDto pmtNotificationRequest) {
        return new PmtNotificationResponseDto("0", pmtNotificationRequest.getRequestId(), "Fue exitosa la notificación", "98765");
    }
}
