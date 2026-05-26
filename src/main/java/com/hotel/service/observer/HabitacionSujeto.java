package com.hotel.service.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto (Subject) del patrón Observer.
 * <p>
 * Patrón de diseño: <b>Observer</b> — mantiene la lista de observadores
 * registrados y los notifica automáticamente cada vez que el estado de
 * una habitación cambia. Permite agregar o remover observadores en tiempo
 * de ejecución sin modificar la lógica del sujeto.
 * </p>
 */
public class HabitacionSujeto {

    /**
     * Lista interna de observadores registrados que recibirán las notificaciones
     * de cambios de estado.
     */
    private final List<HabitacionObserver> observadores = new ArrayList<>();

    /**
     * Registra un nuevo observador en la lista de notificaciones.
     *
     * @param observador instancia de {@link HabitacionObserver} que desea recibir notificaciones
     */
    public void agregarObservador(HabitacionObserver observador) {
        observadores.add(observador);
    }

    /**
     * Elimina un observador previamente registrado de la lista de notificaciones.
     *
     * @param observador instancia de {@link HabitacionObserver} que se desea remover
     */
    public void removerObservador(HabitacionObserver observador) {
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio de estado
     * en una habitación.
     *
     * @param idHabitacion   identificador único de la habitación que cambió de estado
     * @param estadoAnterior nombre del estado previo de la habitación
     * @param estadoNuevo    nombre del nuevo estado de la habitación
     */
    public void notificar(int idHabitacion, String estadoAnterior, String estadoNuevo) {
        for (HabitacionObserver obs : observadores) {
            obs.actualizar(idHabitacion, estadoAnterior, estadoNuevo);
        }
    }
}
