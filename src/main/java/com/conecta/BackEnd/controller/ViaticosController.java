package com.conecta.BackEnd.controller;

import com.conecta.BackEnd.dto.ViaticosDTO;
import com.conecta.BackEnd.entity.Viaticos;
import com.conecta.BackEnd.entity.DocumentoViatico;
import com.conecta.BackEnd.repository.ViaticosRepository;
import com.conecta.BackEnd.repository.DocumentoViaticoRepository;
import com.conecta.BackEnd.service.ViaticoValidationService;
import com.conecta.BackEnd.service.FileProcessingService;
import com.conecta.BackEnd.service.ViaticosService;
import com.conecta.BackEnd.service.IdGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/viaticos")
@CrossOrigin(origins = "*")
public class ViaticosController {

    private static final Logger logger = LoggerFactory.getLogger(ViaticosController.class);

    @Autowired
    private ViaticosRepository viaticosRepository;

    @Autowired
    private DocumentoViaticoRepository documentoViaticoRepository;

    @Autowired
    private ViaticoValidationService validationService;

    @Autowired
    private FileProcessingService fileProcessingService;

    @Autowired
    private ViaticosService viaticosService;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @GetMapping("/consulta/{numeroIdentificacion}")
    public ResponseEntity<List<ViaticosDTO>> consultarViaticos(@PathVariable String numeroIdentificacion) {
        try {
            List<ViaticosDTO> viaticos = viaticosService.obtenerViaticosPorIdentificacion(numeroIdentificacion);
            return ResponseEntity.ok(viaticos);
        } catch (Exception e) {
            logger.error("Error al consultar viáticos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createViatico(@RequestBody ViaticosDTO request) {
        try {
            validationService.validateFechaRegistro(request.getFechaRegistro());
            validationService.validateFechasViaje(request.getFechaInicioViaje(), request.getFechaFinViaje());

            Viaticos viatico = viaticosService.crearViatico(request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Viático creado exitosamente");
            response.put("id", viatico.getId());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación al crear viático: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Error al crear viático: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/{viaticoId}/documentos")
    public ResponseEntity<?> uploadDocumentos(@PathVariable String viaticoId, @RequestParam("file") MultipartFile zipFile) {
        try {
            Viaticos viatico = viaticosRepository.findById(viaticoId)
                    .orElseThrow(() -> new IllegalArgumentException("Viático no encontrado"));

            int pdfCount = fileProcessingService.countPdfFilesInZip(zipFile);
            String filePath = fileProcessingService.saveZipFile(zipFile, viaticoId);

            DocumentoViatico documento = new DocumentoViatico();
            documento.setId(idGeneratorService.generateDocumentoId());
            documento.setViatico(viatico);
            documento.setNombreZip(zipFile.getOriginalFilename());
            documento.setRutaArchivo(filePath);
            documento.setNumeroArchivosPdf(pdfCount);

            documentoViaticoRepository.save(documento);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Documentos subidos exitosamente");
            response.put("numeroArchivos", pdfCount);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al subir documentos: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Error al procesar documentos: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al procesar los documentos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<ViaticosDTO>> getAllViaticos() {
        try {
            return ResponseEntity.ok(viaticosService.obtenerTodosLosViaticos());
        } catch (Exception e) {
            logger.error("Error al obtener todos los viáticos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViaticosDTO> getViaticoById(@PathVariable String id) {
        try {
            Optional<ViaticosDTO> viatico = viaticosService.obtenerViaticoPorId(id);
            return viatico.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error al obtener viático por ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateViatico(@PathVariable String id, @RequestBody ViaticosDTO request) {
        try {
            Viaticos viatico = viaticosService.actualizarViatico(id, request);
            return ResponseEntity.ok(viatico);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar viático: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            logger.error("Error al actualizar viático: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteViatico(@PathVariable String id) {
        try {
            viaticosService.eliminarViatico(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error al eliminar viático: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el viático");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateEstado(@PathVariable String id, @RequestParam String nuevoEstado) {
        try {
            Viaticos viatico = viaticosService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(viatico);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar estado: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            logger.error("Error al actualizar estado: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/metricas")
    public ResponseEntity<?> obtenerMetricasDashboard() {
        try {
            Map<String, Object> metricas = viaticosService.obtenerMetricasDashboard();
            return ResponseEntity.ok(metricas);
        } catch (Exception e) {
            logger.error("Error al obtener métricas: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al obtener métricas");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/grafica-mensual/{año}")
    public ResponseEntity<Map<String, Object>> obtenerGraficaMensual(@PathVariable int año) {
        try {
            Map<String, Object> datos = viaticosService.obtenerDatosGrafica(año);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", datos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener gráfica mensual: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error al obtener datos de la gráfica");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}