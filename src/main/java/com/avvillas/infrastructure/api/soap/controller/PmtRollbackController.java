package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.PmtRollbackRequestXml;
import com.avvillas.application.dto.PmtRollbackResponseXml;
import com.avvillas.infrastructure.api.soap.IPmtRollbackController;
import jakarta.jws.WebService;

/**
 * Controlador para el reverso de una factura desde ATH
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IPmtRollbackController")
public class PmtRollbackController implements IPmtRollbackController {

    /**
     * Reversa el pago de una factura
     * @param pmtRollbackRequest Dto con la factura a reversar
     * @return Dto con la confirmacion del reverso de la factura
     */
    @Override
    public PmtRollbackResponseXml sendPmtRollback(PmtRollbackRequestXml pmtRollbackRequest) {
        return new PmtRollbackResponseXml("0", pmtRollbackRequest.getRequestId(), "Reverso exitoso", "98765");
    }
}
