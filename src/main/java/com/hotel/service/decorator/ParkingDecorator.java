package com.hotel.service.decorator;

/**
 * Decorador concreto que agrega el servicio de estacionamiento a una reservación.
 * <p>
 * Patrón de diseño: <b>Decorator</b> — extiende {@link ReservacionDecorator}
 * para sumar el costo fijo del estacionamiento ({@value #PRECIO_PARKING} MXN)
 * al costo total de la reservación y añadir la descripción correspondiente.
 * </p>
 */
public class ParkingDecorator extends ReservacionDecorator {

    /**
     * Precio fijo del servicio de estacionamiento por reservación en pesos mexicanos.
     */
    private static final double PRECIO_PARKING = 120.00;

    /**
     * Construye el decorador de estacionamiento envolviendo la reservación indicada.
     *
     * @param reservacion instancia de {@link ReservacionBase} a la que se le
     *                    agregará el servicio de estacionamiento
     */
    public ParkingDecorator(ReservacionBase reservacion) {
        super(reservacion);
    }

    /**
     * Calcula el costo total sumando el precio del estacionamiento al costo
     * de la reservación envuelta.
     *
     * @return costo de la reservación más {@value #PRECIO_PARKING} MXN por estacionamiento
     */
    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto() + PRECIO_PARKING;
    }

    /**
     * Retorna la descripción de la reservación envuelta con el texto del
     * servicio de estacionamiento agregado.
     *
     * @return descripción ampliada que incluye el servicio de estacionamiento y su costo
     */
    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion() + " + Estacionamiento ($120.00)";
    }
}