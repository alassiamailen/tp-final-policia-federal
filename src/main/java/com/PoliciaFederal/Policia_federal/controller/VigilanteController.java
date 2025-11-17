package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.Permiso;
import com.PoliciaFederal.Policia_federal.model.Usuario;
import com.PoliciaFederal.Policia_federal.model.Vigilante;
import com.PoliciaFederal.Policia_federal.service.UsuarioService;
import com.PoliciaFederal.Policia_federal.service.VigilanteService;
import com.PoliciaFederal.Policia_federal.util.PermisoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vigilante")
public class VigilanteController {
    
    @Autowired
    private VigilanteService vigilanteService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/mis-datos")
    public String verMisDatos(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_MIS_DATOS_VIGILANTE)) {
            return "redirect:/";
        }
        
        try {
            Usuario usuarioSesion = PermisoUtil.getUsuarioActual(session);
            if (usuarioSesion == null) {
                return "redirect:/login";
            }
            
            // Recargar el usuario desde la base de datos para asegurar que las relaciones estén cargadas
            Usuario usuario = usuarioService.buscarPorId(usuarioSesion.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Vigilante vigilante = usuario.getVigilante();
            
            // Si el usuario tiene un vigilante_id pero la relación no está cargada,
            // intentar cargarlo desde el servicio
            if (vigilante == null) {
                vigilante = vigilanteService.buscarPorUsuario(usuario.getId()).orElse(null);
            }
            
            if (vigilante == null) {
                model.addAttribute("error", "Este usuario no tiene un vigilante asociado en el sistema. Por favor, contacte al administrador.");
                return "vigilante/mis-datos";
            }
            
            model.addAttribute("vigilante", vigilante);
            return "vigilante/mis-datos";
        } catch (Exception e) {
            e.printStackTrace(); // Para debug
            model.addAttribute("error", "Error al cargar los datos: " + e.getMessage());
            return "vigilante/mis-datos";
        }
    }
}

