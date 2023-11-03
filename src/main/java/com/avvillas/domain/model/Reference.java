package com.avvillas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Campos de busqueda adicionales de {@link BillRequest}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reference {

    /**
     * Nombre de la caracteristica adicional
     */
    private String name;

    /**
     * Descripcion de la caracteristica adicional
     */
    private String message;
}
