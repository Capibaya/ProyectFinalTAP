package com.hotel.model;

public class EstadoHabitacion {
    private int idEstadoHabitacion;
    private String nombre;

    public EstadoHabitacion() {}

    public EstadoHabitacion(int idEstadoHabitacion, String nombre) {
        this.idEstadoHabitacion = idEstadoHabitacion;
        this.nombre = nombre;
    }

    public int getIdEstadoHabitacion() { return idEstadoHabitacion; }
    public void setIdEstadoHabitacion(int idEstadoHabitacion) { this.idEstadoHabitacion = idEstadoHabitacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
