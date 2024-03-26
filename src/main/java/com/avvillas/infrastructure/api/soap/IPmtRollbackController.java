package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.PmtRollbackRequestXml;
import com.avvillas.application.dto.PmtRollbackResponseXml;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Interfaz para el controlador del reverso de una factura desde ATH
 */
@WebService(targetNamespace = "http://biller.com/onlinebilling")
public interface IPmtRollbackController {

    /**
     * Reversa el pago de una factura
     * @param pmtRollbackRequest Dto con la factura a reversar
     * @return Dto con la confirmacion del reverso de la factura
     */
    @WebMethod
    public PmtRollbackResponseXml sendPmtRollback(@WebParam(name = "PmtRollbackRequest") @XmlElement(required = true) PmtRollbackRequestXml pmtRollbackRequest);
}
