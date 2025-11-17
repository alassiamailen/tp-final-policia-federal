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
@RequestMapping("/contratos")
public class ContratoController {
    
    @Autowired
    private ContratoSucVigService contratoService;
    @Autowired
    private EntidadBancariaService entidadBancariaService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private VigilanteService vigilanteService;
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONTRATAR_VIGILANTE)) {
            return "redirect:/";
        }
        model.addAttribute("contrato", new ContratoSucVig());
        model.addAttribute("entidades", entidadBancariaService.listarTodas());
        model.addAttribute("sucursales", sucursalService.listarTodas());
        model.addAttribute("vigilantes", vigilanteService.listarTodos());
        return "contratos/crear";
    }
    
    @PostMapping("/crear")
    public String crearContrato(@RequestParam("sucursal.id") Long sucursalId,
                                @RequestParam("vigilante.id") Long vigilanteId,
                                @RequestParam LocalDate fechaInicio,
                                @RequestParam LocalDate fechaFin,
                                @RequestParam(required = false) Boolean portarArma,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONTRATAR_VIGILANTE)) {
            return "redirect:/";
        }
        try {
            // Si portarArma es null (checkbox no marcado), establecer como false
            if (portarArma == null) {
                portarArma = false;
            }
            
            // Validar fechas
            LocalDate hoy = LocalDate.now();
            if (fechaInicio.isBefore(hoy)) {
                redirectAttributes.addFlashAttribute("error", "La fecha de inicio no puede ser anterior al día de hoy.");
                return "redirect:/contratos/crear";
            }
            
            if (!fechaFin.isAfter(fechaInicio)) {
                redirectAttributes.addFlashAttribute("error", "La fecha de fin debe ser posterior a la fecha de inicio.");
                return "redirect:/contratos/crear";
            }
            
            Sucursal sucursal = sucursalService.buscarPorId(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Vigilante vigilante = vigilanteService.buscarPorId(vigilanteId)
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));
            
            ContratoSucVig contrato = new ContratoSucVig(fechaInicio, fechaFin, sucursal, vigilante, portarArma);
            contratoService.guardar(contrato);
            
            redirectAttributes.addFlashAttribute("mensaje", "Contrato creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/contratos/crear";
    }
}

