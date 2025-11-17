package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.*;
import com.PoliciaFederal.Policia_federal.service.*;
import com.PoliciaFederal.Policia_federal.util.ErrorHandlerUtil;
import com.PoliciaFederal.Policia_federal.util.PermisoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/crear-entidad")
public class CrearEntidadController {
    
    @Autowired
    private JuezService juezService;
    @Autowired
    private AsaltanteService asaltanteService;
    @Autowired
    private BandaCriminalService bandaCriminalService;
    @Autowired
    private VigilanteService vigilanteService;
    @Autowired
    private EntidadBancariaService entidadBancariaService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public String menuCrearEntidad(HttpSession session) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        return "crear-entidad/menu";
    }
    
    // Crear Juez
    @GetMapping("/juez")
    public String mostrarFormularioJuez(@RequestParam(required = false) String returnTo,
                                       HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("juez", new Juez());
        if (returnTo != null) {
            model.addAttribute("returnTo", returnTo);
        }
        return "crear-entidad/juez";
    }
    
    @PostMapping("/juez")
    public String crearJuez(@ModelAttribute Juez juez, 
                           @RequestParam(required = false) String returnTo,
                           HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            juezService.guardar(juez);
            redirectAttributes.addFlashAttribute("mensaje", "Juez creado exitosamente");
            if (returnTo != null) {
                return "redirect:" + returnTo;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/crear-entidad";
    }
    
    // Crear Asaltante
    @GetMapping("/asaltante")
    public String mostrarFormularioAsaltante(@RequestParam(required = false) String returnTo,
                                             HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("asaltante", new Asaltante());
        model.addAttribute("bandas", bandaCriminalService.listarTodas());
        if (returnTo != null) {
            model.addAttribute("returnTo", returnTo);
        }
        return "crear-entidad/asaltante";
    }
    
    @PostMapping("/asaltante")
    public String crearAsaltante(@ModelAttribute Asaltante asaltante, 
                                 @RequestParam(required = false) Long bandaCriminalId,
                                 @RequestParam(required = false) String returnTo,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            if (bandaCriminalId != null) {
                BandaCriminal banda = bandaCriminalService.buscarPorId(bandaCriminalId)
                    .orElse(null);
                asaltante.setBandaCriminal(banda);
            }
            asaltanteService.guardar(asaltante);
            redirectAttributes.addFlashAttribute("mensaje", "Asaltante creado exitosamente");
            if (returnTo != null) {
                return "redirect:" + returnTo;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/crear-entidad";
    }
    
    // Crear Vigilante
    @GetMapping("/vigilante")
    public String mostrarFormularioVigilante(@RequestParam(required = false) String returnTo,
                                             HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("vigilante", new Vigilante());
        // Obtener usuarios con rol Vigilante que no tienen vigilante asignado
        model.addAttribute("usuariosDisponibles", usuarioService.buscarUsuariosVigilanteDisponibles());
        if (returnTo != null) {
            model.addAttribute("returnTo", returnTo);
        }
        return "crear-entidad/vigilante";
    }
    
    @PostMapping("/vigilante")
    public String crearVigilante(@ModelAttribute Vigilante vigilante,
                                 @RequestParam(required = false) Long usuarioExistenteId,
                                 @RequestParam(required = false) String nombreUsuario,
                                 @RequestParam(required = false) String password,
                                 @RequestParam(required = false) String returnTo,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            // Guardar el vigilante primero
            vigilanteService.guardar(vigilante);
            
            // Buscar el rol Vigilante (debe existir por el DataInitializer)
            Rol rolVigilante = rolService.buscarPorNombre("Vigilante")
                .orElseThrow(() -> new RuntimeException("El rol Vigilante no existe en el sistema. Por favor, contacte al administrador."));
            
            if (usuarioExistenteId != null) {
                // Vincular a un usuario existente
                Usuario usuarioExistente = usuarioService.buscarPorId(usuarioExistenteId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
                // Verificar que el usuario no tenga ya un vigilante asignado
                if (usuarioExistente.getVigilante() != null) {
                    redirectAttributes.addFlashAttribute("error", "El usuario seleccionado ya tiene un vigilante asignado");
                    return "redirect:/crear-entidad/vigilante" + (returnTo != null ? "?returnTo=" + returnTo : "");
                }
                
                // Verificar que el usuario tenga el rol Vigilante
                if (!usuarioExistente.getRol().getNombre().equals("Vigilante")) {
                    redirectAttributes.addFlashAttribute("error", "El usuario seleccionado no tiene el rol Vigilante");
                    return "redirect:/crear-entidad/vigilante" + (returnTo != null ? "?returnTo=" + returnTo : "");
                }
                
                // Vincular el vigilante al usuario existente
                usuarioExistente.setVigilante(vigilante);
                // Usar actualizar para preservar otros datos del usuario
                usuarioService.actualizar(usuarioExistente.getId(), usuarioExistente);
                
                redirectAttributes.addFlashAttribute("mensaje", "Vigilante creado y vinculado al usuario existente exitosamente");
            } else {
                // Crear un nuevo usuario
                if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Debe proporcionar un nombre de usuario o seleccionar un usuario existente");
                    return "redirect:/crear-entidad/vigilante" + (returnTo != null ? "?returnTo=" + returnTo : "");
                }
                
                if (password == null || password.trim().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Debe proporcionar una contraseña");
                    return "redirect:/crear-entidad/vigilante" + (returnTo != null ? "?returnTo=" + returnTo : "");
                }
                
                // Verificar si el nombre de usuario ya existe
                if (usuarioService.buscarPorNombreUsuario(nombreUsuario).isPresent()) {
                    redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya existe");
                    return "redirect:/crear-entidad/vigilante" + (returnTo != null ? "?returnTo=" + returnTo : "");
                }
                
                // Crear el usuario asociado
                Usuario usuario = new Usuario(nombreUsuario, password, rolVigilante);
                usuario.setVigilante(vigilante);
                usuarioService.guardar(usuario);
                
                redirectAttributes.addFlashAttribute("mensaje", "Vigilante y usuario creados exitosamente");
            }
            
            if (returnTo != null) {
                return "redirect:" + returnTo;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/crear-entidad";
    }
    
    // Crear Entidad Bancaria (ya existe, pero lo mantenemos)
    @GetMapping("/entidad-bancaria")
    public String mostrarFormularioEntidadBancaria(@RequestParam(required = false) String returnTo,
                                                   HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("entidad", new EntidadBancaria());
        if (returnTo != null) {
            model.addAttribute("returnTo", returnTo);
        }
        return "crear-entidad/entidad-bancaria";
    }
    
    @PostMapping("/entidad-bancaria")
    public String crearEntidadBancaria(@ModelAttribute EntidadBancaria entidad, 
                                      @RequestParam(required = false) String returnTo,
                                      HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            entidadBancariaService.guardar(entidad);
            redirectAttributes.addFlashAttribute("mensaje", "Entidad bancaria creada exitosamente");
            if (returnTo != null) {
                return "redirect:" + returnTo;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/crear-entidad";
    }
    
    // Crear Sucursal
    @GetMapping("/sucursal")
    public String mostrarFormularioSucursal(@RequestParam(required = false) String returnTo,
                                            HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("sucursal", new Sucursal());
        model.addAttribute("entidades", entidadBancariaService.listarTodas());
        if (returnTo != null) {
            model.addAttribute("returnTo", returnTo);
        }
        return "crear-entidad/sucursal";
    }
    
    @PostMapping("/sucursal")
    public String crearSucursal(@ModelAttribute Sucursal sucursal,
                                @RequestParam("entidadBancaria.id") Long entidadBancariaId,
                                @RequestParam(required = false) String returnTo,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            EntidadBancaria entidad = entidadBancariaService.buscarPorId(entidadBancariaId)
                .orElseThrow(() -> new RuntimeException("Entidad bancaria no encontrada"));
            sucursal.setEntidadBancaria(entidad);
            sucursalService.guardar(sucursal);
            redirectAttributes.addFlashAttribute("mensaje", "Sucursal creada exitosamente");
            if (returnTo != null) {
                return "redirect:" + returnTo;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/crear-entidad";
    }
    
    
    @GetMapping("/banda-criminal")
    public String mostrarFormularioBandaCriminal(@RequestParam(required = false) String returnTo, 
                                                 HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("banda", new BandaCriminal());
        if (returnTo != null) {
            model.addAttribute("returnTo", returnTo);
        }
        return "crear-entidad/banda-criminal";
    }
    
    @PostMapping("/banda-criminal")
    public String crearBandaCriminal(@ModelAttribute BandaCriminal banda, 
                                    @RequestParam(required = false) String returnTo,
                                    HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            bandaCriminalService.guardar(banda);
            redirectAttributes.addFlashAttribute("mensaje", "Banda criminal creada exitosamente");
            if (returnTo != null) {
                return "redirect:" + returnTo;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
            return "redirect:/crear-entidad/banda-criminal" + (returnTo != null ? "?returnTo=" + returnTo : "");
        }
        return "redirect:/crear-entidad";
    }
}

