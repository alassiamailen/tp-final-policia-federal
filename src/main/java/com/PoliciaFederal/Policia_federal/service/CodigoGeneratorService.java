package com.PoliciaFederal.Policia_federal.service;

import com.PoliciaFederal.Policia_federal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para generar códigos autoincrementables para las entidades.
 * Los códigos se generan basándose en el último código existente en la base de datos.
 */
@Service
@Transactional
public class CodigoGeneratorService {
    
    @Autowired
    private VigilanteRepository vigilanteRepository;
    
    @Autowired
    private AsaltanteRepository asaltanteRepository;
    
    @Autowired
    private JuezRepository juezRepository;
    
    @Autowired
    private EntidadBancariaRepository entidadBancariaRepository;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    /**
     * Genera el siguiente código para un vigilante (formato: VIG-001, VIG-002, etc.)
     */
    public String generarCodigoVigilante() {
        return generarSiguienteCodigo("VIG", vigilanteRepository.findAll().stream()
            .map(v -> v.getCodigo())
            .filter(c -> c != null && c.matches("VIG-\\d+"))
            .mapToInt(c -> {
                try {
                    return Integer.parseInt(c.substring(4));
                } catch (Exception e) {
                    return 0;
                }
            })
            .max()
            .orElse(0));
    }
    
    /**
     * Genera el siguiente código para un asaltante (formato: ASL-001, ASL-002, etc.)
     */
    public String generarCodigoAsaltante() {
        return generarSiguienteCodigo("ASL", asaltanteRepository.findAll().stream()
            .map(a -> a.getCodigo())
            .filter(c -> c != null && c.matches("ASL-\\d+"))
            .mapToInt(c -> {
                try {
                    return Integer.parseInt(c.substring(4));
                } catch (Exception e) {
                    return 0;
                }
            })
            .max()
            .orElse(0));
    }
    
    /**
     * Genera la siguiente clave interna para un juez (formato: JZ-001, JZ-002, etc.)
     */
    public String generarClaveInternaJuzgado() {
        return generarSiguienteCodigo("JZ", juezRepository.findAll().stream()
            .map(j -> j.getClaveInternaJuzgado())
            .filter(c -> c != null && c.matches("JZ-\\d+"))
            .mapToInt(c -> {
                try {
                    return Integer.parseInt(c.substring(3));
                } catch (Exception e) {
                    return 0;
                }
            })
            .max()
            .orElse(0));
    }
    
    /**
     * Genera el siguiente código para una entidad bancaria (formato: ENT-001, ENT-002, etc.)
     */
    public String generarCodigoEntidadBancaria() {
        return generarSiguienteCodigo("ENT", entidadBancariaRepository.findAll().stream()
            .map(e -> e.getCodigo())
            .filter(c -> c != null && c.matches("ENT-\\d+"))
            .mapToInt(c -> {
                try {
                    return Integer.parseInt(c.substring(4));
                } catch (Exception e) {
                    return 0;
                }
            })
            .max()
            .orElse(0));
    }
    
    /**
     * Genera el siguiente código para una sucursal (formato: SUC-001, SUC-002, etc.)
     */
    public String generarCodigoSucursal() {
        return generarSiguienteCodigo("SUC", sucursalRepository.findAll().stream()
            .map(s -> s.getCodigo())
            .filter(c -> c != null && c.matches("SUC-\\d+"))
            .mapToInt(c -> {
                try {
                    return Integer.parseInt(c.substring(4));
                } catch (Exception e) {
                    return 0;
                }
            })
            .max()
            .orElse(0));
    }
    
    /**
     * Genera el siguiente código basado en el prefijo y el último número encontrado.
     * @param prefijo El prefijo del código (ej: "VIG", "ASL", etc.)
     * @param ultimoNumero El último número encontrado en la base de datos
     * @return El siguiente código en formato PREFIJO-XXX
     */
    private String generarSiguienteCodigo(String prefijo, int ultimoNumero) {
        int siguienteNumero = ultimoNumero + 1;
        return String.format("%s-%03d", prefijo, siguienteNumero);
    }
}

