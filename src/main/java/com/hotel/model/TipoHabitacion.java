package com.hotel.model;

public class TipoHabitacion {
    private int idTipo;
    private String nombre;
    private int capacidad;
    private double precioNoche;

    public TipoHabitacion() {}

    public TipoHabitacion(int idTipo, String nombre, int capacidad, double precioNoche) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
    }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public double getPrecioNoche() { return precioNoche; }
    public void setPrecioNoche(double precioNoche) { this.precioNoche = precioNoche; }
}
