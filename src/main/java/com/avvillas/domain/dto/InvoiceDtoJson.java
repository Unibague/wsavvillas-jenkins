package com.avvillas.domain.dto;

import com.avvillas.infrastructure.util.LocalDateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Factura de {@link BillResponseDto}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDtoJson {

    /**
     * Numero de factura
     */
    private String identificacion;

    /**
     * Valor total de la factura
     */
    private Double valor;

    /**
     * Fecha de expiracion de la factura
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fecha_vencimiento;

    /**
     * Fecha de ultimo pago de la factura
     */
    //private LocalDateTime endPaymentDate;

    /**
     * Detalles de los valores de la factura
     */
  //  private List<ValueDetailDto> valuesDetail;

    /**
     * Detalles adicionales de la factura
     */
 //   private List<AdditionalDataDto> additionalData;
}
