package com.nova.ticket.dto;

import com.nova.ticket.model.Ticket;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Ticket}
 */
@Value
public class TicketDto implements Serializable {
    Integer id;
    String folio;
    LocalDateTime fecha;
    String agente;
    String nota;
    Integer unidadId;
    Integer reporteId;
    Integer areaId;
    Integer estadoId;
}