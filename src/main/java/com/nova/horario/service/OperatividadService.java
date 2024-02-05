package com.nova.horario.service;

import com.nova.horario.model.Operatividad;

import java.util.List;

public interface OperatividadService {
    List<Operatividad> obtenerOperatividad(Integer idUnidad);

}
