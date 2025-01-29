package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Unified interface for handling both consulting and paying bills
 */
@WebService(targetNamespace = "http://organizacion.com/wsEstandar/")
public interface IBillAvVillasController {

    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @WebMethod(operationName = "consultarFacturaEstandar")
    public ConsultBillAvVillasResponseXml consultBill(@WebParam(name = "oe_consultarFacturaEstandar") @XmlElement(required = true) ConsultBillAvVillasRequestXml consultBillRequest);


    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param payBillRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @WebMethod(operationName = "pagarFacturaEstandar")
    public PayBillAvVillasResponseXml payBill(@WebParam(name = "oe_pagarFacturaEstandar") @XmlElement(required = true) PayBillAvVillasRequestXml payBillRequestXml);

}
