package com.conecta.BackEnd.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentoViaticoDTO {
    private String id;
    private String viaticoId;
    private String nombreZip;
    private String rutaArchivo;
    private Integer numeroArchivosPdf;
    private LocalDateTime fechaCarga;
}