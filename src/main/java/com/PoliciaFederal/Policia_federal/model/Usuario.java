package com.PoliciaFederal.Policia_federal.model;

import jakarta.persistence.*;

/**
 * Representa un usuario del sistema, el cual tiene un nombre de usuario, una
 * contraseña, un rol con permisos específicos, y opcionalmente un vigilante
 * asociado.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "vigilante_id")
    private Vigilante vigilante; // null si no aplica

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String password, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    public boolean autenticar(String nombreUsuario, String password) {
        return this.nombreUsuario.equals(nombreUsuario) && this.password.equals(password);
    }

    public boolean puedeRealizar(Permiso permiso) {
        return rol != null && rol.tienePermiso(permiso);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }
}

