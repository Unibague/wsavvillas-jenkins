package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;
import com.avvillas.infrastructure.security.WSS4JInterceptorOutATH;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

/**
 * Interfaz para el controlador de la notificacion de pago de una factura
 */
@WebService(targetNamespace = "http://biller.com/onlinebilling")
//@InInterceptors(classes = {WSS4JInterceptorOutATH.class})
//@OutInterceptors(classes = {WSS4JInterceptorOutATH.class})
public interface IPmtNotificationController {

    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param pmtNotificationRequestXml Dto con la notificacion de la factura pagada en el banco
     * @return Dto con la confirmacion del pago recibido
     */
    @WebMethod
    public PmtNotificationResponseXml sendPmtNotification(@WebParam(name = "PmtNotificationRequest") PmtNotificationRequestXml pmtNotificationRequestXml);
}
