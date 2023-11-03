package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.domain.usecase.IBillUseCase;
import com.avvillas.infrastructure.api.soap.IBillController;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * Controlador para la consulta de una factura
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IBillController", targetNamespace = "http://biller.com/onlinebilling")
public class BillController implements IBillController {

    private final IBillUseCase iBillUseCase;

    @Inject
    public BillController(IBillUseCase iBillUseCase) {
        this.iBillUseCase = iBillUseCase;
    }

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequestXml Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponseXml getBill(BillRequestXml billRequestXml) {
        return iBillUseCase.getBill(billRequestXml);
    }

    @Override
    public String saludar() {
        return "¡Hola, Quarkus";
    }
}
