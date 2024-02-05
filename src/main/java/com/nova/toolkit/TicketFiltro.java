package com.nova.toolkit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TicketFiltro {
    private LocalDateTime fecha;
    private Integer pagina, filas, unidad, estado, area;

}
