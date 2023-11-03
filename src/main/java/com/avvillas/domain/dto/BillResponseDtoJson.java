package com.avvillas.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillResponseDtoJson {

    /**
     * Código de aceptación
     */
    private String status;

    /**
     * Id de la solicitud (el mismo del {@link BillRequestDto} recibido)
     */
 //   private String requestId;

    /**
     * Descripcion o mensaje del {@link #status}
     */
    private String message;

    /**
     * Lista con las facturas
     * Opcional Si {@link #status} es diferente a 0
     */
    private List<InvoiceDtoJson> invoices;
}
