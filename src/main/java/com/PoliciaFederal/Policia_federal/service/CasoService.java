package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Caso;
import com.PoliciaFederal.Policia_federal.repository.CasoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CasoService {
    
    @Autowired
    private CasoRepository casoRepository;
    
    public List<Caso> listarTodos() {
        return casoRepository.findAll();
    }
    
    public Optional<Caso> buscarPorId(Long id) {
        return casoRepository.findById(id);
    }
    
    public List<Caso> buscarPorJuezId(Long juezId) {
        return casoRepository.findByJuezId(juezId);
    }
    
    public Caso guardar(Caso caso) {
        return casoRepository.save(caso);
    }
    
    public void eliminar(Long id) {
        if (!casoRepository.existsById(id)) {
            throw new RuntimeException("Caso no encontrado");
        }
        casoRepository.deleteById(id);
    }
}

