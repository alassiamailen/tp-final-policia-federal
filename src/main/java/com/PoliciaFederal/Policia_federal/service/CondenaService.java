package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Condena;
import com.PoliciaFederal.Policia_federal.repository.CondenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CondenaService {
    
    @Autowired
    private CondenaRepository condenaRepository;
    
    public List<Condena> listarTodas() {
        return condenaRepository.findAll();
    }
    
    public Optional<Condena> buscarPorId(Long id) {
        return condenaRepository.findById(id);
    }
    
    public List<Condena> buscarPorAsaltanteId(Long asaltanteId) {
        return condenaRepository.findByAsaltanteId(asaltanteId);
    }
    
    public Condena guardar(Condena condena) {
        return condenaRepository.save(condena);
    }
    
    public void eliminar(Long id) {
        if (!condenaRepository.existsById(id)) {
            throw new RuntimeException("Condena no encontrada");
        }
        condenaRepository.deleteById(id);
    }
}

