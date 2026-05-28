package com.hotel.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa un consumo de servicio adicional dentro de una reservación.
 * Relaciona una {@link Reservacion} con un {@link Servicio} consumido,
 * registrando la cantidad, el precio unitario cobrado en ese momento y la fecha.
 */
public class Consumo {
    /** Identificador único del consumo en la base de datos. */
    private int idConsumo;

    /** Reservación a la que pertenece este consumo. */
    private Reservacion reservacion;

    /** Servicio consumido. */
    private Servicio servicio;

    /** Cantidad de unidades del servicio consumidas. */
    private int cantidad;

    /** Precio unitario cobrado al momento de hacer el consumo. */
    private double precioUnitario;

    /** Fecha y hora en que se registró el consumo. */
    private LocalDateTime fecha;

    public Consumo() {}

    /**
     * Constructor con todos los campos que persiste la BD.
     */
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