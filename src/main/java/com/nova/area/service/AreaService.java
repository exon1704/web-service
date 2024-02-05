package com.nova.area.service;

import com.nova.area.model.Area;
import com.nova.area.model.AreaInfo;

import java.util.List;

public interface AreaService {
    List<AreaInfo> obtenerAreas();

    List<Area> obtenerAreaConReportes();
}
