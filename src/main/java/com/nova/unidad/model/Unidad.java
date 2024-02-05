package com.nova.unidad.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "unidad")
public class Unidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad", nullable = false)
    private Integer id;
    @Column(name = "clave", nullable = false, length = 10)
    private String clave;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    public Unidad(Integer id) {
        this.id = id;
    }

    public Unidad() {
    }

}