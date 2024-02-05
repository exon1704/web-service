package com.nova.seguimiento.service;

import com.nova.seguimiento.model.Seguimiento;
import com.nova.seguimiento.model.SeguimientoInfo;
import com.nova.seguimiento.repository.SeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoServiceImpl implements SeguimientoService {
    private final SeguimientoRepository repository;

    @Autowired
    public SeguimientoServiceImpl(SeguimientoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Seguimiento agregar(Seguimiento seguimiento) {
        return repository.save(seguimiento);
    }

    @Override
    public List<SeguimientoInfo> obtenerSeguimientos(Integer ticketId) {
        return repository.findByTicket_IdOrderByFechaDesc(ticketId);
    }
}
