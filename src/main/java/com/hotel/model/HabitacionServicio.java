package com.hotel.model;

public class HabitacionServicio {
    private Habitacion habitacion;
    private Servicio servicio;

    public HabitacionServicio() {}

    public HabitacionServicio(Habitacion habitacion, Servicio servicio) {
        this.habitacion = habitacion;
        this.servicio = servicio;
    }

    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
}