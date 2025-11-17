package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.Permiso;
import com.PoliciaFederal.Policia_federal.model.Usuario;
import com.PoliciaFederal.Policia_federal.util.PermisoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Obtener permisos del usuario
        List<Permiso> permisos = new ArrayList<>(usuario.getRol().getPermisos());
        model.addAttribute("permisos", permisos);
        model.addAttribute("usuario", usuario);
        
        return "index";
    }
}

