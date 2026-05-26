package com.hotel.service.decorator;

/**
 * Decorador abstracto del patrón Decorator.
 * <p>
 * Patrón de diseño: <b>Decorator</b> — clase base para todos los decoradores
 * concretos ({@link DesayunoDecorator}, {@link ParkingDecorator},
 * {@link SpaDecorator}). Envuelve una instancia de {@link ReservacionBase}
 * y delega las llamadas a los métodos, permitiendo que las subclases agreguen
 * comportamiento adicional antes o después de la delegación.
 * </p>
 */
public abstract class ReservacionDecorator implements ReservacionBase {

    /**
     * Instancia de {@link ReservacionBase} que este decorador envuelve.
     * Las subclases acceden a este campo para extender el comportamiento.
     */
    protected ReservacionBase reservacion;

    /**
     * Construye el decorador envolviendo la reservación indicada.
     *
     * @param reservacion instancia de {@link ReservacionBase} a decorar;
     *                    puede ser un componente concreto u otro decorador
     */
    public ReservacionDecorator(ReservacionBase reservacion) {
        this.reservacion = reservacion;
    }

    /**
     * Delega el cálculo del costo a la instancia envuelta.
     * Las subclases deben sobreescribir este método para agregar costos adicionales.
     *
     * @return costo calculado por la reservación envuelta
     */
    @Override
    public double calcularCosto() {
        return reservacion.calcularCosto();
    }

    /**
     * Delega la obtención de la descripción a la instancia envuelta.
     * Las subclases deben sobreescribir este método para ampliar la descripción.
     *
     * @return descripción de la reservación envuelta
     */
    @Override
    public String getDescripcion() {
        return reservacion.getDescripcion();
    }
}
