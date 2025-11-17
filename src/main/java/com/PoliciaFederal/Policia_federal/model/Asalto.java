package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa un asalto registrado en el sistema policial.
 * Contiene información sobre el incidente, incluyendo fecha, asaltante
 * involucrado, sucursal afectada y banda criminal relacionada.
 */
@Entity
@Table(name = "asaltos")
public class Asalto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @ManyToOne
    @JoinColumn(name = "asaltante_id")
    private Asaltante asaltante;
    
    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;
    
    @ManyToOne
    @JoinColumn(name = "banda_criminal_id")
    private BandaCriminal bandaCriminal;
    
    @Column(nullable = false)
    private Boolean condenado = false;

    public Asalto() {
    }

    public Asalto(LocalDate fecha, Asaltante asaltante, Sucursal sucursal, BandaCriminal bandaCriminal) {
        this.fecha = fecha;
        this.asaltante = asaltante;
        this.sucursal = sucursal;
        this.bandaCriminal = bandaCriminal;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Asaltante getAsaltante() {
        return asaltante;
    }

    public void setAsaltante(Asaltante asaltante) {
        this.asaltante = asaltante;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public BandaCriminal getBandaCriminal() {
        return bandaCriminal;
    }

    public void setBandaCriminal(BandaCriminal bandaCriminal) {
        this.bandaCriminal = bandaCriminal;
    }

    public Boolean getCondenado() {
        return condenado;
    }

    public void setCondenado(Boolean condenado) {
        this.condenado = condenado;
    }
}

