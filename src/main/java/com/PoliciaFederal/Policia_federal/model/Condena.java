package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;

/**
 * Representa una condena asociada a un asaltante y un asalto.
 */
@Entity
@Table(name = "condenas")
public class Condena {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "asaltante_id", nullable = false)
    private Asaltante asaltante;
    
    @ManyToOne
    @JoinColumn(name = "asalto_id", nullable = false)
    private Asalto asalto;
    
    @Column(nullable = false)
    private Boolean fueCondenado = false;
    
    @Column(nullable = true)
    private Integer tiempoCondena; // en años, nullable si no fue condenado

    public Condena() {
    }

    public Condena(Asaltante asaltante, Asalto asalto, Boolean fueCondenado, Integer tiempoCondena) {
        this.asaltante = asaltante;
        this.asalto = asalto;
        this.fueCondenado = fueCondenado;
        this.tiempoCondena = tiempoCondena;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asaltante getAsaltante() {
        return asaltante;
    }

    public void setAsaltante(Asaltante asaltante) {
        this.asaltante = asaltante;
    }

    public Asalto getAsalto() {
        return asalto;
    }

    public void setAsalto(Asalto asalto) {
        this.asalto = asalto;
    }

    public Boolean getFueCondenado() {
        return fueCondenado;
    }

    public void setFueCondenado(Boolean fueCondenado) {
        this.fueCondenado = fueCondenado;
    }

    public Integer getTiempoCondena() {
        return tiempoCondena;
    }

    public void setTiempoCondena(Integer tiempoCondena) {
        this.tiempoCondena = tiempoCondena;
    }
}

