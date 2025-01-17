package com.conecta.BackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager; 
import jakarta.persistence.Query;

@Service
public class IdGeneratorService {
    
    @Autowired
    private EntityManager entityManager;
    
    public String generateViaticoId() {
        Query query = entityManager.createNativeQuery(
            "SELECT MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)) FROM viaticos WHERE id LIKE 'v-%'");
        Object result = query.getSingleResult();
        int nextNum = (result != null) ? ((Number) result).intValue() + 1 : 1;
        return String.format("v-%03d", nextNum);
    }
    
    public String generateDocumentoId() {
        Query query = entityManager.createNativeQuery(
            "SELECT MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)) FROM documentos_viatico WHERE id LIKE 'd-%'");
        Object result = query.getSingleResult();
        int nextNum = (result != null) ? ((Number) result).intValue() + 1 : 1;
        return String.format("d-%03d", nextNum);
    }
}