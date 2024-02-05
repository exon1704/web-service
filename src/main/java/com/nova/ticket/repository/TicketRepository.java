package com.nova.ticket.repository;

import com.nova.ticket.model.Ticket;
import com.nova.ticket.model.TicketDetallesInfo;
import com.nova.ticket.model.TicketInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repositorio JPA para la entidad Ticket.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<TicketInfo> {

    /**
     * Obtiene la información de un ticket por su folio.
     *
     * @param folio El folio del ticket que se desea obtener.
     * @return La información del ticket si se encuentra, de lo contrario, null.
     */
    @Query(value = "CALL ticket_info(:_folio);", nativeQuery = true)
    TicketInfo obtenerTicket(@Param("_folio") String folio);

    /**
     * Obtiene los detalles de un ticket por su folio.
     *
     * @param folio El folio del ticket del cual se desean obtener los detalles.
     * @return Los detalles del ticket si se encuentran, de lo contrario, null.
     */
    @Query(value = "CALL ticket_detalles(:_folio);", nativeQuery = true)
    TicketDetallesInfo obtenerDetalles(@Param("_folio") String folio);

    /**
     * Busca tickets con filtros opcionales y paginación.
     *
     * @param idEstado El identificador del estado del ticket (opcional).
     * @param idUnidad El identificador de la unidad del ticket (opcional).
     * @param idArea   El identificador del área del ticket (opcional).
     * @param fecha    La fecha mínima del ticket (opcional).
     * @param pageable La información de paginación.
     * @return La página de información de los tickets que cumplen con los filtros.
     */
    @Query("SELECT ticket.id AS id, " + "ticket.folio AS folio, " + "estadoReporte.nombre AS estadoNombre, " + "area.nombre AS reporteAreaNombre, " + "reporte.nombre AS reporteNombre, " + "unidad.clave AS unidadClave, " + "unidad.nombre AS unidadNombre, " + "ticket.fecha AS fecha " + "FROM Ticket ticket " + "INNER JOIN ticket.estado estadoReporte " + "INNER JOIN ticket.unidad unidad " + "INNER JOIN ticket.reporte reporte " + "INNER JOIN reporte.area area " + "WHERE (:idEstado IS NULL OR ticket.estado.id = :idEstado) " + "AND (:idUnidad IS NULL OR ticket.unidad.id = :idUnidad) " + "AND (:idArea IS NULL OR reporte.area.id = :idArea) " + "AND (:fecha IS NULL OR ticket.fecha >= :fecha) and ticket.visible = true")
    Page<TicketInfo> buscarTickets(@Param("idEstado") Integer idEstado, @Param("idUnidad") Integer idUnidad, @Param("idArea") Integer idArea, @Param("fecha") LocalDateTime fecha, Pageable pageable);

    /**
     * Verifica si un ticket con un ID específico existe y está marcado como visible.
     *
     * @param id El identificador del ticket.
     * @return true si el ticket existe y está marcado como visible, de lo contrario, false.
     */
    boolean existsByIdAndVisibleTrue(Integer id);

    /**
     * Verifica si un ticket con un folio específico existe y está marcado como visible.
     *
     * @param folio El folio del ticket.
     * @return true si el ticket con el folio especificado existe y está marcado como visible, de lo contrario, false.
     */
    boolean existsByFolioAndVisibleTrue(String folio);


}
