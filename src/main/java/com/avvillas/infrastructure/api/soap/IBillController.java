package com.avvillas.infrastructure.api.soap;

import com.avvillas.domain.dto.BillRequestDto;
import com.avvillas.domain.dto.BillResponseDto;
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
    public BillResponseDto getBill(@WebParam(name = "BillRequest") BillRequestDto billRequest);

    @WebMethod
    public String saludar();
}
