package com.hotel.service.observer;

/**
 * Interfaz Observer.
 * Todo el que quiera ser notificado de cambios de habitacion
 * debe implementar esta interfaz.
 */
public interface HabitacionObserver {
    void actualizar(int idHabitacion, String estadoAnterior, String estadoNuevo);
}
