package com.avvillas.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta del pago de una factura desde el convenio hacia ATH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "PmtNotificationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PmtNotificationResponseXml {

    /**
     * Codigo de aceptacion 0,1,82,83,84
     */
    @XmlElement(name = "Status", required = true, namespace = "")
    private String status;

    /**
     * Id de la solicitud (la misma de {@link PmtNotificationRequestXml})
     */
    @XmlElement(name = "RequestId", required = true, namespace = "")
    private String requestId;

    /**
     * Descripcion del {@link #status}
     */
    @XmlElement(name = "Message", required = true, namespace = "")
    private String message;

    /**
     * Numero de autorizacion del convenio
     */
    @XmlElement(name = "PartnerAuthCode", required = false, namespace = "")
    private String partnerAuthCode;
}
