package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un vigilante con nombre, código, edad y contratos asociados.
 */
@Entity
@Table(name = "vigilantes")
public class Vigilante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true)
    private String codigo;
    
    @Column(nullable = false)
    private String nombreApellido;
    
    @Column(nullable = false)
    private Integer edad;
    
    @OneToOne(mappedBy = "vigilante")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "vigilante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratoSucVig> contratos = new ArrayList<>();

    public Vigilante() {
    }

    public Vigilante(String codigo, String nombreApellido, Integer edad) {
        this.codigo = codigo;
        this.nombreApellido = nombreApellido;
        this.edad = edad;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ContratoSucVig> getContratos() {
        return contratos;
    }

    public void setContratos(List<ContratoSucVig> contratos) {
        this.contratos = contratos;
    }
}

