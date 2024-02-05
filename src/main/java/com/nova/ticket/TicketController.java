package com.nova.ticket;

import com.nova.estado.model.EstadoReporte;
import com.nova.reporte.model.Reporte;
import com.nova.seguimiento.model.Seguimiento;
import com.nova.seguimiento.model.SeguimientoInfo;
import com.nova.seguimiento.service.SeguimientoService;
import com.nova.ticket.dto.TicketDto;
import com.nova.ticket.dto.TicketSeguimientoDTO;
import com.nova.ticket.model.Ticket;
import com.nova.ticket.model.TicketDetallesInfo;
import com.nova.ticket.model.TicketInfo;
import com.nova.ticket.service.TicketService;
import com.nova.toolkit.ApiResponse;
import com.nova.toolkit.TicketFiltro;
import com.nova.unidad.model.Unidad;
import com.nova.unidad.service.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService service;
    private final SeguimientoService seguimientoService;
    private final UnidadService unidadService;

    @Autowired
    public TicketController(TicketService service, SeguimientoService seguimientoService, UnidadService unidadService) {
        this.service = service;
        this.seguimientoService = seguimientoService;
        this.unidadService = unidadService;
    }

    /**
     * Obtiene la información de un ticket por su folio.
     *
     * @param folio El folio del ticket que se desea obtener.
     * @return ResponseEntity con un ApiResponse indicando el resultado de la operación.
     * - 200 OK si se encontró la información del ticket.
     * - 204 No Content si no se encontró la información del ticket.
     * - 500 Internal Server Error si no se pudo completar la operación.
     */
    @GetMapping("info")
    public ResponseEntity<ApiResponse> obtenerTicket(@RequestParam("folio") String folio) {
        if (service.existeFolio(folio)) {
            TicketInfo ticketInfo = service.obtenerPorFolio(folio);
            return ResponseEntity.ok(ApiResponse.builder().data(ticketInfo).code(200).build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(204).build());
        }
    }

    /**
     * Obtiene los detalles de un ticket por su folio.
     *
     * @param folio El folio del ticket del cual se desean obtener los detalles.
     * @return ResponseEntity con un ApiResponse indicando el resultado de la operación.
     * - 200 OK si se encontraron los detalles del ticket.
     * - 204 No Content si no se encontraron los detalles del ticket.
     * - 500 Internal Server Error si no se pudo completar la operación.
     */
    @GetMapping("detalles")
    public ResponseEntity<ApiResponse> obtenerDetalles(@RequestParam("folio") String folio) {
        if (service.existeFolio(folio)) {
            TicketDetallesInfo ticketInfo = service.obtenerDetalles(folio);
            return ResponseEntity.ok(ApiResponse.builder().code(ticketInfo == null ? HttpStatus.NO_CONTENT.value() : 200).data(ticketInfo).message(ticketInfo == null ? "No se encontraron resultados" : null).build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().message("No se encontraron resultados").code(204).build());
        }
    }

    /**
     * Obtiene una lista paginada de tickets según los filtros proporcionados.
     *
     * @param fecha  La fecha de los tickets a obtener.
     * @param pagina El número de la página solicitada.
     * @param filas  El número de filas por página.
     * @param unidad El identificador de la unidad (opcional).
     * @param estado El identificador del estado (opcional).
     * @param area   El identificador del área (opcional).
     * @return ResponseEntity con un ApiResponse indicando el resultado de la operación.
     * - 200 OK si se encontraron tickets según los filtros.
     * - 204 No Content si no se encontraron tickets según los filtros.
     * - 500 Internal Server Error si no se pudo completar la operación.
     */
    @GetMapping("tickets")
    public ResponseEntity<ApiResponse> obtenerTickets(@RequestParam("fecha") LocalDateTime fecha, @RequestParam("pagina") int pagina, @RequestParam("filas") int filas, @RequestParam(value = "unidad", required = false) Integer unidad, @RequestParam(value = "estado", required = false) Integer estado, @RequestParam(value = "area", required = false) Integer area) {
        TicketFiltro filtro = new TicketFiltro(fecha, pagina, filas, unidad, estado, area);
        Page<TicketInfo> list = service.obtenerPorFiltro(filtro);

        if (list.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.builder().code(HttpStatus.NO_CONTENT.value()).data(null).message("No se encontraron resultados").build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().data(list.getContent()).page(Map.of("numberOfElements", list.getNumberOfElements(), "totalPage", list.getTotalPages(), "totalElements", list.getTotalElements(), "number", list.getNumber())).size(list.getContent().size()).code(200).build());
        }
    }

    /**
     * Guarda un nuevo ticket con la información proporcionada.
     *
     * @param ticket El objeto TicketDto que contiene la información del nuevo ticket.
     * @return ResponseEntity con un ApiResponse indicando el resultado de la operación.
     * - 200 OK si el ticket se registró correctamente.
     * - 500 Internal Server Error si no se pudo completar la operación.
     */
    @PostMapping("guardar")
    public ResponseEntity<ApiResponse> guardar(@RequestBody TicketDto ticket) {
        if (service.existeFolio(ticket.getFolio())) {
            return ResponseEntity.ok(ApiResponse.builder().code(HttpStatus.CONFLICT.value()).message("El folio " + ticket.getFolio() + " ya se encuentra registrado").build());
        } else {
            Ticket t = new Ticket();
            try {
                t.setFolio(ticket.getFolio());
                t.setAgente(ticket.getAgente());
                t.setNota(ticket.getNota());
                t.setReporte(new Reporte(ticket.getReporteId()));
                t.setUnidad(new Unidad(ticket.getUnidadId()));
                t.setFecha(LocalDateTime.now());
                t.setEstado(new EstadoReporte(ticket.getId()));
                Ticket result = service.guardar(t);
                agregarSeguimiento(result);
                return ResponseEntity.ok(ApiResponse.builder().message(result != null ? "El ticket se ha registrado correctamente" : "No se pudo registrar el ticket").code(result != null ? 200 : 500).build());
            } catch (DataIntegrityViolationException e) {
                return handleDataIntegrityViolation(e, 1);
            }
        }
    }

    @PostMapping("seguimiento")
    public ResponseEntity<ApiResponse> agregarSeguimiento(@RequestBody TicketDto ticket) {
        try {
            Optional<Ticket> dataModel = service.obtenerPorId(ticket.getId());
            if (dataModel.isPresent()) {
                Ticket t = dataModel.get();
                Seguimiento seg = new Seguimiento();
                seg.setTicket(t);
                seg.setFecha(LocalDateTime.now());
                seg.setAgente(ticket.getAgente());
                seg.setNota(ticket.getNota());
                seg.setEstado(new EstadoReporte(ticket.getEstadoId()));
                seguimientoService.agregar(seg);
                return ResponseEntity.ok(ApiResponse.builder().message("Se agrego el seguimiento al ticket").code(200).build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder().message("No se encontro el ticket").code(204).build());
            }
        } catch (DataIntegrityViolationException e) {
            // Manejar el error de violación de integridad de datos aquí
            // Puedes construir y devolver una respuesta personalizada al cliente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder().message("No se pudo registrar el seguimiento, verifique la consistencia de los datos").code(500).build());
        }
    }

    @GetMapping("seguimiento")
    public ResponseEntity<ApiResponse> obtenerSeguimiento(@RequestParam("folio") String folio) {
        TicketDetallesInfo ticket = this.service.obtenerDetalles(folio);
        if (ticket != null) {
            TicketSeguimientoDTO dto = new TicketSeguimientoDTO();
            dto.setTicket(ticket);
            List<SeguimientoInfo> list = new ArrayList<>(seguimientoService.obtenerSeguimientos(ticket.getId()));
            dto.setSeguimientos(list);
            return ResponseEntity.ok(ApiResponse.builder().data(dto).code(200).build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder().code(HttpStatus.NO_CONTENT.value()).data(null).message("No se encontraron resultados").build());
        }
    }

    /**
     * Agrega un seguimiento al ticket proporcionado.
     *
     * @param t El ticket al cual se le agregará el seguimiento.
     */
    private void agregarSeguimiento(Ticket t) {
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setTicket(t);
        seguimiento.setFecha(LocalDateTime.now());
        seguimiento.setEstado(t.getEstado());
        seguimiento.setAgente("Administrador de ticket");
        seguimiento.setNota("Se agrego un nuevo ticket");
        seguimientoService.agregar(seguimiento);
    }


    /**
     * Actualiza la información de un ticket existente.
     *
     * @param dto El objeto TicketDto que contiene la información actualizada del ticket.
     * @return ResponseEntity con un ApiResponse indicando el resultado de la operación.
     * - 200 OK si la actualización fue exitosa.
     * - 204 No Content si el ticket no se encuentra registrado o no está visible.
     * - 500 Internal Server Error si no se pudo completar la actualización.
     */
    @PutMapping("actualizar")
    public ResponseEntity<ApiResponse> actualizar(@RequestBody TicketDto dto) {
        Optional<Ticket> dataModel = service.obtenerPorId(dto.getId());

        if (dataModel.isEmpty() || !dataModel.get().getVisible()) {
            return ResponseEntity.ok(ApiResponse.builder().message("El ticket no se encuentra registrado").code(HttpStatus.NO_CONTENT.value()).build());
        } else {
            Ticket ticketModel = dataModel.get();
            try {
                ticketModel.setAgente(dto.getAgente());
                ticketModel.setFecha(LocalDateTime.now());
                ticketModel.setFolio(dto.getFolio() != null ? dto.getFolio() : ticketModel.getFolio());
                ticketModel.setReporte(dto.getReporteId() != null ? new Reporte(dto.getReporteId()) : ticketModel.getReporte());
                ticketModel.setNota(dto.getNota());
                ticketModel.setUnidad(dto.getUnidadId() != null ? new Unidad(dto.getUnidadId()) : ticketModel.getUnidad());
                ticketModel.setEstado(dto.getEstadoId() != null ? new EstadoReporte(dto.getEstadoId()) : ticketModel.getEstado());
                Ticket result = service.guardar(ticketModel);
                agregarSeguimiento(ticketModel);
                return ResponseEntity.ok(ApiResponse.builder().message(result != null ? "El ticket se ha actualizado correctamente" : "No se pudo actualizar el ticket").code(result != null ? 200 : 500).build());
            } catch (DataIntegrityViolationException e) {
                return handleDataIntegrityViolation(e, 2);
            }

        }
    }

    /**
     * Elimina lógicamente un ticket marcándolo como no visible.
     *
     * @param id El identificador único del ticket a borrar.
     * @return ResponseEntity con un ApiResponse indicando el resultado de la operación.
     * - 200 OK si la eliminación lógica fue exitosa.
     * - 204 No Content si el ticket no se encuentra registrado o ya está marcado como no visible.
     */
    @DeleteMapping("borrar")
    public ResponseEntity<ApiResponse> borrar(@RequestParam("id") Integer id) {
        Optional<Ticket> dataModel = service.obtenerPorId(id);

        if (dataModel.isEmpty() || !dataModel.get().getVisible()) {
            return ResponseEntity.ok(ApiResponse.builder().message("El ticket no se encuentra registrado").code(HttpStatus.NO_CONTENT.value()).build());
        } else {
            Ticket ticket = dataModel.get();
            ticket.setVisible(false);

            //Agregamos el seguimiento de eliminación de tickekt
            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setAgente("Administrador");
            seguimiento.setFecha(LocalDateTime.now());
            seguimiento.setTicket(ticket);
            seguimiento.setEstado(new EstadoReporte(2));
            seguimiento.setNota("Se elimino el ticket por el agente " + seguimiento.getAgente());
            service.guardar(ticket);
            seguimientoService.agregar(seguimiento);
            return ResponseEntity.ok(ApiResponse.builder().message("El ticket se ha eliminado correctamente").code(200).build());
        }
    }

    private ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException e, int tipo) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().message("Violación de integridad al intentar " + (tipo == 1 ? "registrar" : "actualizar") + " el ticket. Verifique que el folio no esté duplicado.").build());
    }
}