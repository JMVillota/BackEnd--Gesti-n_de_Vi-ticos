package com.conecta.BackEnd.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class ViaticoValidationService {
    
    private static final Logger logger = LoggerFactory.getLogger(ViaticoValidationService.class);
    private static final int DIAS_MAXIMOS_ATRAS = 90;
    
    public void validateFechaRegistro(LocalDate fechaRegistro) {
        try {
            Objects.requireNonNull(fechaRegistro, "La fecha de registro no puede ser nula");
            
            LocalDate today = LocalDate.now();
            LocalDate ninetyDaysAgo = today.minusDays(DIAS_MAXIMOS_ATRAS);
            
            if (fechaRegistro.isAfter(today)) {
                logger.warn("Intento de registro con fecha futura: {}", fechaRegistro);
                throw new IllegalArgumentException("La fecha de registro no puede ser mayor a hoy");
            }
            
            if (fechaRegistro.isBefore(ninetyDaysAgo)) {
                logger.warn("Intento de registro con fecha anterior a {} días: {}", DIAS_MAXIMOS_ATRAS, fechaRegistro);
                throw new IllegalArgumentException("La fecha de registro no puede ser menor a 90 días atrás");
            }
            
        } catch (NullPointerException e) {
            logger.error("Error de validación: fecha de registro nula");
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al validar fecha de registro: {}", e.getMessage());
            throw new IllegalArgumentException("Error al validar la fecha de registro");
        }
    }
    
    public void validateFechasViaje(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula");
            Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula");
            
            if (fechaFin.isBefore(fechaInicio)) {
                logger.warn("Intento de registro con fechas inválidas: inicio={}, fin={}", fechaInicio, fechaFin);
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
            }
            
            // Validación adicional para evitar fechas muy lejanas
            LocalDate maxFutureDate = LocalDate.now().plusYears(1);
            if (fechaInicio.isAfter(maxFutureDate) || fechaFin.isAfter(maxFutureDate)) {
                logger.warn("Intento de registro con fechas muy futuras: inicio={}, fin={}", fechaInicio, fechaFin);
                throw new IllegalArgumentException("Las fechas no pueden ser superiores a un año en el futuro");
            }
            
        } catch (NullPointerException e) {
            logger.error("Error de validación: fechas nulas");
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al validar fechas de viaje: {}", e.getMessage());
            throw new IllegalArgumentException("Error al validar las fechas del viaje");
        }
    }
}