package com.hotel.model;

import java.time.LocalDateTime;

public class CheckIn {
    private int idCheckIn;
    private Reservacion reservacion;
    private Empleado empleado;
    private LocalDateTime fechaHora;
    private String observaciones;

    public CheckIn() {}

    public CheckIn(int idCheckIn, Reservacion reservacion, Empleado empleado,
                   LocalDateTime fechaHora, String observaciones) {
        this.idCheckIn = idCheckIn;
        this.reservacion = reservacion;
        this.empleado = empleado;
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
    }

    public int getIdCheckIn() { return idCheckIn; }
    public void setIdCheckIn(int idCheckIn) { this.idCheckIn = idCheckIn; }
    public Reservacion getReservacion() { return reservacion; }
    public void setReservacion(Reservacion reservacion) { this.reservacion = reservacion; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
