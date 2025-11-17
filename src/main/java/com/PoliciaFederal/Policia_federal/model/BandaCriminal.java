package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una banda criminal con información básica de identificación y
 * miembros.
 */
@Entity
@Table(name = "bandas_criminales")
public class BandaCriminal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private Integer numeroIdentificacion;
    
    @Column(nullable = false)
    private Integer cantidadDeMiembros;
    
    @OneToMany(mappedBy = "bandaCriminal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asaltante> asaltantes = new ArrayList<>();
    
    @OneToMany(mappedBy = "bandaCriminal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asalto> asaltos = new ArrayList<>();

    public BandaCriminal() {
    }

    public BandaCriminal(Integer numeroIdentificacion, Integer cantidadDeMiembros) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.cantidadDeMiembros = cantidadDeMiembros;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(Integer numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Integer getCantidadDeMiembros() {
        return cantidadDeMiembros;
    }

    public void setCantidadDeMiembros(Integer cantidadDeMiembros) {
        this.cantidadDeMiembros = cantidadDeMiembros;
    }

    public List<Asaltante> getAsaltantes() {
        return asaltantes;
    }

    public void setAsaltantes(List<Asaltante> asaltantes) {
        this.asaltantes = asaltantes;
    }

    public List<Asalto> getAsaltos() {
        return asaltos;
    }

    public void setAsaltos(List<Asalto> asaltos) {
        this.asaltos = asaltos;
    }
}

