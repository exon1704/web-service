package com.nova.estado.mapper;

import com.nova.estado.dto.EstadoReporteDTO;
import com.nova.estado.model.EstadoReporte;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EstadoReporteMapper {

    List<EstadoReporteDTO> toDto(List<EstadoReporte> estadoReporte);
}