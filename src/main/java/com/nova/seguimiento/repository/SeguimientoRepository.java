package com.nova.seguimiento.repository;

import com.nova.seguimiento.model.Seguimiento;
import com.nova.seguimiento.model.SeguimientoInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Integer> {
    List<SeguimientoInfo> findByTicket_IdOrderByFechaDesc(Integer id);


}