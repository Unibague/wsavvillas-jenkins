package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import com.avvillas.infrastructure.api.soap.IBillAvVillasController;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * Unified controller for consulting and paying bills.
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IBillAvVillasController",
        serviceName = "billavvillas")
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
    @Override
    public ConsultBillAvVillasResponseXml consultBill(ConsultBillAvVillasRequestXml consultBillRequest) {
        return consultUseCase.consultBill(consultBillRequest);
    }

    @Override
    public PayBillAvVillasResponseXml payBill(PayBillAvVillasRequestXml payBillRequestXml) {
        return payUseCase.payBill(payBillRequestXml);
    }
}
