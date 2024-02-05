package com.nova.estado.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.nova.estado.model.EstadoReporte}
 */
public record EstadoReporteDTO(Integer id, String nombre) implements Serializable {
}