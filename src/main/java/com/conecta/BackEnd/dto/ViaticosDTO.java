package com.conecta.BackEnd.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ViaticosDTO {
    private String id;
    private String numeroIdentificacion;
    private String nombreEmpleado;
    private LocalDate fechaRegistro;
    private String motivoViaje;
    private String clienteProyecto;
    private LocalDate fechaInicioViaje;
    private LocalDate fechaFinViaje;
    private String correoAprobador;
    private String estado;
}