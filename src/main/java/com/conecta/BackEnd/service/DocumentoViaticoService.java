package com.conecta.BackEnd.service;

import com.conecta.BackEnd.dto.DocumentoViaticoDTO;
import com.conecta.BackEnd.entity.DocumentoViatico;
import com.conecta.BackEnd.repository.DocumentoViaticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoViaticoService {

    @Autowired
    private DocumentoViaticoRepository documentoViaticoRepository;

    /**
     * Convierte una entidad DocumentoViatico a DTO
     */
    private DocumentoViaticoDTO convertirADTO(DocumentoViatico documento) {
        DocumentoViaticoDTO dto = new DocumentoViaticoDTO();
        dto.setId(documento.getId());
        dto.setViaticoId(documento.getViaticoId());
        dto.setNombreZip(documento.getNombreZip());
        dto.setRutaArchivo(documento.getRutaArchivo());
        dto.setNumeroArchivosPdf(documento.getNumeroArchivosPdf());
        dto.setFechaCarga(documento.getFechaCarga());
        return dto;
    }

    /**
     * Obtiene los documentos asociados a un viático específico
     * @param viaticoId ID del viático
     * @return Lista de DTOs de documentos encontrados
     */
    @Transactional(readOnly = true)
    public List<DocumentoViaticoDTO> obtenerDocumentosPorViaticoId(String viaticoId) {
        return documentoViaticoRepository.findByViaticoId(viaticoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}