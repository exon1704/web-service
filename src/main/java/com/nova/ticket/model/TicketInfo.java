package com.nova.ticket.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * Projection for {@link Ticket}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface TicketInfo {

    Integer getId();

    String getFolio();

    LocalDateTime getFecha();

    String getUnidadClave();

    String getUnidadNombre();

    String getReporteNombre();

    String getEstadoNombre();

    String getReporteAreaNombre();

}