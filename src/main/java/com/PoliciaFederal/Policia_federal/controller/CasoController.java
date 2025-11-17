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
@RequestMapping("/casos")
public class CasoController {
    
    @Autowired
    private CasoService casoService;
    @Autowired
    private AsaltoService asaltoService;
    @Autowired
    private JuezService juezService;
    @Autowired
    private CondenaService condenaService;
    
    @GetMapping("/abrir")
    public String mostrarFormularioAbrir(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.ABRIR_CASO)) {
            return "redirect:/";
        }
        model.addAttribute("asaltos", asaltoService.listarTodos());
        model.addAttribute("jueces", juezService.listarTodos());
        return "casos/abrir";
    }
    
    @PostMapping("/abrir")
    public String abrirCaso(@RequestParam("asalto.id") Long asaltoId,
                           @RequestParam("juez.id") Long juezId,
                           @RequestParam(required = false) Boolean fueCondenado,
                           @RequestParam(required = false) Integer tiempoCondena,
                           HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.ABRIR_CASO)) {
            return "redirect:/";
        }
        try {
            if (fueCondenado == null) {
                fueCondenado = false;
            }
            
            // Si no fue condenado, asegurar que tiempoCondena sea null
            if (!fueCondenado) {
                tiempoCondena = null;
            }
            
            if (fueCondenado && (tiempoCondena == null || tiempoCondena <= 0 || tiempoCondena > 50)) {
                redirectAttributes.addFlashAttribute("error", "Si fue condenado, la pena permitida es de 1 a 50 años");
                return "redirect:/casos/abrir";
            }
            
            Asalto asalto = asaltoService.buscarPorId(asaltoId)
                .orElseThrow(() -> new RuntimeException("Asalto no encontrado"));
            Juez juez = juezService.buscarPorId(juezId)
                .orElseThrow(() -> new RuntimeException("Juez no encontrado"));
            
            Asaltante asaltante = asalto.getAsaltante();
            if (asaltante == null) {
                redirectAttributes.addFlashAttribute("error", "El asalto no tiene asaltante asociado");
                return "redirect:/casos/abrir";
            }
            
            Condena condena = new Condena(asaltante, asalto, fueCondenado, tiempoCondena);
            condenaService.guardar(condena);
            
            Caso caso = new Caso(juez, condena);
            casoService.guardar(caso);
            
            asalto.setCondenado(fueCondenado);
            asaltoService.guardar(asalto);
            
            redirectAttributes.addFlashAttribute("mensaje", "Caso abierto exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/casos/abrir";
    }
}

