package com.avvillas.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Respuesta de una factura desde el convenio hacia ATH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "BillResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillResponseXml {

    /**
     * Código de aceptación
     */
    @XmlElement(name = "Status", required = true, namespace = "")
    private String status;

    /**
     * Id de la solicitud (el mismo del {@link BillRequestXml} recibido)
     */
    @XmlElement(name = "RequestId", required = true, namespace = "")
    private String requestId;

    /**
     * Descripcion o mensaje del {@link #status}
     */
    @XmlElement(name = "Message", required = true, namespace = "")
    private String message;

    /**
     * Lista con las facturas
     * Opcional Si {@link #status} es diferente a 0
     */
    @XmlElement(name = "Invoices", required = false, namespace = "")
    private List<InvoiceXml> invoices;
}
