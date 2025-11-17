package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Rol;
import com.PoliciaFederal.Policia_federal.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {
    
    @Autowired
    private RolRepository rolRepository;
    
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }
    
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }
    
    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
    
    public Rol guardar(Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new RuntimeException("El rol ya existe");
        }
        return rolRepository.save(rol);
    }
    
    public Rol actualizar(Long id, Rol rolActualizado) {
        Rol rol = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        if (!rol.getNombre().equals(rolActualizado.getNombre()) 
            && rolRepository.existsByNombre(rolActualizado.getNombre())) {
            throw new RuntimeException("El nombre del rol ya existe");
        }
        
        rol.setNombre(rolActualizado.getNombre());
        rol.setPermisos(rolActualizado.getPermisos());
        
        return rolRepository.save(rol);
    }
    
    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }
}

