package com.nova.area.service;

import com.nova.area.model.Area;
import com.nova.area.model.AreaInfo;
import com.nova.area.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    private final AreaRepository repository;

    @Autowired
    public AreaServiceImpl(AreaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AreaInfo> obtenerAreas() {
        return repository.obtenerAreas();
    }

    @Override
    public List<Area> obtenerAreaConReportes() {
        return repository.findAll();
    }
}
