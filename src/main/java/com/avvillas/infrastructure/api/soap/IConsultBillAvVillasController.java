package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.infrastructure.security.WSS4JInterceptorInAvVillas;
import com.avvillas.infrastructure.security.WSS4JInterceptorOutAvVillas;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

/**
 * Interfaz para el controlador de la consulta de una factura desde AvVillas
 */
@WebService(targetNamespace = "http://organizacion.com/wsEstandar/")
//@InInterceptors(classes = {WSS4JInterceptorInAvVillas.class})
//@OutInterceptors(classes = {WSS4JInterceptorOutAvVillas.class})
public interface IConsultBillAvVillasController {

    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @WebMethod(operationName = "consultarFacturaEstandar")
    public ConsultBillAvVillasResponseXml consultBill(@WebParam(name = "oe_consultarFacturaEstandar") @XmlElement(required = true) ConsultBillAvVillasRequestXml consultBillRequest);
}
