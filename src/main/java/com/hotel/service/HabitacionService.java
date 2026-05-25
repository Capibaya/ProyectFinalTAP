package com.hotel.service;

import com.hotel.dao.HabitacionDAO;
import com.hotel.model.Empleado;
import com.hotel.model.EstadoHabitacion;
import com.hotel.model.Habitacion;
import com.hotel.observer.DashboardObserver;
import com.hotel.observer.HabitacionSujeto;
import java.util.List;
/**
 * Servicio de habitaciones.
 * Usa el patron Observer para notificar cambios de estado.
 */
public class HabitacionService {
    private final HabitacionDAO habitacionDAO = new HabitacionDAO();
    private final HabitacionSujeto sujeto = new HabitacionSujeto();
    public HabitacionService() {
        // Registrar observadores
        sujeto.agregarObservador(new DashboardObserver());
    }
    /**
     * Obtiene todas las habitaciones.
     */
    public List<Habitacion> obtenerTodas() {
        return habitacionDAO.obtenerTodos();
    }
    /**
     * Obtiene habitaciones filtradas por estado.
     */
    public List<Habitacion> obtenerPorEstado(String estado) {
        return habitacionDAO.obtenerPorEstado(estado);
    }
    /**
     * Cuenta habitaciones por estado para el dashboard.
     */
    public int contarPorEstado(String estado) {
        return habitacionDAO.contarPorEstado(estado);
    }
    /**
     * Cambia el estado de una habitacion y notifica a los observadores.
     * @param habitacion Habitacion a modificar
     * @param nuevoEstado Nuevo estado a asignar
     * @param empleado Empleado que realiza el cambio
     */
    public void cambiarEstado(Habitacion habitacion, String nuevoEstado, Empleado empleado) {
        String estadoAnterior = habitacion.getEstadoHabitacion().getNombre();
        EstadoHabitacion estadoNuevo = new EstadoHabitacion();
        estadoNuevo.setNombre(nuevoEstado);
        habitacion.setEstadoHabitacion(estadoNuevo);
        habitacionDAO.actualizar(habitacion);
        // Notificar a todos los observadores (patron Observer)
        sujeto.notificar(habitacion.getIdHabitacion(), estadoAnterior, nuevoEstado);
    }
    /**
     * Guarda una nueva habitacion.
     */
    public void guardar(Habitacion habitacion) {
        habitacionDAO.guardar(habitacion);
    }
    /**
     * Actualiza los datos de una habitacion.
     */
    public void actualizar(Habitacion habitacion) {
        habitacionDAO.actualizar(habitacion);
    }
    /**
     * Elimina una habitacion por id.
     */
    public void eliminar(int id) {
        habitacionDAO.eliminar(id);
    }
}