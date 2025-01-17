package com.conecta.BackEnd.repository;

import com.conecta.BackEnd.entity.Viaticos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViaticosRepository extends JpaRepository<Viaticos, String> {
    
    List<Viaticos> findByNumeroIdentificacion(String numeroIdentificacion);

    long countByEstado(String estado);

    @Query("""
        SELECT COUNT(v) 
        FROM Viaticos v 
        WHERE MONTH(v.fechaRegistro) = MONTH(CURRENT_DATE()) 
        AND YEAR(v.fechaRegistro) = YEAR(CURRENT_DATE())
        """)
    long countViaticosDelMesActual();

    @Query("SELECT COUNT(v) FROM Viaticos v")
    long countTotalViaticos();

    @Query("SELECT COUNT(v) FROM Viaticos v WHERE v.estado = :estado")
    long countViaticosByEstado(@Param("estado") String estado);

    default long countViaticosPendientes() {
        return countViaticosByEstado("PENDIENTE");
    }

    @Query(value = """
        SELECT 
            MONTH(fecha_registro) as mes,
            COUNT(*) as cantidad
        FROM viaticos 
        WHERE YEAR(fecha_registro) = :año
        AND fecha_registro IS NOT NULL
        GROUP BY MONTH(fecha_registro)
        ORDER BY MONTH(fecha_registro)
        """, nativeQuery = true)
    List<Object[]> obtenerViaticosPorMes(@Param("año") int año);
    
    @Query(value = """
        SELECT v 
        FROM Viaticos v 
        WHERE v.fechaRegistro IS NOT NULL 
        AND v.estado = :estado 
        ORDER BY v.fechaRegistro DESC
        """)
    List<Viaticos> findAllByEstadoOrderByFechaRegistroDesc(@Param("estado") String estado);
}