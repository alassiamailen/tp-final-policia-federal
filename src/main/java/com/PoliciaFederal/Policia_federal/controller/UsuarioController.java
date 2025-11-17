package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.Usuario;
import com.PoliciaFederal.Policia_federal.model.Rol;
import com.PoliciaFederal.Policia_federal.model.Permiso;
import com.PoliciaFederal.Policia_federal.service.UsuarioService;
import com.PoliciaFederal.Policia_federal.service.RolService;
import com.PoliciaFederal.Policia_federal.util.ErrorHandlerUtil;
import com.PoliciaFederal.Policia_federal.util.PermisoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS) && 
            !PermisoUtil.tienePermiso(session, Permiso.ELIMINAR_DATOS)) {
            return "redirect:/";
        }
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("puedeEditar", PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS));
        model.addAttribute("puedeEliminar", PermisoUtil.tienePermiso(session, Permiso.ELIMINAR_DATOS));
        return "usuarios/listar";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS)) {
            return "redirect:/";
        }
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listarTodos());
        return "usuarios/crear";
    }
    
    @PostMapping("/crear")
    public String crear(@ModelAttribute Usuario usuario, @RequestParam("rol.id") Long rolId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS)) {
            return "redirect:/";
        }
        try {
            Rol rol = rolService.buscarPorId(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
            return "redirect:/usuarios/crear";
        }
        return "redirect:/usuarios";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS)) {
            return "redirect:/";
        }
        Usuario usuario = usuarioService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarTodos());
        return "usuarios/editar";
    }
    
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Usuario usuario, @RequestParam("rol.id") Long rolId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS)) {
            return "redirect:/";
        }
        try {
            Rol rol = rolService.buscarPorId(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
            usuarioService.actualizar(id, usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
            return "redirect:/usuarios/editar/" + id;
        }
        return "redirect:/usuarios";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.ELIMINAR_DATOS)) {
            return "redirect:/";
        }
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/usuarios";
    }
    
    // Crear Rol Personalizado
    @GetMapping("/crear-rol")
    public String mostrarFormularioCrearRol(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS)) {
            return "redirect:/";
        }
        List<Permiso> permisosDisponibles = new ArrayList<>(List.of(Permiso.values()));
        permisosDisponibles.remove(Permiso.CERRAR_SESION);
        permisosDisponibles.remove(Permiso.SALIR);
        model.addAttribute("permisosDisponibles", permisosDisponibles);
        return "usuarios/crear-rol";
    }
    
    @PostMapping("/crear-rol")
    public String crearRol(@RequestParam String nombreRol,
                          @RequestParam(required = false) List<String> permisosSeleccionados,
                          @RequestParam String nombreUsuario,
                          @RequestParam String password,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_USUARIOS)) {
            return "redirect:/";
        }
        try {
            // Crear el nuevo rol
            Rol nuevoRol = new Rol(nombreRol);
            Set<Permiso> permisos = new HashSet<>();
            
            if (permisosSeleccionados != null && !permisosSeleccionados.isEmpty()) {
                for (String permisoStr : permisosSeleccionados) {
                    try {
                        Permiso permiso = Permiso.valueOf(permisoStr);
                        permisos.add(permiso);
                    } catch (IllegalArgumentException e) {
                        // Ignorar permisos inválidos
                    }
                }
            }
            
            // Siempre agregar CERRAR_SESION y SALIR
            permisos.add(Permiso.CERRAR_SESION);
            permisos.add(Permiso.SALIR);
            
            nuevoRol.setPermisos(permisos);
            rolService.guardar(nuevoRol);
            
            // Crear el usuario con el nuevo rol
            Usuario nuevoUsuario = new Usuario(nombreUsuario, password, nuevoRol);
            usuarioService.guardar(nuevoUsuario);
            
            redirectAttributes.addFlashAttribute("mensaje", "Rol y usuario creados exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear rol: " + ErrorHandlerUtil.obtenerMensajeAmigable(e));
            return "redirect:/usuarios/crear-rol";
        }
        return "redirect:/usuarios";
    }
}

