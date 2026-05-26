package com.hotel.service;

import com.hotel.dao.HabitacionDAO;
import com.hotel.model.EstadoHabitacion;
import com.hotel.model.Habitacion;
import com.hotel.service.observer.DashboardObserver;
import com.hotel.service.observer.HabitacionObserver;
import com.hotel.service.observer.HabitacionSujeto;

import java.util.List;
import java.util.Map;

public class HabitacionService {

    private final HabitacionDAO dao = new HabitacionDAO();
    private final HabitacionSujeto sujeto = new HabitacionSujeto();

    public HabitacionService() {
        sujeto.agregarObservador(new DashboardObserver());
    }

    public List<Habitacion> obtenerTodas() {
        return dao.obtenerTodos();
    }

    public List<Habitacion> obtenerPorEstado(String estado) {
        return dao.obtenerPorEstado(estado);
    }

    public int contarPorEstado(String estado) {
        return dao.contarPorEstado(estado);
    }

    /** Retorna los nombres de estados disponibles en la BD (para el ComboBox). */
    public List<String> obtenerNombresEstados() {
        return dao.obtenerNombresEstados();
    }

    /** Retorna los ingresos reales agrupados por mes del año actual. */
    public Map<String, Double> obtenerIngresosPorMes() {
        return dao.obtenerIngresosPorMes();
    }

    /**
     * Cambia el estado de una habitación.
     * Bug corregido: ahora resuelve el id_estado_habitacion real desde la BD,
     * evitando que se guarde id=0 en el UPDATE.
     */
    public void cambiarEstado(Habitacion habitacion, String nuevoEstado, HabitacionObserver observadorExtra) {
        String estadoAnterior = habitacion.getEstadoHabitacion() != null
                ? habitacion.getEstadoHabitacion().getNombre()
                : "desconocido";

        // Resolver el ID real del estado desde la BD
        int idEstado = dao.buscarIdEstadoPorNombre(nuevoEstado);
        if (idEstado < 0) {
            System.err.println("Estado '" + nuevoEstado + "' no encontrado en la BD. Abortando cambio.");
            return;
        }

        if (observadorExtra != null) sujeto.agregarObservador(observadorExtra);

        EstadoHabitacion nuevoEstadoObj = new EstadoHabitacion();
        nuevoEstadoObj.setIdEstadoHabitacion(idEstado);
        nuevoEstadoObj.setNombre(nuevoEstado);
        habitacion.setEstadoHabitacion(nuevoEstadoObj);
        dao.actualizar(habitacion);

        sujeto.notificar(habitacion.getIdHabitacion(), estadoAnterior, nuevoEstado);

        if (observadorExtra != null) sujeto.removerObservador(observadorExtra);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}