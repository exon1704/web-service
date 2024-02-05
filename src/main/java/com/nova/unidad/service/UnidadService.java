package com.nova.unidad.service;

import com.nova.unidad.model.UnidadInfo;

import java.util.List;
import java.util.Optional;

public interface UnidadService {
    List<UnidadInfo> obtenerUnidades();

    Optional<UnidadInfo> obtenerUnidadPorClave(String clave);

    Optional<UnidadInfo> obtenerUnidadPorId(Integer clave);
}
