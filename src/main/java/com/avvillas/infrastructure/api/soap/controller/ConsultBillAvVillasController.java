package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import com.avvillas.infrastructure.api.soap.IConsultBillAvVillasController;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * Controlador para la consulta de una factura desde AvVillas
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IConsultBillAvVillasController")
public class ConsultBillAvVillasController implements IConsultBillAvVillasController {

    private final IConsultBillAvVillasUseCase iConsultBillAvVillasUseCase;

    @Inject
    public ConsultBillAvVillasController(IConsultBillAvVillasUseCase iConsultBillAvVillasUseCase) {
        this.iConsultBillAvVillasUseCase = iConsultBillAvVillasUseCase;
    }

    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @Override
    public ConsultBillAvVillasResponseXml consultBill(ConsultBillAvVillasRequestXml consultBillRequest) {
        return iConsultBillAvVillasUseCase.consultBill(consultBillRequest);
    }
}
