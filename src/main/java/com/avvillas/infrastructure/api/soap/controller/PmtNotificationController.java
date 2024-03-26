package com.avvillas.infrastructure.api.soap.controller;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.application.dto.PmtNotificationResponseXml;
import com.avvillas.domain.usecase.IPmtNotificationUseCase;
import com.avvillas.infrastructure.api.soap.IPmtNotificationController;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * Controlador para la notificacion de pago de una factura desde ATH
 */
@WebService(endpointInterface = "com.avvillas.infrastructure.api.soap.IPmtNotificationController")
public class PmtNotificationController implements IPmtNotificationController {

    /**
     * Caso de uso para el pago de una factura
     */
    private final IPmtNotificationUseCase iPmtNotificationUseCase;

    @Inject
    public PmtNotificationController(IPmtNotificationUseCase iPmtNotificationUseCase) {
        this.iPmtNotificationUseCase = iPmtNotificationUseCase;
    }

    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param pmtNotificationRequestXml XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @Override
    public PmtNotificationResponseXml sendPmtNotification(PmtNotificationRequestXml pmtNotificationRequestXml) {
        return iPmtNotificationUseCase.sendPmtNotification(pmtNotificationRequestXml);
    }
}
