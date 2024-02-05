package com.nova.seguimiento.model;

import java.time.LocalDateTime;

/**
 * Projection for {@link Seguimiento}
 */
public interface SeguimientoInfo {
    Integer getId();

    String getAgente();

    LocalDateTime getFecha();

    String getNota();

    String getEstadoNombre();
}