package com.nova.horario.service;

import com.nova.horario.model.Operatividad;
import com.nova.horario.repository.OperatividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatividadServiceImpl implements OperatividadService {
    private final OperatividadRepository repository;

    @Autowired
    public OperatividadServiceImpl(OperatividadRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Operatividad> obtenerOperatividad(Integer idUnidad) {
        return repository.findByUnidad(idUnidad);
    }
}
