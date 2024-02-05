package com.nova.unidad.service;

import com.nova.unidad.model.UnidadInfo;
import com.nova.unidad.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadServiceImpl implements UnidadService {
    private final UnidadRepository repository;

    @Autowired
    public UnidadServiceImpl(UnidadRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<UnidadInfo> obtenerUnidades() {
        return repository.obtenerUnidades();
    }

    @Override
    public Optional<UnidadInfo> obtenerUnidadPorClave(String clave) {
        return repository.findByClave(clave);
    }

    @Override
    public Optional<UnidadInfo> obtenerUnidadPorId(Integer id) {
        return repository.findById(id, UnidadInfo.class);
    }
}
