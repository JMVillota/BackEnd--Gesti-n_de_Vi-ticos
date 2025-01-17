package com.conecta.BackEnd.repository;

import com.conecta.BackEnd.entity.DocumentoViatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentoViaticoRepository extends JpaRepository<DocumentoViatico, String> {
    List<DocumentoViatico> findByViaticoId(String viaticoId);
}