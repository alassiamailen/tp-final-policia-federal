package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Asaltante;
import com.PoliciaFederal.Policia_federal.repository.AsaltanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsaltanteService {
    
    @Autowired
    private AsaltanteRepository asaltanteRepository;
    
    @Autowired
    private CodigoGeneratorService codigoGeneratorService;
    
    public List<Asaltante> listarTodos() {
        return asaltanteRepository.findAll();
    }
    
    public Optional<Asaltante> buscarPorId(Long id) {
        return asaltanteRepository.findById(id);
    }
    
    public Asaltante guardar(Asaltante asaltante) {
        // Generar código automáticamente si no está presente
        if (asaltante.getCodigo() == null || asaltante.getCodigo().trim().isEmpty()) {
            asaltante.setCodigo(codigoGeneratorService.generarCodigoAsaltante());
        }
        return asaltanteRepository.save(asaltante);
    }
    
    public Asaltante actualizar(Long id, Asaltante asaltanteActualizado) {
        Asaltante asaltante = asaltanteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asaltante no encontrado"));
        
        asaltante.setCodigo(asaltanteActualizado.getCodigo());
        asaltante.setNombreApellido(asaltanteActualizado.getNombreApellido());
        asaltante.setBandaCriminal(asaltanteActualizado.getBandaCriminal());
        
        return asaltanteRepository.save(asaltante);
    }
    
    public void eliminar(Long id) {
        if (!asaltanteRepository.existsById(id)) {
            throw new RuntimeException("Asaltante no encontrado");
        }
        asaltanteRepository.deleteById(id);
    }
}

