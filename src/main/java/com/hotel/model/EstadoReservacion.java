package com.hotel.model;

public class EstadoReservacion {
    private int idEstadoReservacion;
    private String nombre;

    public EstadoReservacion() {}

    public EstadoReservacion(int idEstadoReservacion, String nombre) {
        this.idEstadoReservacion = idEstadoReservacion;
        this.nombre = nombre;
    }

    public int getIdEstadoReservacion() { return idEstadoReservacion; }
    public void setIdEstadoReservacion(int idEstadoReservacion) { this.idEstadoReservacion = idEstadoReservacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}