package com.nova.horario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "operatividad")
public class Operatividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operatividad", nullable = false)
    private Integer id;

    @Column(name = "id_unidad", nullable = false)
    private Integer unidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_horario", nullable = false)
    private Horario horario;

    @Column(name = "hora_apertura")
    private LocalTime apertura;

    @Column(name = "hora_cierre")
    private LocalTime cierre;

    @Column(name = "activo")
    private Boolean activo;

}