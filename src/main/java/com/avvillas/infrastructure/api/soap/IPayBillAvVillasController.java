package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.infrastructure.security.WSS4JInterceptorInAvVillas;
import com.avvillas.infrastructure.security.WSS4JInterceptorOutAvVillas;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

/**
 * Interfaz para el controlador de la notificacion de pago de una factura desde AvVillas
 */
@WebService(targetNamespace = "http://organizacion.com/wsEstandar/")
//@InInterceptors(classes = {WSS4JInterceptorInAvVillas.class})
//@OutInterceptors(classes = {WSS4JInterceptorOutAvVillas.class})
public interface IPayBillAvVillasController {

    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param payBillRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @WebMethod(operationName = "pagarFacturaEstandar")
    public PayBillAvVillasResponseXml payBill(@WebParam(name = "oe_pagarFacturaEstandar") @XmlElement(required = true) PayBillAvVillasRequestXml payBillRequestXml);
}
