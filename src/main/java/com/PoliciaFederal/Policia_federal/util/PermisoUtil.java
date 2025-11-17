package com.PoliciaFederal.Policia_federal.util;

import com.PoliciaFederal.Policia_federal.model.Permiso;
import com.PoliciaFederal.Policia_federal.model.Usuario;
import jakarta.servlet.http.HttpSession;

public class PermisoUtil {
    
    public static boolean tienePermiso(HttpSession session, Permiso permiso) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return false;
        }
        return usuario.puedeRealizar(permiso);
    }
    
    public static Usuario getUsuarioActual(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }
}

