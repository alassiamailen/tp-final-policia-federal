package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.Usuario;
import com.PoliciaFederal.Policia_federal.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String nombreUsuario, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorNombreUsuario(nombreUsuario);
        
        if (usuarioOpt.isPresent() && usuarioOpt.get().autenticar(nombreUsuario, password)) {
            session.setAttribute("usuario", usuarioOpt.get());
            session.setAttribute("usuarioId", usuarioOpt.get().getId());
            session.setAttribute("usuarioNombre", usuarioOpt.get().getNombreUsuario());
            session.setAttribute("usuarioRol", usuarioOpt.get().getRol().getNombre());
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

