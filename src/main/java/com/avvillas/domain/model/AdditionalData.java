package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Detalles adicionales de una factura de {@link Invoice}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalData {

    /**
     * Nombre del detalle adicional
     */
    private String name;

    /**
     * Descripción del detalle adicional
     */
    private String message;
}
