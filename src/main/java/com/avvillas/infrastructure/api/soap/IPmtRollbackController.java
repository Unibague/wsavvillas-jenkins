package com.avvillas.infrastructure.api.soap;

import com.avvillas.domain.dto.PmtRollbackRequestDto;
import com.avvillas.domain.dto.PmtRollbackResponseDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Interfaz para el controlador del reverso de una factura
 */
@WebService(targetNamespace = "http://biller.com/onlinebilling")
public interface IPmtRollbackController {

    /**
     * Reversa el pago de una factura
     * @param pmtRollbackRequest Dto con la factura a reversar
     * @return Dto con la confirmacion del reverso de la factura
     */
    @WebMethod
    public PmtRollbackResponseDto sendPmtRollback(@WebParam(name = "PmtRollbackRequest") PmtRollbackRequestDto pmtRollbackRequest);
}
