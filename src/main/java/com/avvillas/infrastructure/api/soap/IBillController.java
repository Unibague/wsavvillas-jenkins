package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.infrastructure.security.WSS4JInterceptorInATH;
import com.avvillas.infrastructure.security.WSS4JInterceptorOutATH;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

/**
 * Interfaz para el controlador de la consulta de una factura desde ATH
 */
@WebService(targetNamespace = "http://biller.com/onlinebilling")
//@InInterceptors(classes = {WSS4JInterceptorInATH.class})
//@OutInterceptors(classes = {WSS4JInterceptorOutATH.class})
public interface IBillController {

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @WebMethod
    public BillResponseXml getBill(@WebParam(name = "BillRequest") @XmlElement(required = true) BillRequestXml billRequest);

    @WebMethod
    public String saludar();
}
