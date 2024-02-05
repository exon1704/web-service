package com.nova.ticket.dto;

import com.nova.seguimiento.model.SeguimientoInfo;
import com.nova.ticket.model.TicketDetallesInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketSeguimientoDTO {
    private TicketDetallesInfo ticket;
    private List<SeguimientoInfo> seguimientos;
}
