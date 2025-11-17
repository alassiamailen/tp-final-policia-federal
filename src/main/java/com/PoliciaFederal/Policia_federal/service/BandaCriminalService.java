package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.BandaCriminal;
import com.PoliciaFederal.Policia_federal.repository.BandaCriminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BandaCriminalService {
    
    @Autowired
    private BandaCriminalRepository bandaCriminalRepository;
    
    public List<BandaCriminal> listarTodas() {
        return bandaCriminalRepository.findAll();
    }
    
    public Optional<BandaCriminal> buscarPorId(Long id) {
        return bandaCriminalRepository.findById(id);
    }
    
    public Optional<BandaCriminal> buscarPorNumeroIdentificacion(Integer numeroIdentificacion) {
        return bandaCriminalRepository.findByNumeroIdentificacion(numeroIdentificacion);
    }
    
    public BandaCriminal guardar(BandaCriminal bandaCriminal) {
        // Validar si el número de identificación ya existe
        if (bandaCriminal.getNumeroIdentificacion() != null) {
            Optional<BandaCriminal> existente = buscarPorNumeroIdentificacion(bandaCriminal.getNumeroIdentificacion());
            if (existente.isPresent() && 
                (bandaCriminal.getId() == null || !existente.get().getId().equals(bandaCriminal.getId()))) {
                throw new RuntimeException("El número de identificación " + bandaCriminal.getNumeroIdentificacion() + 
                    " ya existe. Por favor, elija otro número.");
            }
        }
        return bandaCriminalRepository.save(bandaCriminal);
    }
    
    public void eliminar(Long id) {
        if (!bandaCriminalRepository.existsById(id)) {
            throw new RuntimeException("Banda criminal no encontrada");
        }
        bandaCriminalRepository.deleteById(id);
    }
}

