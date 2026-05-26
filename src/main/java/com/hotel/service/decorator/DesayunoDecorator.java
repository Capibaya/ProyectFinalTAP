package com.hotel.service.decorator;

/**
 * Decorador concreto que agrega el servicio de desayuno a una reservación.
 * <p>
 * Patrón de diseño: <b>Decorator</b> — extiende {@link ReservacionDecorator}
 * para sumar el costo fijo del desayuno ({@value #PRECIO_DESAYUNO} MXN) al
 * costo total de la reservación y añadir la descripción correspondiente.
 * </p>
 */
public class DesayunoDecorator extends ReservacionDecorator {

    /**
     * Precio fijo del servicio de desayuno por reservación en pesos mexicanos.
     */
    private static final double PRECIO_DESAYUNO = 180.00;

    /**
     * Construye el decorador de desayuno envolviendo la reservación indicada.
     *
     * @param reservacion instancia de {@link ReservacionBase} a la que se le
     *                    agregará el servicio de desayuno
     */
    public DesayunoDecorator(ReservacionBase reservacion) {
        super(reservacion);
    }

    /**
     * Calcula el costo total sumando el precio del desayuno al costo de la
     * reservación envuelta.
     *
     * @return costo de la reservación más {@value #PRECIO_DESAYUNO} MXN por desayuno
     */
    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto() + PRECIO_DESAYUNO;
    }

    /**
     * Retorna la descripción de la reservación envuelta con el texto del
     * servicio de desayuno agregado.
     *
     * @return descripción ampliada que incluye el servicio de desayuno y su costo
     */
    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion() + " + Desayuno ($180.00)";
    }
}
