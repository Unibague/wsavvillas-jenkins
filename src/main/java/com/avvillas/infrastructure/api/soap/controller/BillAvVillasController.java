package com.avvillas.infrastructure.api.soap.controller;

import java.util.List;
import java.util.Map;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import com.avvillas.infrastructure.api.soap.IBillAvVillasController;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

@WebService(
    serviceName = "BillAvVillasService",
    portName = "BillAvVillasPort",
    targetNamespace = "http://organizacion.com/wsEstandar/",
    endpointInterface = "com.avvillas.infrastructure.api.soap.IBillAvVillasController"
)
public class BillAvVillasController implements IBillAvVillasController {

    private final IConsultBillAvVillasUseCase consultUseCase;
    private final IPayBillAvVillasUseCase payUseCase;

    @Resource
    private WebServiceContext context;

    public BillAvVillasController(IConsultBillAvVillasUseCase consultUseCase, IPayBillAvVillasUseCase payUseCase) {
        this.consultUseCase = consultUseCase;
        this.payUseCase = payUseCase;
    }

    @Override
    @WebMethod
    public ConsultBillAvVillasResponseXml consultarFacturaEstandar(ConsultBillAvVillasRequestXml consultBillRequest) {
        MessageContext messageContext = context.getMessageContext();
        Map<String, List<String>> headers = (Map<String, List<String>>) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        String transactionId = null;
        if (headers != null && headers.containsKey("X-Transaction-ID")) {
            transactionId = headers.get("X-Transaction-ID").get(0);
            System.out.println("🔍 X-Transaction-ID recibido: " + transactionId);
        } else {
            System.out.println("⚠️ X-Transaction-ID no presente en headers HTTP");
        }

        return consultUseCase.consultBill(consultBillRequest);
    }

    @Override
    @WebMethod
    public PayBillAvVillasResponseXml pagarFacturaEstandar(PayBillAvVillasRequestXml payBillRequest) {
        return payUseCase.payBill(payBillRequest);
    }
}
