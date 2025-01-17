package com.conecta.BackEnd.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "documentos_viatico", indexes = {
    @Index(name = "idx_documentos_viatico_id", columnList = "viatico_id")
})
public class DocumentoViatico {
    
    @Id
    @Column(nullable = false, length = 10)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viatico_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Viaticos viatico;
    
    @Column(name = "nombre_zip", nullable = false, length = 255)
    private String nombreZip;
    
    @Column(name = "ruta_archivo", nullable = false, length = 500)
    private String rutaArchivo;
    
    @Column(name = "numero_archivos_pdf", nullable = false)
    private Integer numeroArchivosPdf;
    
    @Column(name = "fecha_carga", nullable = false, updatable = false)
    private LocalDateTime fechaCarga;
    
    @PrePersist
    protected void onCreate() {
        fechaCarga = LocalDateTime.now();
    }
}