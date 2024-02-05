package com.nova.estado.service;

import com.nova.estado.model.EstadoReporte;
import com.nova.estado.repository.EstadoReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoReporteServiceImpl implements EstadoReporteService {
    private final EstadoReporteRepository repository;

    @Autowired
    public EstadoReporteServiceImpl(EstadoReporteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EstadoReporte> obtenerEstados() {
        return repository.findAll();
    }
}
