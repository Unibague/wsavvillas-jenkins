package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.domain.dto.PmtRollbackRequestDto;
import com.avvillas.domain.dto.PmtRollbackResponseDto;
import com.avvillas.infrastructure.api.soap.IPmtRollbackController;
import jakarta.jws.WebService;

/**
 * Controlador para el reverso de una factura
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IPmtRollbackController", targetNamespace = "http://biller.com/onlinebilling")
public class PmtRollbackController implements IPmtRollbackController {

    /**
     * Reversa el pago de una factura
     * @param pmtRollbackRequest Dto con la factura a reversar
     * @return Dto con la confirmacion del reverso de la factura
     */
    @Override
    public PmtRollbackResponseDto sendPmtRollback(PmtRollbackRequestDto pmtRollbackRequest) {
        return new PmtRollbackResponseDto("0", pmtRollbackRequest.getRequestId(), "Reverso exitoso", "98765");
    }
}
