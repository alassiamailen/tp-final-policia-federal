package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Vigilante;
import com.PoliciaFederal.Policia_federal.repository.VigilanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VigilanteService {
    
    @Autowired
    private VigilanteRepository vigilanteRepository;
    
    @Autowired
    private CodigoGeneratorService codigoGeneratorService;
    
    public List<Vigilante> listarTodos() {
        return vigilanteRepository.findAll();
    }
    
    public Optional<Vigilante> buscarPorId(Long id) {
        return vigilanteRepository.findById(id);
    }
    
    public Vigilante guardar(Vigilante vigilante) {
        // Generar código automáticamente si no está presente
        if (vigilante.getCodigo() == null || vigilante.getCodigo().trim().isEmpty()) {
            vigilante.setCodigo(codigoGeneratorService.generarCodigoVigilante());
        }
        return vigilanteRepository.save(vigilante);
    }
    
    public Vigilante actualizar(Long id, Vigilante vigilanteActualizado) {
        Vigilante vigilante = vigilanteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));
        
        // No actualizar el código - es inmutable
        vigilante.setNombreApellido(vigilanteActualizado.getNombreApellido());
        vigilante.setEdad(vigilanteActualizado.getEdad());
        
        return vigilanteRepository.save(vigilante);
    }
    
    public void eliminar(Long id) {
        if (!vigilanteRepository.existsById(id)) {
            throw new RuntimeException("Vigilante no encontrado");
        }
        vigilanteRepository.deleteById(id);
    }
    
    public Optional<Vigilante> buscarPorUsuario(Long usuarioId) {
        return vigilanteRepository.findByUsuarioId(usuarioId);
    }
}

