package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Asalto;
import com.PoliciaFederal.Policia_federal.repository.AsaltoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsaltoService {
    
    @Autowired
    private AsaltoRepository asaltoRepository;
    
    public List<Asalto> listarTodos() {
        return asaltoRepository.findAll();
    }
    
    public Optional<Asalto> buscarPorId(Long id) {
        return asaltoRepository.findById(id);
    }
    
    public List<Asalto> buscarPorSucursalId(Long sucursalId) {
        return asaltoRepository.findBySucursalId(sucursalId);
    }
    
    public List<Asalto> buscarPorAsaltanteId(Long asaltanteId) {
        return asaltoRepository.findByAsaltanteId(asaltanteId);
    }
    
    public Asalto guardar(Asalto asalto) {
        // Validar que la fecha no sea futura
        LocalDate hoy = LocalDate.now();
        if (asalto.getFecha().isAfter(hoy)) {
            throw new RuntimeException("La fecha del asalto no puede ser futura. Debe ser la fecha actual o una fecha pasada.");
        }
        
        return asaltoRepository.save(asalto);
    }
    
    public void eliminar(Long id) {
        if (!asaltoRepository.existsById(id)) {
            throw new RuntimeException("Asalto no encontrado");
        }
        asaltoRepository.deleteById(id);
    }
}

