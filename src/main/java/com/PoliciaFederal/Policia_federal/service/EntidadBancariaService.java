package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.EntidadBancaria;
import com.PoliciaFederal.Policia_federal.repository.EntidadBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntidadBancariaService {
    
    @Autowired
    private EntidadBancariaRepository entidadBancariaRepository;
    
    @Autowired
    private CodigoGeneratorService codigoGeneratorService;
    
    public List<EntidadBancaria> listarTodas() {
        return entidadBancariaRepository.findAll();
    }
    
    public Optional<EntidadBancaria> buscarPorId(Long id) {
        return entidadBancariaRepository.findById(id);
    }
    
    public EntidadBancaria guardar(EntidadBancaria entidadBancaria) {
        // Generar código automáticamente si no está presente
        if (entidadBancaria.getCodigo() == null || entidadBancaria.getCodigo().trim().isEmpty()) {
            entidadBancaria.setCodigo(codigoGeneratorService.generarCodigoEntidadBancaria());
        }
        return entidadBancariaRepository.save(entidadBancaria);
    }
    
    public EntidadBancaria actualizar(Long id, EntidadBancaria entidadActualizada) {
        EntidadBancaria entidad = entidadBancariaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entidad bancaria no encontrada"));
        
        // No actualizar el código - es inmutable
        entidad.setNombre(entidadActualizada.getNombre());
        entidad.setDomicilio(entidadActualizada.getDomicilio());
        
        return entidadBancariaRepository.save(entidad);
    }
    
    public void eliminar(Long id) {
        if (!entidadBancariaRepository.existsById(id)) {
            throw new RuntimeException("Entidad bancaria no encontrada");
        }
        entidadBancariaRepository.deleteById(id);
    }
}

