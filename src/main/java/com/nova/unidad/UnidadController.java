package com.nova.unidad;

import com.nova.toolkit.ApiResponse;
import com.nova.unidad.model.UnidadInfo;
import com.nova.unidad.service.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("unidad")
public class UnidadController {
    private final UnidadService service;

    @Autowired
    public UnidadController(UnidadService service) {
        this.service = service;
    }

    @GetMapping("unidades")
    public ResponseEntity<ApiResponse> obtenerUnidades() {
        List<UnidadInfo> result = service.obtenerUnidades();
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.builder().data(result).code(200).build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(204).build());
        }
    }

    @GetMapping("unidad")
    public ResponseEntity<ApiResponse> obtenerUnidad(@RequestParam("clave") String clave) {
        Optional<UnidadInfo> info = service.obtenerUnidadPorClave(clave);
        return info.map(unidadInfo -> ResponseEntity.ok(ApiResponse.builder().data(unidadInfo).code(200).build())).orElseGet(() -> ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(204).build()));
    }
}
