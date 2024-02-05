package com.nova.area.repository;

import com.nova.area.model.Area;
import com.nova.area.model.AreaInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    @EntityGraph(attributePaths = {"reportes"})
    @Query("SELECT a.id as id , a.nombre as nombre FROM Area a")
    List<AreaInfo> obtenerAreas();


}