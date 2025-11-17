package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.model.Usuario;
import com.PoliciaFederal.Policia_federal.model.Vigilante;
import com.PoliciaFederal.Policia_federal.repository.UsuarioRepository;
import com.PoliciaFederal.Policia_federal.repository.VigilanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VigilanteRepository vigilanteRepository;
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    
    public Usuario guardar(Usuario usuario) {
        // Si el usuario tiene ID, es una actualización, no validar duplicados
        if (usuario.getId() == null) {
            // Solo validar duplicados al crear un nuevo usuario
            if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
                throw new RuntimeException("El nombre de usuario ya existe");
            }
        }
        return usuarioRepository.save(usuario);
    }
    
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Verificar si el nombre de usuario cambió y si ya existe
        if (!usuario.getNombreUsuario().equals(usuarioActualizado.getNombreUsuario()) 
            && usuarioRepository.existsByNombreUsuario(usuarioActualizado.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        // Preservar la asociación con el vigilante si ya existía
        // (el formulario no envía este campo, así que viene como null)
        Vigilante vigilanteOriginal = usuario.getVigilante();
        
        usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuario.setPassword(usuarioActualizado.getPassword());
        usuario.setRol(usuarioActualizado.getRol());
        
        // Solo actualizar el vigilante si se proporciona explícitamente
        // Si no se proporciona (null), preservar el original
        if (usuarioActualizado.getVigilante() != null) {
            usuario.setVigilante(usuarioActualizado.getVigilante());
        } else {
            // Preservar la asociación original con el vigilante
            usuario.setVigilante(vigilanteOriginal);
        }
        
        return usuarioRepository.save(usuario);
    }
    
    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Romper la relación bidireccional con Vigilante antes de eliminar
        if (usuario.getVigilante() != null) {
            Vigilante vigilante = usuario.getVigilante();
            // Romper la relación bidireccional
            vigilante.setUsuario(null);
            usuario.setVigilante(null);
            // Guardar el vigilante para actualizar la relación en la base de datos
            vigilanteRepository.save(vigilante);
        }
        
        usuarioRepository.delete(usuario);
    }
    
    public boolean autenticar(String nombreUsuario, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        return usuario.isPresent() && usuario.get().autenticar(nombreUsuario, password);
    }
    
    /**
     * Obtiene la lista de usuarios con rol "Vigilante" que no tienen un vigilante asignado.
     * Estos usuarios pueden ser vinculados a un nuevo vigilante.
     */
    public List<Usuario> buscarUsuariosVigilanteDisponibles() {
        return usuarioRepository.findUsuariosVigilanteSinVigilanteAsignado();
    }
}

