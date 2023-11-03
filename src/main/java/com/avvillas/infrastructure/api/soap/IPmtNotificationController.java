package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;
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
    public PmtNotificationResponseXml sendPmtNotification(@WebParam(name = "PmtNotificationRequest") PmtNotificationRequestXml pmtNotificationRequest);
}
