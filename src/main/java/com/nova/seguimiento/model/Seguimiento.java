package com.nova.seguimiento.model;

import com.nova.estado.model.EstadoReporte;
import com.nova.ticket.model.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "seguimiento")
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoReporte estado;

    @Column(name = "agente", length = 50)
    private String agente;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Lob
    @Column(name = "nota")
    private String nota;

}