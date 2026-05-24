package com.hotel.model;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String calle;
    private String ciudad;
    private String estado;
    private String cp;

    public Cliente() {}

    public Cliente(int idCliente, String nombre, String apellido, String correo,
                   String telefono, String calle, String ciudad, String estado, String cp) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.calle = calle;
        this.ciudad = ciudad;
        this.estado = estado;
        this.cp = cp;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }
}
