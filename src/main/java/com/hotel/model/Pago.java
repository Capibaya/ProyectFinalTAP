package com.hotel.model;

import java.time.LocalDateTime;

public class Pago {
    private int idPago;
    private Reservacion reservacion;
    private MetodoPago metodoPago;
    private double monto;
    private LocalDateTime fechaPago;

    public Pago() {}

    public Pago(int idPago, Reservacion reservacion, MetodoPago metodoPago,
                double monto, LocalDateTime fechaPago) {
        this.idPago = idPago;
        this.reservacion = reservacion;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }
    public Reservacion getReservacion() { return reservacion; }
    public void setReservacion(Reservacion reservacion) { this.reservacion = reservacion; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}
