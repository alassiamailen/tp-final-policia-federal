package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa un contrato entre una sucursal bancaria y un vigilante.
 * Gestiona información sobre fechas de contrato, entidad bancaria, sucursal,
 * vigilante asignado y condiciones de portación de arma.
 */
@Entity
@Table(name = "contratos_suc_vig")
public class ContratoSucVig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate fechaInicio;
    
    @Column(nullable = false)
    private LocalDate fechaFin;
    
    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;
    
    @ManyToOne
    @JoinColumn(name = "vigilante_id", nullable = false)
    private Vigilante vigilante;
    
    @Column(nullable = false)
    private Boolean portarArma;

    public ContratoSucVig() {
    }

    public ContratoSucVig(LocalDate fechaInicio, LocalDate fechaFin, Sucursal sucursal, Vigilante vigilante, Boolean portarArma) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sucursal = sucursal;
        this.vigilante = vigilante;
        this.portarArma = portarArma;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    public Boolean getPortarArma() {
        return portarArma;
    }

    public void setPortarArma(Boolean portarArma) {
        this.portarArma = portarArma;
    }
}

