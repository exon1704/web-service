package com.nova.unidad.repository;

import com.nova.unidad.model.Unidad;
import com.nova.unidad.model.UnidadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnidadRepository extends JpaRepository<Unidad, Integer> {
    @Query(value = "CALL unidades()", nativeQuery = true)
    List<UnidadInfo> obtenerUnidades();

    Optional<UnidadInfo> findByClave(String clave);


    <T> Optional<T> findById(Integer integer, Class<T> type);
}