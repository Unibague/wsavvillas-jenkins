package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Detalles de los valores de una factura de {@link Invoice} o {@link PaidInvoice}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValueDetail {

    /**
     * Descripcion del valor detallado
     */
    private String description;

    /**
     * Valor al que corresponde la descripción
     */
    private Double value;

    /**
     * Convenio en caso de que el valor sea de subservicio
     */
    private String classDescription;
}
