package com.avvillas.web;

import com.avvillas.domain.dto.PmtNotificationRequestDto;
import com.avvillas.domain.dto.PmtNotificationResponseDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Interfaz para el controlador de la notificacion de una factura
 */
@WebService(targetNamespace = "http://biller.com/onlinebilling")
public interface IPmtNotificationController {

    /**
     * Guarda la notificacion de pago de una factura
     * @param pmtNotificationRequest Dto con la notificacion de la factura pagada
     * @return Dto con la confirmacion de guardado de la notificacion
     */
    @WebMethod
    public PmtNotificationResponseDto sendPmtNotification(@WebParam(name = "PmtNotificationRequest") PmtNotificationRequestDto pmtNotificationRequest);
}
