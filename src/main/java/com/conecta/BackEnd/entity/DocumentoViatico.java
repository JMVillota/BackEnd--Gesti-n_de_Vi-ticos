package com.conecta.BackEnd.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos_viatico")
@Data
public class DocumentoViatico {
    
    @Id
    private String id;
    
    @Column(name = "viatico_id")
    private String viaticoId;
    
    @Column(name = "nombre_zip")
    private String nombreZip;
    
    @Column(name = "ruta_archivo")
    private String rutaArchivo;
    
    @Column(name = "numero_archivos_pdf")
    private Integer numeroArchivosPdf;
    
    @Column(name = "fecha_carga")
    private LocalDateTime fechaCarga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viatico_id", insertable = false, updatable = false)
    @JsonIgnore
    private Viaticos viatico;
}