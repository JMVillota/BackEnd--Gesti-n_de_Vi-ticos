package com.conecta.BackEnd.service;

import com.conecta.BackEnd.entity.Viaticos;
import com.conecta.BackEnd.repository.ViaticosRepository;
import com.conecta.BackEnd.repository.DocumentoViaticoRepository;
import com.conecta.BackEnd.dto.ViaticosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class ViaticosService {
    
    private static final Logger logger = LoggerFactory.getLogger(ViaticosService.class);

    @Autowired
    private ViaticosRepository viaticosRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private DocumentoViaticoRepository documentoViaticoRepository;

    public Viaticos crearViatico(ViaticosDTO request) {
        try {
            Viaticos viatico = new Viaticos();
            viatico.setId(idGeneratorService.generateViaticoId());
            viatico.setNumeroIdentificacion(request.getNumeroIdentificacion());
            viatico.setNombreEmpleado(request.getNombreEmpleado());
            viatico.setFechaRegistro(request.getFechaRegistro());
            viatico.setMotivoViaje(request.getMotivoViaje());
            viatico.setClienteProyecto(request.getClienteProyecto());
            viatico.setFechaInicioViaje(request.getFechaInicioViaje());
            viatico.setFechaFinViaje(request.getFechaFinViaje());
            viatico.setCorreoAprobador(request.getCorreoAprobador());
            viatico.setEstado("PENDIENTE");

            return viaticosRepository.save(viatico);
        } catch (Exception e) {
            logger.error("Error al crear viático: {}", e.getMessage());
            throw new RuntimeException("Error al crear el viático");
        }
    }

    public List<ViaticosDTO> obtenerViaticosPorIdentificacion(String numeroIdentificacion) {
        try {
            List<Viaticos> viaticos = viaticosRepository.findByNumeroIdentificacion(numeroIdentificacion);
            return viaticos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener viáticos por identificación: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los viáticos");
        }
    }

    public List<ViaticosDTO> obtenerTodosLosViaticos() {
        try {
            List<Viaticos> viaticos = viaticosRepository.findAll();
            return viaticos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener todos los viáticos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los viáticos");
        }
    }

    public Optional<ViaticosDTO> obtenerViaticoPorId(String id) {
        try {
            return viaticosRepository.findById(id)
                    .map(this::convertirADTO);
        } catch (Exception e) {
            logger.error("Error al obtener viático por ID: {}", e.getMessage());
            throw new RuntimeException("Error al obtener el viático");
        }
    }

    public Viaticos actualizarViatico(String id, ViaticosDTO request) {
        try {
            return viaticosRepository.findById(id)
                    .map(viatico -> {
                        viatico.setNumeroIdentificacion(request.getNumeroIdentificacion());
                        viatico.setNombreEmpleado(request.getNombreEmpleado());
                        viatico.setFechaRegistro(request.getFechaRegistro());
                        viatico.setMotivoViaje(request.getMotivoViaje());
                        viatico.setClienteProyecto(request.getClienteProyecto());
                        viatico.setFechaInicioViaje(request.getFechaInicioViaje());
                        viatico.setFechaFinViaje(request.getFechaFinViaje());
                        viatico.setCorreoAprobador(request.getCorreoAprobador());
                        viatico.setEstado(request.getEstado());
                        return viaticosRepository.save(viatico);
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Viático no encontrado con ID: " + id));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al actualizar viático: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el viático");
        }
    }

    public void eliminarViatico(String id) {
        try {
            viaticosRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar viático: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el viático");
        }
    }

    private ViaticosDTO convertirADTO(Viaticos viatico) {
        ViaticosDTO dto = new ViaticosDTO();
        dto.setId(viatico.getId());
        dto.setNumeroIdentificacion(viatico.getNumeroIdentificacion());
        dto.setNombreEmpleado(viatico.getNombreEmpleado());
        dto.setFechaRegistro(viatico.getFechaRegistro());
        dto.setMotivoViaje(viatico.getMotivoViaje());
        dto.setClienteProyecto(viatico.getClienteProyecto());
        dto.setFechaInicioViaje(viatico.getFechaInicioViaje());
        dto.setFechaFinViaje(viatico.getFechaFinViaje());
        dto.setCorreoAprobador(viatico.getCorreoAprobador());
        dto.setEstado(viatico.getEstado());
        return dto;
    }

    public Viaticos actualizarEstado(String id, String nuevoEstado) {
        try {
            return viaticosRepository.findById(id)
                    .map(viatico -> {
                        viatico.setEstado(nuevoEstado);
                        return viaticosRepository.save(viatico);
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Viático no encontrado con ID: " + id));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al actualizar estado: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el estado del viático");
        }
    }

    public Map<String, Object> obtenerMetricasDashboard() {
        try {
            Map<String, Object> metricas = new HashMap<>();
            metricas.put("totalViaticos", viaticosRepository.countTotalViaticos());
            metricas.put("viaticosPendientes", viaticosRepository.countViaticosPendientes());
            metricas.put("documentosTotales", documentoViaticoRepository.count());
            metricas.put("viaticosDelMes", viaticosRepository.countViaticosDelMesActual());
            return metricas;
        } catch (Exception e) {
            logger.error("Error al obtener métricas del dashboard: {}", e.getMessage());
            throw new RuntimeException("Error al obtener las métricas");
        }
    }

    public Map<String, Object> obtenerDatosGrafica(int año) {
        try {
            List<Object[]> resultados = viaticosRepository.obtenerViaticosPorMes(año);
            List<String> labelX = new ArrayList<>();
            List<Long> labelY = new ArrayList<>();
            
            resultados.forEach(resultado -> {
                int mes = ((Number) resultado[0]).intValue();
                labelX.add(obtenerNombreMes(mes));
                labelY.add(((Number) resultado[1]).longValue());
            });
            
            Map<String, Object> datos = new HashMap<>();
            datos.put("X", labelX);
            datos.put("Y", labelY);
            return datos;
        } catch (Exception e) {
            logger.error("Error al obtener datos de la gráfica para el año {}: {}", año, e.getMessage());
            throw new RuntimeException("Error al obtener los datos de la gráfica");
        }
    }

    private String obtenerNombreMes(int mes) {
        return switch (mes) {
            case 1 -> "Enero";
            case 2 -> "Febrero";
            case 3 -> "Marzo";
            case 4 -> "Abril";
            case 5 -> "Mayo";
            case 6 -> "Junio";
            case 7 -> "Julio";
            case 8 -> "Agosto";
            case 9 -> "Septiembre";
            case 10 -> "Octubre";
            case 11 -> "Noviembre";
            case 12 -> "Diciembre";
            default -> "";
        };
    }
}