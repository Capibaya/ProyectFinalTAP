package com.hotel.service.decorator;

/**
 * Agrega el servicio de desayuno al costo base.
 */
public class DesayunoDecorator extends ReservacionDecorator {

    private static final double PRECIO_DESAYUNO = 180.00;

    public DesayunoDecorator(ReservacionBase reservacion) {
        super(reservacion);
    }

    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto() + PRECIO_DESAYUNO;
    }

    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion() + " + Desayuno ($180.00)";
    }
}
