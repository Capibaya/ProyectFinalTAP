package com.hotel.service.decorator;

/**
 * Interfaz base del patron Decorator.
 * Define el contrato para calcular el costo de una reservacion.
 */
public interface ReservacionBase {
    double calcularCosto();
    String getDescripcion();
}
