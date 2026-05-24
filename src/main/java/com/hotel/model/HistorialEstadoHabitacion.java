package com.hotel.model;

import java.time.LocalDateTime;

public class HistorialEstadoHabitacion {
    private int idHistorial;
    private Habitacion habitacion;
    private EstadoHabitacion estadoAnterior;
    private EstadoHabitacion estadoNuevo;
    private Empleado empleado;
    private LocalDateTime fechaCambio;
    private String motivo;

    public HistorialEstadoHabitacion() {}

    public HistorialEstadoHabitacion(int idHistorial, Habitacion habitacion,
                                     EstadoHabitacion estadoAnterior, EstadoHabitacion estadoNuevo,
                                     Empleado empleado, LocalDateTime fechaCambio, String motivo) {
        this.idHistorial = idHistorial;
        this.habitacion = habitacion;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.empleado = empleado;
        this.fechaCambio = fechaCambio;
        this.motivo = motivo;
    }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }
    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    public EstadoHabitacion getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoHabitacion estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public EstadoHabitacion getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoHabitacion estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}