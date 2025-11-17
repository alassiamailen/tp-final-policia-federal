package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.ContratoSucVig;
import com.PoliciaFederal.Policia_federal.repository.ContratoSucVigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContratoSucVigService {
    
    @Autowired
    private ContratoSucVigRepository contratoSucVigRepository;
    
    public List<ContratoSucVig> listarTodos() {
        return contratoSucVigRepository.findAll();
    }
    
    public Optional<ContratoSucVig> buscarPorId(Long id) {
        return contratoSucVigRepository.findById(id);
    }
    
    public List<ContratoSucVig> buscarPorVigilanteId(Long vigilanteId) {
        return contratoSucVigRepository.findByVigilanteId(vigilanteId);
    }
    
    public List<ContratoSucVig> buscarPorSucursalId(Long sucursalId) {
        return contratoSucVigRepository.findBySucursalId(sucursalId);
    }
    
    public ContratoSucVig guardar(ContratoSucVig contrato) {
        // Validar fechas
        LocalDate hoy = LocalDate.now();
        if (contrato.getFechaInicio().isBefore(hoy)) {
            throw new RuntimeException("La fecha de inicio no puede ser anterior al día de hoy.");
        }
        
        if (!contrato.getFechaFin().isAfter(contrato.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }
        
        return contratoSucVigRepository.save(contrato);
    }
    
    public void eliminar(Long id) {
        if (!contratoSucVigRepository.existsById(id)) {
            throw new RuntimeException("Contrato no encontrado");
        }
        contratoSucVigRepository.deleteById(id);
    }
}

