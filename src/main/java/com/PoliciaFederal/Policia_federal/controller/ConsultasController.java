package com.PoliciaFederal.Policia_federal.controller;

import com.PoliciaFederal.Policia_federal.model.*;
import com.PoliciaFederal.Policia_federal.service.*;
import com.PoliciaFederal.Policia_federal.util.PermisoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultas")
public class ConsultasController {
    
    @Autowired
    private JuezService juezService;
    @Autowired
    private CondenaService condenaService;
    @Autowired
    private AsaltanteService asaltanteService;
    @Autowired
    private AsaltoService asaltoService;
    @Autowired
    private CasoService casoService;
    @Autowired
    private VigilanteService vigilanteService;
    @Autowired
    private ContratoSucVigService contratoService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private EntidadBancariaService entidadBancariaService;
    @Autowired
    private BandaCriminalService bandaCriminalService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public String menuConsultas(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeCrear", usuario.puedeRealizar(Permiso.CREAR_ENTIDAD));
        return "consultas/menu";
    }
    
    // Información Judicial
    @GetMapping("/jueces")
    public String consultarJueces(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        model.addAttribute("jueces", juezService.listarTodos());
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        return "consultas/jueces";
    }
    
    @GetMapping("/jueces/{id}")
    public String verJuez(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        Juez juez = juezService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Juez no encontrado"));
        model.addAttribute("juez", juez);
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeAbrirCaso", usuario.puedeRealizar(Permiso.ABRIR_CASO));
        model.addAttribute("casos", casoService.listarTodos());
        return "consultas/juez-detalle";
    }
    
    @GetMapping("/condenas")
    public String consultarCondenas(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("condenas", condenaService.listarTodas());
        return "consultas/condenas";
    }
    
    @GetMapping("/asaltantes")
    public String consultarAsaltantes(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        model.addAttribute("asaltantes", asaltanteService.listarTodos());
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeCrear", usuario.puedeRealizar(Permiso.CREAR_ENTIDAD));
        return "consultas/asaltantes";
    }
    
    @GetMapping("/asaltantes/{id}")
    public String verAsaltante(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        Asaltante asaltante = asaltanteService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Asaltante no encontrado"));
        model.addAttribute("asaltante", asaltante);
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeCargarDetenido", usuario.puedeRealizar(Permiso.CARGAR_DETENIDO));
        model.addAttribute("puedeAbrirCaso", usuario.puedeRealizar(Permiso.ABRIR_CASO));
        model.addAttribute("bandas", bandaCriminalService.listarTodas());
        model.addAttribute("sucursales", sucursalService.listarTodas());
        model.addAttribute("asaltos", asaltoService.listarTodos());
        model.addAttribute("jueces", juezService.listarTodos());
        return "consultas/asaltante-detalle";
    }
    
    @GetMapping("/asaltos")
    public String consultarAsaltos(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("asaltos", asaltoService.listarTodos());
        return "consultas/asaltos";
    }
    
    @GetMapping("/asaltos/{id}")
    public String verAsalto(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Asalto asalto = asaltoService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Asalto no encontrado"));
        model.addAttribute("asalto", asalto);
        return "consultas/asalto-detalle";
    }
    
    @GetMapping("/casos")
    public String consultarCasos(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("casos", casoService.listarTodos());
        return "consultas/casos";
    }
    
    @GetMapping("/casos/{id}")
    public String verCaso(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Caso caso = casoService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Caso no encontrado"));
        model.addAttribute("caso", caso);
        return "consultas/caso-detalle";
    }
    
    // Seguridad Bancaria
    @GetMapping("/vigilantes")
    public String consultarVigilantes(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        model.addAttribute("vigilantes", vigilanteService.listarTodos());
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeCrear", usuario.puedeRealizar(Permiso.CREAR_ENTIDAD));
        return "consultas/vigilantes";
    }
    
    @GetMapping("/vigilantes/{id}")
    public String verVigilante(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        Vigilante vigilante = vigilanteService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));
        model.addAttribute("vigilante", vigilante);
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeContratar", usuario.puedeRealizar(Permiso.CONTRATAR_VIGILANTE));
        model.addAttribute("sucursales", sucursalService.listarTodas());
        return "consultas/vigilante-detalle";
    }
    
    @GetMapping("/contratos")
    public String consultarContratos(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("contratos", contratoService.listarTodos());
        return "consultas/contratos";
    }
    
    @GetMapping("/contratos/{id}")
    public String verContrato(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        ContratoSucVig contrato = contratoService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        model.addAttribute("contrato", contrato);
        return "consultas/contrato-detalle";
    }
    
    // Entidades Financieras
    @GetMapping("/entidades-bancarias")
    public String consultarEntidadesBancarias(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("entidades", entidadBancariaService.listarTodas());
        return "consultas/entidades-bancarias";
    }
    
    @GetMapping("/entidades-bancarias/{id}")
    public String verEntidadBancaria(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        EntidadBancaria entidad = entidadBancariaService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Entidad bancaria no encontrada"));
        model.addAttribute("entidad", entidad);
        return "consultas/entidad-bancaria-detalle";
    }
    
    @GetMapping("/sucursales")
    public String consultarSucursales(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        model.addAttribute("sucursales", sucursalService.listarTodas());
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeCrear", usuario.puedeRealizar(Permiso.CREAR_ENTIDAD));
        return "consultas/sucursales";
    }
    
    @GetMapping("/sucursales/{id}")
    public String verSucursal(@PathVariable Long id, HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        Usuario usuario = PermisoUtil.getUsuarioActual(session);
        Sucursal sucursal = sucursalService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("puedeEditar", usuario.puedeRealizar(Permiso.EDITAR_DATOS));
        model.addAttribute("puedeContratar", usuario.puedeRealizar(Permiso.CONTRATAR_VIGILANTE));
        model.addAttribute("puedeCargarDetenido", usuario.puedeRealizar(Permiso.CARGAR_DETENIDO));
        return "consultas/sucursal-detalle";
    }
    
    // Administración del Sistema
    @GetMapping("/usuarios-sistema")
    public String consultarUsuariosSistema(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "consultas/usuarios-sistema";
    }
    
    @GetMapping("/roles-sistema")
    public String consultarRolesSistema(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("roles", rolService.listarTodos());
        return "consultas/roles-sistema";
    }
    
    @GetMapping("/permisos")
    public String consultarPermisos(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONSULTAR_DATOS_DE_OTRAS_ENTIDADES)) {
            return "redirect:/";
        }
        model.addAttribute("permisos", Permiso.values());
        return "consultas/permisos";
    }
}

