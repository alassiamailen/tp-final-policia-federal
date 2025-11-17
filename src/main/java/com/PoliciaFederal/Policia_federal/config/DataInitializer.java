package com.PoliciaFederal.Policia_federal.config;

import com.PoliciaFederal.Policia_federal.model.*;
import com.PoliciaFederal.Policia_federal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Clase que inicializa datos básicos en la base de datos al iniciar la aplicación.
 * Crea roles y usuarios de ejemplo.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VigilanteRepository vigilanteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo crear datos si no existen
        if (rolRepository.count() == 0) {
            crearRoles();
        }
        if (usuarioRepository.count() == 0) {
            crearUsuarios();
        } else {
            // Verificar si el usuario vigilante existe pero no tiene vigilante asociado
            asegurarVigilanteAsociado();
        }
    }
    
    private void asegurarVigilanteAsociado() {
        usuarioRepository.findByNombreUsuario("vigilante").ifPresent(usuario -> {
            if (usuario.getVigilante() == null) {
                // Crear el Vigilante y asociarlo al usuario
                Vigilante vigilante = new Vigilante("VIG-001", "Juan Pérez", 35);
                vigilante.setUsuario(usuario);
                vigilanteRepository.save(vigilante);
                
                // Actualizar el usuario para asociar el vigilante
                usuario.setVigilante(vigilante);
                usuarioRepository.save(usuario);
                
                System.out.println("✅ Vigilante asociado al usuario 'vigilante'");
            }
        });
    }

    private void crearRoles() {
        // Crear Rol Administrador
        Rol admin = new Rol("Administrador");
        admin.agregarPermiso(Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES);
        admin.agregarPermiso(Permiso.CONTRATAR_VIGILANTE);
        admin.agregarPermiso(Permiso.CARGAR_DETENIDO);
        admin.agregarPermiso(Permiso.ABRIR_CASO);
        admin.agregarPermiso(Permiso.CREAR_USUARIOS);
        admin.agregarPermiso(Permiso.CREAR_ENTIDAD);
        admin.agregarPermiso(Permiso.EDITAR_DATOS);
        admin.agregarPermiso(Permiso.ELIMINAR_DATOS);
        admin.agregarPermiso(Permiso.CERRAR_SESION);
        admin.agregarPermiso(Permiso.SALIR);
        rolRepository.save(admin);

        // Crear Rol Vigilante
        Rol vigilante = new Rol("Vigilante");
        vigilante.agregarPermiso(Permiso.CONSULTAR_MIS_DATOS_VIGILANTE);
        vigilante.agregarPermiso(Permiso.CERRAR_SESION);
        vigilante.agregarPermiso(Permiso.SALIR);
        rolRepository.save(vigilante);

        // Crear Rol Investigador
        Rol investigador = new Rol("Investigador");
        investigador.agregarPermiso(Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES);
        investigador.agregarPermiso(Permiso.CERRAR_SESION);
        investigador.agregarPermiso(Permiso.SALIR);
        rolRepository.save(investigador);

        System.out.println("✅ Roles creados exitosamente");
    }

    private void crearUsuarios() {
        // Buscar los roles creados
        Rol admin = rolRepository.findByNombre("Administrador")
            .orElseThrow(() -> new RuntimeException("Rol Administrador no encontrado"));
        
        Rol vigilanteRol = rolRepository.findByNombre("Vigilante")
            .orElseThrow(() -> new RuntimeException("Rol Vigilante no encontrado"));
        
        Rol investigador = rolRepository.findByNombre("Investigador")
            .orElseThrow(() -> new RuntimeException("Rol Investigador no encontrado"));

        // Crear usuario administrador
        Usuario usuarioAdmin = new Usuario("admin", "1234", admin);
        usuarioRepository.save(usuarioAdmin);

        // Crear usuario vigilante con su Vigilante asociado
        Usuario usuarioVigilante = new Usuario("vigilante", "1234", vigilanteRol);
        usuarioRepository.save(usuarioVigilante);
        
        // Crear el Vigilante y asociarlo al usuario
        Vigilante vigilante = new Vigilante("VIG-001", "Juan Pérez", 35);
        vigilante.setUsuario(usuarioVigilante);
        vigilanteRepository.save(vigilante);
        
        // Actualizar el usuario para asociar el vigilante
        usuarioVigilante.setVigilante(vigilante);
        usuarioRepository.save(usuarioVigilante);

        // Crear usuario investigador
        Usuario usuarioInvestigador = new Usuario("investigador", "1234", investigador);
        usuarioRepository.save(usuarioInvestigador);

        System.out.println("✅ Usuarios creados exitosamente");
        System.out.println("📝 Usuarios disponibles:");
        System.out.println("   - admin / 1234 (Administrador)");
        System.out.println("   - vigilante / 1234 (Vigilante) - Asociado a Vigilante: Juan Pérez (VIG-001)");
        System.out.println("   - investigador / 1234 (Investigador)");
    }
}

