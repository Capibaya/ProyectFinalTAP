package com.hotel.service.decorator;

/**
 * Agrega el servicio de spa al costo base.
 */
public class SpaDecorator extends ReservacionDecorator {

    private static final double PRECIO_SPA = 500.00;

    public SpaDecorator(ReservacionBase reservacion) {
        super(reservacion);
    }

    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto() + PRECIO_SPA;
    }

    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion() + " + Spa ($500.00)";
    }
}
