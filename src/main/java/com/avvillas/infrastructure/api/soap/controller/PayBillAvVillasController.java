package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import com.avvillas.infrastructure.api.soap.IPayBillAvVillasController;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * Controlador para la notificacion de pago de una factura desde AvVillas
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IPayBillAvVillasController")
public class PayBillAvVillasController implements IPayBillAvVillasController {

    /**
     * Caso de uso para la notificacion de pago de una factura
     */
    private final IPayBillAvVillasUseCase iPayBillAvVillasUseCase;

    @Inject
    public PayBillAvVillasController(IPayBillAvVillasUseCase iPayBillAvVillasUseCase) {
        this.iPayBillAvVillasUseCase = iPayBillAvVillasUseCase;
    }

    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param payBillRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @Override
    public PayBillAvVillasResponseXml payBill(PayBillAvVillasRequestXml payBillRequestXml) {
        return iPayBillAvVillasUseCase.payBill(payBillRequestXml);
    }
}
