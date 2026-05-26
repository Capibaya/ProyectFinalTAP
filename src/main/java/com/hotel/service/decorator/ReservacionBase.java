package com.hotel.service.decorator;

/**
 * Interfaz base del patrón Decorator para reservaciones hoteleras.
 * <p>
 * Patrón de diseño: <b>Decorator</b> — define el contrato que comparten
 * tanto el componente concreto ({@link ReservacionConcreto}) como los
 * decoradores ({@link ReservacionDecorator} y sus subclases). Permite
 * agregar servicios adicionales a una reservación de forma dinámica y
 * transparente sin modificar las clases existentes.
 * </p>
 */
public interface ReservacionBase {

    /**
     * Calcula el costo total de la reservación, incluyendo los servicios
     * adicionales que hayan sido decorados sobre el costo base.
     *
     * @return costo total de la reservación en pesos mexicanos
     */
    double calcularCosto();

    /**
     * Retorna la descripción textual de la reservación, incluyendo los
     * servicios adicionales agregados mediante decoradores.
     *
     * @return cadena descriptiva de la reservación y sus servicios incluidos
     */
    String getDescripcion();
}
