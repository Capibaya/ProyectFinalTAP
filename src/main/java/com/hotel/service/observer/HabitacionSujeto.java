package com.hotel.service.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto del patron Observer.
 * Mantiene la lista de observadores y los notifica
 * cuando el estado de una habitacion cambia.
 */
public class HabitacionSujeto {

    private final List<HabitacionObserver> observadores = new ArrayList<>();

    public void agregarObservador(HabitacionObserver observador) {
        observadores.add(observador);
    }

    public void removerObservador(HabitacionObserver observador) {
        observadores.remove(observador);
    }

    public void notificar(int idHabitacion, String estadoAnterior, String estadoNuevo) {
        for (HabitacionObserver obs : observadores) {
            obs.actualizar(idHabitacion, estadoAnterior, estadoNuevo);
        }
    }
}
