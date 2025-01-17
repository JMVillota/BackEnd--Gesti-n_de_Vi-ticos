package com.conecta.BackEnd.controller;

import com.conecta.BackEnd.dto.DocumentoViaticoDTO;
import com.conecta.BackEnd.service.DocumentoViaticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos-viatico")
@CrossOrigin(origins = "*")
public class DocumentoViaticoController {

    @Autowired
    private DocumentoViaticoService documentoViaticoService;

    @GetMapping("/viatico/{viaticoId}")
    public ResponseEntity<List<DocumentoViaticoDTO>> obtenerDocumentosPorViaticoId(
            @PathVariable String viaticoId) {
        List<DocumentoViaticoDTO> documentos = documentoViaticoService.obtenerDocumentosPorViaticoId(viaticoId);
        return ResponseEntity.ok(documentos);
    }
}