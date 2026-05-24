package com.hotel.model;

public class UsuarioSistema {
    private int idUsuario;
    private Empleado empleado;
    private String usuario;
    private String passwordHash;
    private Rol rol;

    public UsuarioSistema() {}

    public UsuarioSistema(int idUsuario, Empleado empleado, String usuario,
                          String passwordHash, Rol rol) {
        this.idUsuario = idUsuario;
        this.empleado = empleado;
        this.usuario = usuario;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
