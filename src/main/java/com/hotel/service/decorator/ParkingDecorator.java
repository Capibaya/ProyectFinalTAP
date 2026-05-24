package com.hotel.service.decorator;

/**
 * Agrega el servicio de estacionamiento al costo base.
 */
public class ParkingDecorator extends ReservacionDecorator {

    private static final double PRECIO_PARKING = 120.00;

    public ParkingDecorator(ReservacionBase reservacion) {
        super(reservacion);
    }

    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto() + PRECIO_PARKING;
    }

    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion() + " + Estacionamiento ($120.00)";
    }
}