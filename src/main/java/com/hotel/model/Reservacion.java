package com.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservacion {
    private int idReservacion;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDateTime fechaReservacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int numeroHuespedes;
    private double total;
    private EstadoReservacion estadoReservacion;

    public Reservacion() {}

    public Reservacion(int idReservacion, Cliente cliente, Habitacion habitacion,
                       LocalDateTime fechaReservacion, LocalDate fechaEntrada,
                       LocalDate fechaSalida, int numeroHuespedes,
                       double total, EstadoReservacion estadoReservacion) {
        this.idReservacion = idReservacion;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaReservacion = fechaReservacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numeroHuespedes = numeroHuespedes;
        this.total = total;
        this.estadoReservacion = estadoReservacion;
    }

    public int getIdReservacion() { return idReservacion; }
    public void setIdReservacion(int idReservacion) { this.idReservacion = idReservacion; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    public LocalDateTime getFechaReservacion() { return fechaReservacion; }
    public void setFechaReservacion(LocalDateTime fechaReservacion) { this.fechaReservacion = fechaReservacion; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }
    public int getNumeroHuespedes() { return numeroHuespedes; }
    public void setNumeroHuespedes(int numeroHuespedes) { this.numeroHuespedes = numeroHuespedes; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public EstadoReservacion getEstadoReservacion() { return estadoReservacion; }
    public void setEstadoReservacion(EstadoReservacion estadoReservacion) { this.estadoReservacion = estadoReservacion; }
}