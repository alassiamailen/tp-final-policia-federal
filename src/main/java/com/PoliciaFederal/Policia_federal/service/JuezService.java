package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Juez;
import com.PoliciaFederal.Policia_federal.repository.JuezRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JuezService {
    
    @Autowired
    private JuezRepository juezRepository;
    
    @Autowired
    private CodigoGeneratorService codigoGeneratorService;
    
    public List<Juez> listarTodos() {
        return juezRepository.findAll();
    }
    
    public Optional<Juez> buscarPorId(Long id) {
        return juezRepository.findById(id);
    }
    
    public Juez guardar(Juez juez) {
        // Generar clave interna automáticamente si no está presente
        if (juez.getClaveInternaJuzgado() == null || juez.getClaveInternaJuzgado().trim().isEmpty()) {
            juez.setClaveInternaJuzgado(codigoGeneratorService.generarClaveInternaJuzgado());
        }
        return juezRepository.save(juez);
    }
    
    public Juez actualizar(Long id, Juez juezActualizado) {
        Juez juez = juezRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Juez no encontrado"));
        
        // No actualizar la clave interna del juzgado (código) - es inmutable
        juez.setNombreApellido(juezActualizado.getNombreApellido());
        juez.setAniosDeServicio(juezActualizado.getAniosDeServicio());
        
        return juezRepository.save(juez);
    }
    
    public void eliminar(Long id) {
        if (!juezRepository.existsById(id)) {
            throw new RuntimeException("Juez no encontrado");
        }
        juezRepository.deleteById(id);
    }
}

