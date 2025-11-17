package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;

/**
 * Representa un caso judicial asociado a un juez y una condena.
 */
@Entity
@Table(name = "casos")
public class Caso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "juez_id", nullable = false)
    private Juez juez;
    
    @OneToOne
    @JoinColumn(name = "condena_id", nullable = false)
    private Condena condena;

    public Caso() {
    }

    public Caso(Juez juez, Condena condena) {
        this.juez = juez;
        this.condena = condena;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Juez getJuez() {
        return juez;
    }

    public void setJuez(Juez juez) {
        this.juez = juez;
    }

    public Condena getCondena() {
        return condena;
    }

    public void setCondena(Condena condena) {
        this.condena = condena;
    }
}

