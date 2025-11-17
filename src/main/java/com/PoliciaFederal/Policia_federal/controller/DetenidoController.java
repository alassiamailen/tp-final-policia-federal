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

import java.time.LocalDate;

@Controller
@RequestMapping("/detenidos")
public class DetenidoController {
    
    @Autowired
    private AsaltoService asaltoService;
    @Autowired
    private AsaltanteService asaltanteService;
    @Autowired
    private SucursalService sucursalService;
    
    @GetMapping("/cargar")
    public String mostrarFormularioCargar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CARGAR_DETENIDO)) {
            return "redirect:/";
        }
        model.addAttribute("asaltantes", asaltanteService.listarTodos());
        model.addAttribute("sucursales", sucursalService.listarTodas());
        return "detenidos/cargar";
    }
    
    @PostMapping("/cargar")
    public String cargarDetenido(@RequestParam("asaltante.id") Long asaltanteId,
                                 @RequestParam("sucursal.id") Long sucursalId,
                                 @RequestParam LocalDate fecha,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CARGAR_DETENIDO)) {
            return "redirect:/";
        }
        try {
            // Validar que la fecha no sea futura
            LocalDate hoy = LocalDate.now();
            if (fecha.isAfter(hoy)) {
                redirectAttributes.addFlashAttribute("error", "La fecha del asalto no puede ser futura. Debe ser la fecha actual o una fecha pasada.");
                return "redirect:/detenidos/cargar";
            }
            
            Asaltante asaltante = asaltanteService.buscarPorId(asaltanteId)
                .orElseThrow(() -> new RuntimeException("Asaltante no encontrado"));
            Sucursal sucursal = sucursalService.buscarPorId(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            
            BandaCriminal banda = asaltante.getBandaCriminal();
            
            Asalto asalto = new Asalto(fecha, asaltante, sucursal, banda);
            asalto.setCondenado(false);
            asaltoService.guardar(asalto);
            
            redirectAttributes.addFlashAttribute("mensaje", "Asalto registrado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/detenidos/cargar";
    }
}

