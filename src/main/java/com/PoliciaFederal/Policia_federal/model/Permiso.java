package com.PoliciaFederal.Policia_federal.model;

/**
 * Enum que representa los distintos permisos que pueden ser asignados a un rol.
 * Cada permiso tiene una descripción legible que se utiliza en la interfaz del sistema.
 */
public enum Permiso {
    CONSULTAR_MIS_DATOS_VIGILANTE("Consultar mis datos."),
    CONSULTAR_DATOS_DE_OTRAS_ENTIDADES("Consultar datos."),
    EDITAR_DATOS("Editar datos."),
    CREAR_USUARIOS("Crear usuario."),
    CONTRATAR_VIGILANTE("Crear un contrato entre sucursal y vigilante."),
    CREAR_ENTIDAD("Crear nueva entidad."),
    CARGAR_DETENIDO("Cargar un detenido."),
    ELIMINAR_DATOS("Eliminar usuarios."),
    ABRIR_CASO("Abrir un caso"),
    CERRAR_SESION("Cerrar sesión."),
    SALIR("Salir del programa.");

    private final String descripcion;

    Permiso(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

