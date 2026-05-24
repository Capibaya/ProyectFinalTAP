package com.hotel.service.observer;

/**
 * Observador concreto.
 * Actualiza el dashboard cuando cambia el estado de una habitacion.
 */
public class DashboardObserver implements HabitacionObserver {

    @Override
    public void actualizar(int idHabitacion, String estadoAnterior, String estadoNuevo) {
        System.out.println("Dashboard actualizado: Habitacion " + idHabitacion +
                " cambio de [" + estadoAnterior + "] a [" + estadoNuevo + "]");
    }
}
