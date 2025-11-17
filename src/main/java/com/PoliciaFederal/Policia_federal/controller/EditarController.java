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
@RequestMapping("/editar")
public class EditarController {
    
    @Autowired
    private JuezService juezService;
    @Autowired
    private VigilanteService vigilanteService;
    @Autowired
    private EntidadBancariaService entidadBancariaService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private AsaltanteService asaltanteService;
    @Autowired
    private BandaCriminalService bandaCriminalService;
    
    @GetMapping
    public String menuEditar(HttpSession session) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        return "editar/menu";
    }
    
    // Editar Juez
    @GetMapping("/juez")
    public String listarJuecesParaEditar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        model.addAttribute("jueces", juezService.listarTodos());
        return "editar/jueces";
    }
    
    @GetMapping("/juez/{id}")
    public String mostrarFormularioEditarJuez(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        Juez juez = juezService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Juez no encontrado"));
        model.addAttribute("juez", juez);
        return "editar/juez-form";
    }
    
    @PostMapping("/juez/{id}")
    public String actualizarJuez(@PathVariable Long id, @ModelAttribute Juez juez, 
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        try {
            juezService.actualizar(id, juez);
            redirectAttributes.addFlashAttribute("mensaje", "Juez actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/editar/juez";
    }
    
    // Editar Vigilante
    @GetMapping("/vigilante")
    public String listarVigilantesParaEditar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        model.addAttribute("vigilantes", vigilanteService.listarTodos());
        return "editar/vigilantes";
    }
    
    @GetMapping("/vigilante/{id}")
    public String mostrarFormularioEditarVigilante(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        Vigilante vigilante = vigilanteService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));
        model.addAttribute("vigilante", vigilante);
        return "editar/vigilante-form";
    }
    
    @PostMapping("/vigilante/{id}")
    public String actualizarVigilante(@PathVariable Long id, @ModelAttribute Vigilante vigilante,
                                      HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        try {
            vigilanteService.actualizar(id, vigilante);
            redirectAttributes.addFlashAttribute("mensaje", "Vigilante actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/editar/vigilante";
    }
    
    // Editar Entidad Bancaria
    @GetMapping("/entidad-bancaria")
    public String listarEntidadesParaEditar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        model.addAttribute("entidades", entidadBancariaService.listarTodas());
        return "editar/entidades";
    }
    
    @GetMapping("/entidad-bancaria/{id}")
    public String mostrarFormularioEditarEntidad(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        EntidadBancaria entidad = entidadBancariaService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Entidad bancaria no encontrada"));
        model.addAttribute("entidad", entidad);
        return "editar/entidad-form";
    }
    
    @PostMapping("/entidad-bancaria/{id}")
    public String actualizarEntidad(@PathVariable Long id, @ModelAttribute EntidadBancaria entidad,
                                    HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        try {
            entidadBancariaService.actualizar(id, entidad);
            redirectAttributes.addFlashAttribute("mensaje", "Entidad bancaria actualizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/editar/entidad-bancaria";
    }
    
    // Editar Sucursal
    @GetMapping("/sucursal")
    public String listarSucursalesParaEditar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        model.addAttribute("sucursales", sucursalService.listarTodas());
        return "editar/sucursales";
    }
    
    @GetMapping("/sucursal/{id}")
    public String mostrarFormularioEditarSucursal(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        Sucursal sucursal = sucursalService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("entidades", entidadBancariaService.listarTodas());
        return "editar/sucursal-form";
    }
    
    @PostMapping("/sucursal/{id}")
    public String actualizarSucursal(@PathVariable Long id, @ModelAttribute Sucursal sucursal,
                                     @RequestParam("entidadBancaria.id") Long entidadBancariaId,
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        try {
            EntidadBancaria entidad = entidadBancariaService.buscarPorId(entidadBancariaId)
                .orElseThrow(() -> new RuntimeException("Entidad bancaria no encontrada"));
            sucursal.setEntidadBancaria(entidad);
            sucursalService.actualizar(id, sucursal);
            redirectAttributes.addFlashAttribute("mensaje", "Sucursal actualizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/editar/sucursal";
    }
    
    // Editar Asaltante
    @GetMapping("/asaltante/{id}")
    public String mostrarFormularioEditarAsaltante(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        Asaltante asaltante = asaltanteService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Asaltante no encontrado"));
        model.addAttribute("asaltante", asaltante);
        model.addAttribute("bandas", bandaCriminalService.listarTodas());
        return "editar/asaltante-form";
    }
    
    @PostMapping("/asaltante/{id}")
    public String actualizarAsaltante(@PathVariable Long id, @ModelAttribute Asaltante asaltante,
                                     @RequestParam(required = false) Long bandaCriminalId,
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        try {
            if (bandaCriminalId != null) {
                BandaCriminal banda = bandaCriminalService.buscarPorId(bandaCriminalId)
                    .orElse(null);
                asaltante.setBandaCriminal(banda);
            } else {
                asaltante.setBandaCriminal(null);
            }
            asaltanteService.actualizar(id, asaltante);
            redirectAttributes.addFlashAttribute("mensaje", "Asaltante actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/consultas/asaltantes";
    }
}

