package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.EntidadBancaria;
import com.PoliciaFederal.Policia_federal.model.Permiso;
import com.PoliciaFederal.Policia_federal.service.EntidadBancariaService;
import com.PoliciaFederal.Policia_federal.util.ErrorHandlerUtil;
import com.PoliciaFederal.Policia_federal.util.PermisoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entidades")
public class EntidadBancariaController {
    
    @Autowired
    private EntidadBancariaService entidadBancariaService;
    
    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD) && 
            !PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        model.addAttribute("entidades", entidadBancariaService.listarTodas());
        model.addAttribute("puedeEditar", PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS));
        model.addAttribute("puedeCrear", PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD));
        return "entidades/listar";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        model.addAttribute("entidad", new EntidadBancaria());
        return "entidades/crear";
    }
    
    @PostMapping("/crear")
    public String crear(@ModelAttribute EntidadBancaria entidad, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CREAR_ENTIDAD)) {
            return "redirect:/";
        }
        try {
            entidadBancariaService.guardar(entidad);
            redirectAttributes.addFlashAttribute("mensaje", "Entidad bancaria creada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
            return "redirect:/entidades/crear";
        }
        return "redirect:/entidades";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        EntidadBancaria entidad = entidadBancariaService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Entidad bancaria no encontrada"));
        model.addAttribute("entidad", entidad);
        return "entidades/editar";
    }
    
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute EntidadBancaria entidad, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.EDITAR_DATOS)) {
            return "redirect:/";
        }
        try {
            entidadBancariaService.actualizar(id, entidad);
            redirectAttributes.addFlashAttribute("mensaje", "Entidad bancaria actualizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
            return "redirect:/entidades/editar/" + id;
        }
        return "redirect:/entidades";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!PermisoUtil.tienePermiso(session, Permiso.ELIMINAR_DATOS)) {
            return "redirect:/";
        }
        try {
            entidadBancariaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Entidad bancaria eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ErrorHandlerUtil.obtenerMensajeAmigable(e));
        }
        return "redirect:/entidades";
    }
}

