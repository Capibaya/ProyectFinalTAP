package com.hotel.service.observer;

/**
 * Observador concreto del patrón Observer.
 * <p>
 * Patrón de diseño: <b>Observer</b> — implementa {@link HabitacionObserver}
 * para actualizar el dashboard del sistema hotelero cada vez que el estado
 * de una habitación cambia. Muestra el cambio de estado por consola.
 * </p>
 */
public class DashboardObserver implements HabitacionObserver {

    /**
     * Recibe la notificación de cambio de estado de una habitación y refleja
     * la actualización en el dashboard imprimiendo un mensaje en consola.
     *
     * @param idHabitacion   identificador único de la habitación que cambió de estado
     * @param estadoAnterior nombre del estado previo de la habitación
     * @param estadoNuevo    nombre del nuevo estado de la habitación
     */
    @Override
    public void actualizar(int idHabitacion, String estadoAnterior, String estadoNuevo) {
        System.out.println("Dashboard actualizado: Habitacion " + idHabitacion +
                " cambio de [" + estadoAnterior + "] a [" + estadoNuevo + "]");
    }
}
