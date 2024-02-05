package com.nova.area;

import com.nova.area.dto.AreaDto;
import com.nova.area.mapper.AreaMapper;
import com.nova.area.model.Area;
import com.nova.area.model.AreaInfo;
import com.nova.area.service.AreaService;
import com.nova.toolkit.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("area")
public class AreaController {
    private final AreaService service;
    private final AreaMapper mapper;

    @Autowired
    public AreaController(AreaService service, AreaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("areas")
    public ResponseEntity<ApiResponse> obtenerAreas() {
        List<AreaInfo> areas = service.obtenerAreas();
        if (areas != null) {
            return ResponseEntity.ok(ApiResponse.builder().data(areas).size(areas.size()).build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(HttpStatus.NO_CONTENT.value()).build());
        }

    }

    @GetMapping("reportes")
    public ResponseEntity<ApiResponse> obtenerReportes() {
        List<Area> areas = service.obtenerAreaConReportes();
        List<AreaDto> areaDtos = mapper.toDto(areas);
        if (areas != null) {
            return ResponseEntity.ok(ApiResponse.builder().data(areaDtos).size(areas.size()).build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(HttpStatus.NO_CONTENT.value()).build());
        }
    }
}
