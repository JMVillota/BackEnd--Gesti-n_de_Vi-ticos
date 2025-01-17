package com.conecta.BackEnd.repository;

import com.conecta.BackEnd.entity.DocumentoViatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoViaticoRepository extends JpaRepository<DocumentoViatico, String> {
    
    List<DocumentoViatico> findByViaticoId(String viaticoId);

    @Query("""
        SELECT SUM(d.numeroArchivosPdf) 
        FROM DocumentoViatico d 
        WHERE d.numeroArchivosPdf > 0
        """)
    Optional<Long> countTotalDocumentos();
    
    @Query("""
        SELECT d 
        FROM DocumentoViatico d 
        WHERE d.viatico.id = :viaticoId 
        ORDER BY d.fechaCarga DESC
        """)
    List<DocumentoViatico> findByViaticoIdOrderByFechaCargaDesc(@Param("viaticoId") String viaticoId);
    
    @Query("""
        SELECT COUNT(d) 
        FROM DocumentoViatico d 
        WHERE d.viatico.id = :viaticoId
        """)
    long countByViaticoId(@Param("viaticoId") String viaticoId);
    
    boolean existsByViaticoId(String viaticoId);
}