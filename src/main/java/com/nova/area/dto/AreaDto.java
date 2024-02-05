package com.nova.area.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.nova.area.model.Area}
 */
public record AreaDto(Integer id, String nombre, Set<ReporteDto> reportes) implements Serializable {
    /**
     * DTO for {@link com.nova.reporte.model.Reporte}
     */
    public record ReporteDto(Integer id, String nombre) implements Serializable {
    }
}