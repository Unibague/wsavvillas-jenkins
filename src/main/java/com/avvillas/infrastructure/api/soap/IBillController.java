package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Interfaz para el controlador de la consulta de una factura
 */
@WebService(targetNamespace = "http://biller.com/onlinebilling")
public interface IBillController {

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @WebMethod
    public BillResponseXml getBill(@WebParam(name = "BillRequest") BillRequestXml billRequest);

    @WebMethod
    public String saludar();
}
