package com.nova.area.mapper;

import com.nova.area.dto.AreaDto;
import com.nova.area.model.Area;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AreaMapper {


    List<AreaDto> toDto(List<Area> area);
}