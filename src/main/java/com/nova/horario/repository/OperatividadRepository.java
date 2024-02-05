package com.nova.horario.repository;

import com.nova.horario.model.Operatividad;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperatividadRepository extends JpaRepository<Operatividad, Integer> {
    @EntityGraph(attributePaths = {"horario"})
    @Query("select o from Operatividad o where o.unidad = ?1 and o.activo = true")
    List<Operatividad> findByUnidad(Integer unidad);

}