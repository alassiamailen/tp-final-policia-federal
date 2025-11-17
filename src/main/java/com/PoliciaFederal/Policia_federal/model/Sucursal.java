package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una sucursal bancaria que tiene empleados, vigilantes contratados,
 * asaltantes relacionados y contratos con vigilantes.
 */
@Entity
@Table(name = "sucursales")
public class Sucursal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true)
    private String codigo;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String domicilio;
    
    @Column(nullable = false)
    private Integer cantidadDeEmpleados;
    
    @ManyToOne
    @JoinColumn(name = "entidad_bancaria_id", nullable = false)
    private EntidadBancaria entidadBancaria;
    
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratoSucVig> contratos = new ArrayList<>();
    
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asalto> asaltos = new ArrayList<>();

    public Sucursal() {
    }

    public Sucursal(String codigo, String nombre, String domicilio, Integer cantidadDeEmpleados, EntidadBancaria entidadBancaria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.cantidadDeEmpleados = cantidadDeEmpleados;
        this.entidadBancaria = entidadBancaria;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getCantidadDeEmpleados() {
        return cantidadDeEmpleados;
    }

    public void setCantidadDeEmpleados(Integer cantidadDeEmpleados) {
        this.cantidadDeEmpleados = cantidadDeEmpleados;
    }

    public EntidadBancaria getEntidadBancaria() {
        return entidadBancaria;
    }

    public void setEntidadBancaria(EntidadBancaria entidadBancaria) {
        this.entidadBancaria = entidadBancaria;
    }

    public List<ContratoSucVig> getContratos() {
        return contratos;
    }

    public void setContratos(List<ContratoSucVig> contratos) {
        this.contratos = contratos;
    }

    public List<Asalto> getAsaltos() {
        return asaltos;
    }

    public void setAsaltos(List<Asalto> asaltos) {
        this.asaltos = asaltos;
    }
}

