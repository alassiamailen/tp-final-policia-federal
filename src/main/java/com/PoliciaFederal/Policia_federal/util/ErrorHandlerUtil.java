package com.PoliciaFederal.Policia_federal.util;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Utilidad para convertir errores técnicos de base de datos en mensajes amigables
 * para el usuario final.
 */
public class ErrorHandlerUtil {
    
    /**
     * Convierte una excepción en un mensaje amigable para el usuario.
     * 
     * @param e La excepción a convertir
     * @return Un mensaje amigable en español
     */
    public static String obtenerMensajeAmigable(Exception e) {
        // Si es una excepción de integridad de datos (restricciones de base de datos)
        if (e instanceof DataIntegrityViolationException) {
            return obtenerMensajeIntegridadDatos((DataIntegrityViolationException) e);
        }
        
        // Si es una RuntimeException con un mensaje personalizado
        if (e instanceof RuntimeException) {
            String mensaje = e.getMessage();
            if (mensaje != null && !mensaje.isEmpty()) {
                // Si el mensaje ya es amigable, devolverlo
                if (esMensajeAmigable(mensaje)) {
                    return mensaje;
                }
            }
        }
        
        // Mensaje genérico para errores desconocidos
        return "Ocurrió un error al procesar la solicitud. Por favor, intente nuevamente.";
    }
    
    /**
     * Convierte errores de integridad de datos en mensajes amigables.
     */
    private static String obtenerMensajeIntegridadDatos(DataIntegrityViolationException e) {
        String mensajeError = e.getMessage();
        if (mensajeError == null) {
            return "Error de validación: Los datos ingresados no son válidos.";
        }
        
        // Detectar errores de restricción de unicidad
        if (mensajeError.contains("llave duplicada") || mensajeError.contains("duplicate key") || 
            mensajeError.contains("viola restricción de unicidad") || mensajeError.contains("unique constraint")) {
            
            // Detectar qué campo está duplicado
            if (mensajeError.contains("numero_identificacion") || mensajeError.contains("numeroIdentificacion")) {
                // Extraer el número duplicado del mensaje
                String numero = extraerValorDuplicado(mensajeError);
                if (numero != null) {
                    return "El número de identificación " + numero + " ya existe. Por favor, elija otro número.";
                }
                return "Ese número de identificación ya existe. Por favor, elija otro número.";
            }
            
            if (mensajeError.contains("nombre_usuario") || mensajeError.contains("nombreUsuario")) {
                String usuario = extraerValorDuplicado(mensajeError);
                if (usuario != null) {
                    return "El nombre de usuario '" + usuario + "' ya existe. Por favor, elija otro nombre de usuario.";
                }
                return "Ese nombre de usuario ya existe. Por favor, elija otro nombre de usuario.";
            }
            
            if (mensajeError.contains("codigo")) {
                String codigo = extraerValorDuplicado(mensajeError);
                if (codigo != null) {
                    return "El código '" + codigo + "' ya existe. Por favor, elija otro código.";
                }
                return "Ese código ya existe. Por favor, elija otro código.";
            }
            
            if (mensajeError.contains("clave_interna_juzgado") || mensajeError.contains("claveInternaJuzgado")) {
                String clave = extraerValorDuplicado(mensajeError);
                if (clave != null) {
                    return "La clave interna del juzgado '" + clave + "' ya existe. Por favor, elija otra clave.";
                }
                return "Esa clave interna del juzgado ya existe. Por favor, elija otra clave.";
            }
            
            if (mensajeError.contains("nombre") && mensajeError.contains("rol")) {
                return "Ese nombre de rol ya existe. Por favor, elija otro nombre.";
            }
            
            // Mensaje genérico para restricciones de unicidad
            return "Los datos ingresados ya existen en el sistema. Por favor, verifique la información e intente nuevamente.";
        }
        
        // Detectar errores de restricción de clave foránea
        if (mensajeError.contains("violates foreign key") || mensajeError.contains("viola restricción de clave foránea")) {
            return "No se puede realizar esta operación porque hay datos relacionados que dependen de este registro.";
        }
        
        // Detectar errores de NOT NULL
        if (mensajeError.contains("violates not-null") || mensajeError.contains("viola restricción NOT NULL")) {
            return "Faltan datos obligatorios. Por favor, complete todos los campos requeridos.";
        }
        
        // Mensaje genérico para otros errores de integridad
        return "Error de validación: Los datos ingresados no son válidos. Por favor, verifique la información e intente nuevamente.";
    }
    
    /**
     * Intenta extraer el valor duplicado del mensaje de error.
     * Ejemplo: "Ya existe la llave (numero_identificacion)=(1)" -> "1"
     */
    private static String extraerValorDuplicado(String mensaje) {
        try {
            // Buscar patrones como (campo)=(valor) o (campo)=('valor')
            int inicio = mensaje.indexOf(")=(");
            if (inicio > 0) {
                int fin = mensaje.indexOf(")", inicio + 3);
                if (fin > inicio) {
                    String valor = mensaje.substring(inicio + 3, fin);
                    // Remover comillas si las hay
                    valor = valor.replace("'", "").replace("\"", "");
                    return valor;
                }
            }
        } catch (Exception e) {
            // Si no se puede extraer, retornar null
        }
        return null;
    }
    
    /**
     * Verifica si un mensaje ya es amigable (no contiene términos técnicos).
     */
    private static boolean esMensajeAmigable(String mensaje) {
        String mensajeLower = mensaje.toLowerCase();
        // Si contiene términos técnicos, no es amigable
        return !mensajeLower.contains("sql") && 
               !mensajeLower.contains("constraint") && 
               !mensajeLower.contains("violation") &&
               !mensajeLower.contains("could not execute") &&
               !mensajeLower.contains("llave duplicada") &&
               !mensajeLower.contains("uk") &&
               !mensajeLower.contains("detail:");
    }
}

