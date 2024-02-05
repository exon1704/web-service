package com.nova.ticket.service;

import com.nova.ticket.model.Ticket;
import com.nova.ticket.model.TicketDetallesInfo;
import com.nova.ticket.model.TicketInfo;
import com.nova.ticket.repository.TicketRepository;
import com.nova.toolkit.TicketFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementación del servicio para gestionar tickets.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    /**
     * Obtiene la información de un ticket por su folio.
     *
     * @param folio El folio del ticket que se desea obtener.
     * @return La información del ticket si se encuentra, de lo contrario, null.
     */
    @Override
    public TicketInfo obtenerPorFolio(String folio) {
        return repository.obtenerTicket(folio);
    }

    /**
     * Obtiene un ticket por su identificador único.
     *
     * @param id El identificador único del ticket.
     * @return Un Optional que puede contener el ticket si se encuentra, de lo contrario, está vacío.
     */
    @Override
    public Optional<Ticket> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    /**
     * Obtiene los detalles de un ticket por su folio.
     *
     * @param folio El folio del ticket del cual se desean obtener los detalles.
     * @return Los detalles del ticket si se encuentran, de lo contrario, null.
     */
    @Override
    public TicketDetallesInfo obtenerDetalles(String folio) {
        return repository.obtenerDetalles(folio);
    }

    /**
     * Guarda un nuevo ticket o actualiza uno existente.
     *
     * @param ticket El ticket que se desea guardar.
     * @return El ticket guardado.
     */
    @Override
    public Ticket guardar(Ticket ticket) {
        return repository.save(ticket);
    }

    /**
     * Verifica si ya existe un ticket con el folio proporcionado.
     *
     * @param folio El folio del ticket.
     * @return true si ya existe un ticket con el folio y está marcado como visible, de lo contrario, false.
     */
    @Override
    public boolean existeFolio(String folio) {
        return repository.existsByFolioAndVisibleTrue(folio);
    }

    /**
     * Elimina lógicamente un ticket marcándolo como no visible.
     *
     * @param id El identificador único del ticket a borrar.
     * @return true si la eliminación lógica fue exitosa, de lo contrario, false.
     */
    @Override
    @Transactional
    public boolean borrar(Integer id) {
        Optional<Ticket> optionalTicket = repository.findById(id);

        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setVisible(false);
            repository.save(ticket);
            return true;
        }

        return false;
    }

    /**
     * Obtiene una lista paginada de tickets según los filtros proporcionados.
     *
     * @param filtro Contiene los filtros de búsqueda y la información de paginación.
     * @return La página de información de los tickets que cumplen con los filtros.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketInfo> obtenerPorFiltro(TicketFiltro filtro) {
        Pageable pageable = PageRequest.of(filtro.getPagina(), filtro.getFilas());
        return repository.buscarTickets(filtro.getEstado(), filtro.getUnidad(), filtro.getArea(), filtro.getFecha(), pageable);
    }
}
