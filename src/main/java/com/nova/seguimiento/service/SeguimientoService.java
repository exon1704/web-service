package com.nova.seguimiento.service;

import com.nova.seguimiento.model.Seguimiento;
import com.nova.seguimiento.model.SeguimientoInfo;

import java.util.List;

public interface SeguimientoService {
    Seguimiento agregar(Seguimiento seguimiento);

    List<SeguimientoInfo> obtenerSeguimientos(Integer ticketId);
}
