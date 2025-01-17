package com.conecta.BackEnd.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;


@Data
@Entity
@Table(name = "viaticos", indexes = {
    @Index(name = "idx_viaticos_numero_identificacion", columnList = "numero_identificacion"),
    @Index(name = "idx_viaticos_estado", columnList = "estado"),
    @Index(name = "idx_viaticos_fecha_registro", columnList = "fecha_registro")
})
public class Viaticos {

    @Id
    @Column(nullable = false, length = 10)
    private String id;
    
    @Column(name = "numero_identificacion", nullable = false, length = 20)
    private String numeroIdentificacion;
    
    @Column(name = "nombre_empleado", nullable = false, length = 100)
    private String nombreEmpleado;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
    
    @Column(name = "motivo_viaje", nullable = false, columnDefinition = "TEXT")
    private String motivoViaje;
    
    @Column(name = "cliente_proyecto", nullable = false, length = 100)
    private String clienteProyecto;
    
    @Column(name = "fecha_inicio_viaje", nullable = false)
    private LocalDate fechaInicioViaje;
    
    @Column(name = "fecha_fin_viaje", nullable = false)
    private LocalDate fechaFinViaje;
    
    @Column(name = "correo_aprobador", nullable = false, length = 100)
    private String correoAprobador;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PENDIENTE";
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}