package com.nova.horario;

import com.nova.horario.dto.OperatividadDTO;
import com.nova.horario.dto.UnidadHorarioDTO;
import com.nova.horario.mapper.OperatividadMapper;
import com.nova.horario.service.OperatividadService;
import com.nova.toolkit.ApiResponse;
import com.nova.unidad.model.UnidadInfo;
import com.nova.unidad.service.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/horario")
public class HorarioController {
    private final UnidadService unidadService;
    private final OperatividadService operatividadService;
    private final OperatividadMapper mapper;

    @Autowired
    public HorarioController(UnidadService unidadService, OperatividadService operatividadService, OperatividadMapper mapper) {
        this.unidadService = unidadService;
        this.operatividadService = operatividadService;
        this.mapper = mapper;
    }

    @GetMapping("horarios")
    public ResponseEntity<ApiResponse> obtenerHorarios() {
        List<UnidadInfo> unidades = unidadService.obtenerUnidades();
        List<UnidadHorarioDTO> horarios = new ArrayList<>();
        unidades.forEach(unidadInfo -> {
            UnidadHorarioDTO unidadHorarioDTO = new UnidadHorarioDTO();
            unidadHorarioDTO.setUnidad(unidadInfo);
            List<OperatividadDTO> operatividadDTOS = mapper.toDto(operatividadService.obtenerOperatividad(unidadInfo.getId()));
            unidadHorarioDTO.setHorarios(operatividadDTOS);
            horarios.add(unidadHorarioDTO);
        });
        return ResponseEntity.ok(ApiResponse.builder().data(horarios).code(200).build());
    }

    @GetMapping("horario")
    public ResponseEntity<ApiResponse> obtenerHorario(@RequestParam("unidad") Integer unidad) {
        UnidadHorarioDTO unidadHorarioDTO = new UnidadHorarioDTO();
        Optional<UnidadInfo> info = unidadService.obtenerUnidadPorId(unidad);
        if (info.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontró a la unidad especificada").code(200).build());
        }
        unidadHorarioDTO.setUnidad(info.get());
        List<OperatividadDTO> operatividadDTOS = mapper.toDto(operatividadService.obtenerOperatividad(unidad));
        if (operatividadDTOS.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.builder().message("No hay horarios disponibles").code(200).build());
        }
        unidadHorarioDTO.setHorarios(operatividadDTOS);
        return ResponseEntity.ok(ApiResponse.builder().data(unidadHorarioDTO).code(200).build());

    }
}
