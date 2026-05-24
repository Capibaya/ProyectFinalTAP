package com.hotel.model;

public class Habitacion {
    private int idHabitacion;
    private String numero;
    private int piso;
    private String descripcion;
    private TipoHabitacion tipoHabitacion;
    private EstadoHabitacion estadoHabitacion;

    public Habitacion() {}

    public Habitacion(int idHabitacion, String numero, int piso, String descripcion,
                      TipoHabitacion tipoHabitacion, EstadoHabitacion estadoHabitacion) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.piso = piso;
        this.descripcion = descripcion;
        this.tipoHabitacion = tipoHabitacion;
        this.estadoHabitacion = estadoHabitacion;
    }

    public int getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public TipoHabitacion getTipoHabitacion() { return tipoHabitacion; }
    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) { this.tipoHabitacion = tipoHabitacion; }
    public EstadoHabitacion getEstadoHabitacion() { return estadoHabitacion; }
    public void setEstadoHabitacion(EstadoHabitacion estadoHabitacion) { this.estadoHabitacion = estadoHabitacion; }
}
