package com.avvillas.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta del reverso desde el convenio hacia ATH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "PmtRollbackResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PmtRollbackResponseDto {

    /**
     * Codigo de aceptacion 0,1,82,83,84
     */
    @XmlElement(name = "Status", required = true, namespace = "")
    private String status;

    /**
     * Id de la solicitud
     */
    @XmlElement(name = "RequestId", required = true, namespace = "")
    private String requestId;

    /**
     * Descripcion del {@link #status}
     */
    @XmlElement(name = "Message", required = true, namespace = "")
    private String message;

    /**
     * Número de autorización del convenio
     */
    @XmlElement(name = "PartnerAuthCode", required = false, namespace = "")
    private String partnerAuthCode;
}
