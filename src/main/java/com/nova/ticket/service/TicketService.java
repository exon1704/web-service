package com.nova.ticket.service;

import com.nova.ticket.model.Ticket;
import com.nova.ticket.model.TicketDetallesInfo;
import com.nova.ticket.model.TicketInfo;
import com.nova.toolkit.TicketFiltro;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con los tickets.
 */
@Transactional
public interface TicketService {

    /**
     * Obtiene la información de un ticket por su folio.
     *
     * @param folio El folio del ticket que se desea obtener.
     * @return TicketInfo con la información del ticket.
     */
    TicketInfo obtenerPorFolio(String folio);

    /**
     * Obtiene un ticket por su identificador único.
     *
     * @param id El identificador único del ticket.
     * @return Optional con el ticket encontrado (si existe).
     */
    Optional<Ticket> obtenerPorId(Integer id);

    /**
     * Obtiene los detalles de un ticket por su folio.
     *
     * @param folio El folio del ticket del cual se desean obtener los detalles.
     * @return TicketDetallesInfo con los detalles del ticket.
     */
    TicketDetallesInfo obtenerDetalles(String folio);

    /**
     * Guarda o actualiza la información de un ticket.
     *
     * @param ticket El objeto Ticket que se desea guardar o actualizar.
     * @return Ticket con la información guardada o actualizada.
     */
    Ticket guardar(Ticket ticket);

    /**
     * Verifica si ya existe un ticket con el folio proporcionado.
     *
     * @param folio El folio del ticket que se desea verificar.
     * @return true si ya existe un ticket con el folio, false en caso contrario.
     */
    boolean existeFolio(String folio);

    /**
     * Elimina lógicamente un ticket marcándolo como no visible.
     *
     * @param id El identificador único del ticket a borrar.
     * @return true si la eliminación lógica fue exitosa, false si el ticket no se encuentra registrado o ya está marcado como no visible.
     */
    boolean borrar(Integer id);

    /**
     * Obtiene una lista paginada de tickets según los filtros proporcionados.
     *
     * @param filtro Filtro con los parámetros de búsqueda.
     * @return Page con la lista paginada de tickets.
     */
    @Transactional(readOnly = true)
    Page<TicketInfo> obtenerPorFiltro(TicketFiltro filtro);
}
