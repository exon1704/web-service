package com.nova.horario.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO for {@link com.nova.horario.model.Operatividad}
 */
@Value
public class OperatividadDTO implements Serializable {
    Integer id;
    HorarioDto horario;
    LocalTime apertura;
    LocalTime cierre;

    /**
     * DTO for {@link com.nova.horario.model.Horario}
     */
    @Value
    public static class HorarioDto implements Serializable {
        Integer id;
        String nombre;
    }
}