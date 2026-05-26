package com.hotel.service.decorator;

/**
 * Componente concreto del patrón Decorator.
 * <p>
 * Patrón de diseño: <b>Decorator</b> — representa la reservación base sin
 * servicios adicionales. Implementa {@link ReservacionBase} con el costo y
 * la descripción originales sobre los cuales los decoradores añadirán
 * funcionalidades de forma dinámica.
 * </p>
 */
public class ReservacionConcreto implements ReservacionBase {

    /**
     * Costo base de la reservación sin servicios adicionales, en pesos mexicanos.
     */
    private final double costoBase;

    /**
     * Descripción textual inicial de la reservación.
     */
    private final String descripcion;

    /**
     * Construye una reservación concreta con el costo y la descripción indicados.
     *
     * @param costoBase   costo inicial de la reservación en pesos mexicanos
     * @param descripcion descripción textual de la reservación base
     */
    public ReservacionConcreto(double costoBase, String descripcion) {
        this.costoBase = costoBase;
        this.descripcion = descripcion;
    }

    /**
     * Retorna el costo base de la reservación sin ningún servicio adicional.
     *
     * @return costo base en pesos mexicanos
     */
    @Override
    public double calcularCosto() {
        return costoBase;
    }

    /**
     * Retorna la descripción textual original de la reservación.
     *
     * @return descripción de la reservación base
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }
}