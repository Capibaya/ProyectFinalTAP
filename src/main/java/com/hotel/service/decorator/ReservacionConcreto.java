package com.hotel.service.decorator;


public class ReservacionConcreto implements ReservacionBase {

    private final double costoBase;
    private final String descripcion;

    public ReservacionConcreto(double costoBase, String descripcion) {
        this.costoBase = costoBase;
        this.descripcion = descripcion;
    }

    @Override
    public double calcularCosto() {
        return costoBase;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}