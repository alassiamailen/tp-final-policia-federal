package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Sucursal;
import com.PoliciaFederal.Policia_federal.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private CodigoGeneratorService codigoGeneratorService;
    
    public List<Sucursal> listarTodas() {
        return sucursalRepository.findAll();
    }
    
    public Optional<Sucursal> buscarPorId(Long id) {
        return sucursalRepository.findById(id);
    }
    
    public Sucursal guardar(Sucursal sucursal) {
        // Generar código automáticamente si no está presente
        if (sucursal.getCodigo() == null || sucursal.getCodigo().trim().isEmpty()) {
            sucursal.setCodigo(codigoGeneratorService.generarCodigoSucursal());
        }
        return sucursalRepository.save(sucursal);
    }
    
    public Sucursal actualizar(Long id, Sucursal sucursalActualizada) {
        Sucursal sucursal = sucursalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        
        // No actualizar el código - es inmutable
        sucursal.setNombre(sucursalActualizada.getNombre());
        sucursal.setDomicilio(sucursalActualizada.getDomicilio());
        sucursal.setCantidadDeEmpleados(sucursalActualizada.getCantidadDeEmpleados());
        
        return sucursalRepository.save(sucursal);
    }
    
    public void eliminar(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new RuntimeException("Sucursal no encontrada");
        }
        sucursalRepository.deleteById(id);
    }
}

