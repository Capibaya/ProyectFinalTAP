package com.hotel.model;

import java.time.LocalDateTime;

public class Consumo {
    private int idConsumo;
    private Reservacion reservacion;
    private Servicio servicio;
    private int cantidad;
    private double precioUnitario;
    private LocalDateTime fecha;

    public Consumo() {}

    public Consumo(int idConsumo, Reservacion reservacion, Servicio servicio,
                   int cantidad, double precioUnitario, LocalDateTime fecha) {
        this.idConsumo = idConsumo;
        this.reservacion = reservacion;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
    }

    public int getIdConsumo() { return idConsumo; }
    public void setIdConsumo(int idConsumo) { this.idConsumo = idConsumo; }
    public Reservacion getReservacion() { return reservacion; }
    public void setReservacion(Reservacion reservacion) { this.reservacion = reservacion; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}