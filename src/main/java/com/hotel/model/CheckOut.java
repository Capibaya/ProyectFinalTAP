package com.hotel.model;

import java.time.LocalDateTime;

public class CheckOut {
    private int idCheckOut;
    private Reservacion reservacion;
    private Empleado empleado;
    private LocalDateTime fechaHora;

    public CheckOut() {}

    public CheckOut(int idCheckOut, Reservacion reservacion,
                    Empleado empleado, LocalDateTime fechaHora) {
        this.idCheckOut = idCheckOut;
        this.reservacion = reservacion;
        this.empleado = empleado;
        this.fechaHora = fechaHora;
    }

    public int getIdCheckOut() { return idCheckOut; }
    public void setIdCheckOut(int idCheckOut) { this.idCheckOut = idCheckOut; }
    public Reservacion getReservacion() { return reservacion; }
    public void setReservacion(Reservacion reservacion) { this.reservacion = reservacion; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
