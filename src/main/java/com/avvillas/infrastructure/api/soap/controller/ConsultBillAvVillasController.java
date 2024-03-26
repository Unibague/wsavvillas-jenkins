package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.infrastructure.api.soap.IConsultBillAvVillasController;
import jakarta.jws.WebService;

/**
 * Controlador para la consulta de una factura desde AvVillas
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IConsultBillAvVillasController")
public class ConsultBillAvVillasController implements IConsultBillAvVillasController {


    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @Override
    public ConsultBillAvVillasResponseXml consultBill(ConsultBillAvVillasRequestXml consultBillRequest) {
        return new ConsultBillAvVillasResponseXml(consultBillRequest.getBankCodeOrigin(), "asd", "asd", 1234, "3423", "12223", "12r134", "123123", 423, "asfas", "43534", "234234");
    }
}
