package com.nova.estado;

import com.nova.estado.dto.EstadoReporteDTO;
import com.nova.estado.mapper.EstadoReporteMapper;
import com.nova.estado.model.EstadoReporte;
import com.nova.estado.service.EstadoReporteService;
import com.nova.toolkit.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("estado")
public class EstadoReporteController {
    private final EstadoReporteService service;
    private final EstadoReporteMapper mapper;

    @Autowired
    public EstadoReporteController(EstadoReporteService service, EstadoReporteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("estados")
    public ResponseEntity<ApiResponse> obteneEstados() {
        List<EstadoReporte> reportes = service.obtenerEstados();

        if (reportes == null || reportes.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(204).build());
        }
        List<EstadoReporteDTO> reporteDTOS = mapper.toDto(reportes);
        return ResponseEntity.ok(ApiResponse.builder().data(reporteDTOS).size(reporteDTOS.size()).code(200).build());
    }

}
