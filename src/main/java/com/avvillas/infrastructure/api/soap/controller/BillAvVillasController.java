package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import com.avvillas.infrastructure.api.soap.IBillAvVillasController;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

/**
 * Unified controller for consulting and paying bills.
 */
@WebService(
        serviceName = "wsEstandar",
        portName = "wsEstandarSOAP",
        endpointInterface = "com.avvillas.infrastructure.api.soap.IBillAvVillasController",
        targetNamespace = "http://organizacion.com/wsEstandar/"
)
public class BillAvVillasController implements IBillAvVillasController {

    private final IConsultBillAvVillasUseCase consultUseCase;
    private final IPayBillAvVillasUseCase payUseCase;

    @Inject
    public BillAvVillasController(IConsultBillAvVillasUseCase consultUseCase, IPayBillAvVillasUseCase payUseCase) {
        this.consultUseCase = consultUseCase;
        this.payUseCase = payUseCase;
    }

    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */

    @WebMethod(
            operationName = "consultarFacturaEstandar",
            action = "http://organizacion.com/wsEstandar/consultarFacturaEstandar"
    )
    @WebResult(name = "os_consultarFacturaEstandar", targetNamespace = "http://organizacion.com/wsEstandar/")

    @Override
    public ConsultBillAvVillasResponseXml consultarFacturaEstandar(
            @WebParam(name = "oe_consultarFacturaEstandar", targetNamespace = "http://organizacion.com/wsEstandar/")
            ConsultBillAvVillasRequestXml consultBillRequest) {
        return consultUseCase.consultBill(consultBillRequest);
    }



    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param payBillRequest XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */

    @WebMethod(
            operationName = "pagarFacturaEstandar",
            action = "http://organizacion.com/wsEstandar/consultarFacturaEstandar"
    )
    @WebResult(name = "os_pagarFacturaEstandar", targetNamespace = "http://organizacion.com/wsEstandar/")

    @Override
    public PayBillAvVillasResponseXml pagarFacturaEstandar(
            @WebParam(name = "oe_pagarFacturaEstandar", targetNamespace = "http://organizacion.com/wsEstandar/")
            PayBillAvVillasRequestXml payBillRequest) {
        return payUseCase.payBill(payBillRequest);
    }


}
