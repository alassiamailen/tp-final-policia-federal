package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un juez con nombre completo, código identificador y años de
 * servicio.
 */
@Entity
@Table(name = "jueces")
public class Juez {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true)
    private String claveInternaJuzgado;
    
    @Column(nullable = false)
    private String nombreApellido;
    
    @Column(nullable = false)
    private Integer aniosDeServicio;
    
    @OneToMany(mappedBy = "juez", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Caso> casosAsignados = new ArrayList<>();

    public Juez() {
    }

    public Juez(String claveInternaJuzgado, String nombreApellido, Integer aniosDeServicio) {
        this.claveInternaJuzgado = claveInternaJuzgado;
        this.nombreApellido = nombreApellido;
        this.aniosDeServicio = aniosDeServicio;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveInternaJuzgado() {
        return claveInternaJuzgado;
    }

    public void setClaveInternaJuzgado(String claveInternaJuzgado) {
        this.claveInternaJuzgado = claveInternaJuzgado;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public Integer getAniosDeServicio() {
        return aniosDeServicio;
    }

    public void setAniosDeServicio(Integer aniosDeServicio) {
        this.aniosDeServicio = aniosDeServicio;
    }

    public List<Caso> getCasosAsignados() {
        return casosAsignados;
    }

    public void setCasosAsignados(List<Caso> casosAsignados) {
        this.casosAsignados = casosAsignados;
    }
}

