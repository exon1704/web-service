package com.nova.horario.mapper;

import com.nova.horario.dto.OperatividadDTO;
import com.nova.horario.model.Operatividad;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperatividadMapper {

    List<OperatividadDTO> toDto(List<Operatividad> operatividad);
}