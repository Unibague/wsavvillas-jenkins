package com.avvillas.domain.model;

/**
 * Enumeracion para los mensajes en los Logs
 */
public enum MessagesLog {

    SUCCESSFULLY_SAVED("Guardado exitoso Log"),
    ERROR_SAVED("Error al Guardar Log");



    /**
     * Descripcion del mensaje
     */
    private final String description;

    /**
     * Instancia un MessageLog
     * @param description Descripcion del mensaje
     */
    MessagesLog(String description) {
        this.description = description;
    }

    /**
     * Devuelve la descripcion del mensaje
     * @return Descripcion del mensaje
     */
    public String getDescription() {
        return description;
    }
}
