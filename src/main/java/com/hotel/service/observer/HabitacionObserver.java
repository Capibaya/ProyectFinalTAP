package com.hotel.service.observer;

/**
 * Interfaz del patrón Observer.
 * <p>
 * Patrón de diseño: <b>Observer</b> — define el contrato que deben cumplir
 * todos los observadores interesados en recibir notificaciones cuando el
 * estado de una habitación cambia dentro del sistema hotelero.
 * </p>
 * Todo objeto que desee ser notificado de cambios en el estado de una
 * habitación debe implementar esta interfaz.
 */
public interface HabitacionObserver {

    /**
     * Método invocado por el sujeto ({@link HabitacionSujeto}) cada vez que
     * el estado de una habitación cambia.
     *
     * @param idHabitacion  identificador único de la habitación que cambió de estado
     * @param estadoAnterior nombre del estado previo de la habitación
     * @param estadoNuevo    nombre del nuevo estado de la habitación
     */
    void actualizar(int idHabitacion, String estadoAnterior, String estadoNuevo);
}
