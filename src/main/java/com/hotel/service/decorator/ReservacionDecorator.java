package com.hotel.service.decorator;

/**
 * Decorator abstracto.
 * Envuelve una ReservacionBase y delega las llamadas.
 */
public abstract class ReservacionDecorator implements ReservacionBase {

    protected ReservacionBase reservacion;

    public ReservacionDecorator(ReservacionBase reservacion) {
        this.reservacion = reservacion;
    }

    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto();
    }

    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion();
    }
}
