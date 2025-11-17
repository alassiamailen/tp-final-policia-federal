package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un asaltante dentro del sistema policial.
 * Contiene información personal del asaltante, su afiliación a bandas
 * criminales y el historial de asaltos cometidos.
 */
@Entity
@Table(name = "asaltantes")
public class Asaltante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true)
    private String codigo;
    
    @Column(nullable = false)
    private String nombreApellido;
    
    @ManyToOne
    @JoinColumn(name = "banda_criminal_id")
    private BandaCriminal bandaCriminal;
    
    @OneToMany(mappedBy = "asaltante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asalto> asaltos = new ArrayList<>();
    
    @OneToMany(mappedBy = "asaltante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Condena> condenas = new ArrayList<>();

    public Asaltante() {
    }

    public Asaltante(String codigo, String nombreApellido) {
        this.codigo = codigo;
        this.nombreApellido = nombreApellido;
    }

    public Asaltante(String codigo, String nombreApellido, BandaCriminal bandaCriminal) {
        this.codigo = codigo;
        this.nombreApellido = nombreApellido;
        this.bandaCriminal = bandaCriminal;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public BandaCriminal getBandaCriminal() {
        return bandaCriminal;
    }

    public void setBandaCriminal(BandaCriminal bandaCriminal) {
        this.bandaCriminal = bandaCriminal;
    }

    public List<Asalto> getAsaltos() {
        return asaltos;
    }

    public void setAsaltos(List<Asalto> asaltos) {
        this.asaltos = asaltos;
    }

    public List<Condena> getCondenas() {
        return condenas;
    }

    public void setCondenas(List<Condena> condenas) {
        this.condenas = condenas;
    }
}

