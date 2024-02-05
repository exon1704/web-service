package com.nova.horario.dto;

import com.nova.unidad.model.UnidadInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UnidadHorarioDTO {
    private UnidadInfo unidad;
    private List<OperatividadDTO> horarios;
}
