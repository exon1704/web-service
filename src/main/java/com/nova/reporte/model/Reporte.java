package com.nova.reporte.model;

import com.nova.area.model.Area;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    public Reporte(Integer id) {
        this.id = id;
    }

    public Reporte() {
    }

}