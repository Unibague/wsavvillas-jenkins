package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.domain.dto.BillRequestDto;
import com.avvillas.domain.dto.BillResponseDto;
import com.avvillas.domain.usecase.IBillUseCase;
import com.avvillas.infrastructure.api.soap.IBillController;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * Controlador para la consulta de una factura
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IBillController", targetNamespace = "http://biller.com/onlinebilling")
public class BillController implements IBillController {

    @Inject
    IBillUseCase iBillUseCase;

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponseDto getBill(BillRequestDto billRequest) {
        return iBillUseCase.getBill(billRequest);
    //    return new BillResponseDto("0", billRequest.getRequestId(), "Fue exitoso", null);
    }

    @Override
    public String saludar() {
        return "¡Hola, Quarkus";
    }
}
