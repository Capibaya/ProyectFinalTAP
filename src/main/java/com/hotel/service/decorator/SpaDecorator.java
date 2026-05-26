package com.hotel.service.decorator;

/**
 * Decorador concreto que agrega el servicio de spa a una reservación.
 * <p>
 * Patrón de diseño: <b>Decorator</b> — extiende {@link ReservacionDecorator}
 * para sumar el costo fijo del spa ({@value #PRECIO_SPA} MXN) al costo total
 * de la reservación y añadir la descripción correspondiente.
 * </p>
 */
public class SpaDecorator extends ReservacionDecorator {

    /**
     * Precio fijo del servicio de spa por reservación en pesos mexicanos.
     */
    private static final double PRECIO_SPA = 500.00;

    /**
     * Construye el decorador de spa envolviendo la reservación indicada.
     *
     * @param reservacion instancia de {@link ReservacionBase} a la que se le
     *                    agregará el servicio de spa
     */
    public SpaDecorator(ReservacionBase reservacion) {
        super(reservacion);
    }

    /**
     * Calcula el costo total sumando el precio del spa al costo de la
     * reservación envuelta.
     *
     * @return costo de la reservación más {@value #PRECIO_SPA} MXN por spa
     */
    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto() + PRECIO_SPA;
    }

    /**
     * Retorna la descripción de la reservación envuelta con el texto del
     * servicio de spa agregado.
     *
     * @return descripción ampliada que incluye el servicio de spa y su costo
     */
    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion() + " + Spa ($500.00)";
    }
}
