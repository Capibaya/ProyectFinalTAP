package com.hotel.service;

import com.hotel.dao.HabitacionDAO;
import com.hotel.model.EstadoHabitacion;
import com.hotel.model.Habitacion;
import com.hotel.service.observer.DashboardObserver;
import com.hotel.service.observer.HabitacionObserver;
import com.hotel.service.observer.HabitacionSujeto;

import java.util.List;
import java.util.Map;

/**
 * Servicio de negocio para la gestión de habitaciones del hotel.
 * <p>
 * Coordina las operaciones sobre habitaciones delegando la persistencia al {@link HabitacionDAO}
 * y notificando los cambios de estado a los observadores registrados.
 * </p>
 * <p>
 * Patrón de diseño: <b>Observer</b> — utiliza {@link HabitacionSujeto} para notificar
 * automáticamente a los observadores (p. ej. {@link DashboardObserver}) ante cualquier
 * cambio de estado de una habitación.
 * </p>
 */
public class HabitacionService {

    /**
     * Objeto de acceso a datos para operaciones CRUD sobre habitaciones.
     */
    private final HabitacionDAO dao = new HabitacionDAO();

    /**
     * Sujeto observable que gestiona y notifica a los observadores registrados
     * cuando cambia el estado de una habitación.
     */
    private final HabitacionSujeto sujeto = new HabitacionSujeto();

    /**
     * Construye el servicio y registra el {@link DashboardObserver} como observador
     * predeterminado del sujeto de habitaciones.
     */
    public HabitacionService() {
        sujeto.agregarObservador(new DashboardObserver());
    }

    /**
     * Obtiene la lista completa de habitaciones registradas en el sistema.
     *
     * @return lista de {@link Habitacion}; puede estar vacía si no hay registros.
     */
    public List<Habitacion> obtenerTodas() {
        return dao.obtenerTodos();
    }

    /**
     * Obtiene las habitaciones filtradas por un estado específico.
     *
     * @param estado el nombre del estado por el cual filtrar (p. ej. "disponible", "ocupada").
     * @return lista de {@link Habitacion} que coinciden con el estado indicado.
     */
    public List<Habitacion> obtenerPorEstado(String estado) {
        return dao.obtenerPorEstado(estado);
    }

    /**
     * Cuenta el número de habitaciones que tienen un estado determinado.
     *
     * @param estado el nombre del estado a contar.
     * @return cantidad de habitaciones con el estado especificado.
     */
    public int contarPorEstado(String estado) {
        return dao.contarPorEstado(estado);
    }

    /**
     * Retorna los nombres de estados disponibles en la BD (para el ComboBox).
     *
     * @return lista de cadenas con los nombres de los estados de habitación disponibles.
     */
    public List<String> obtenerNombresEstados() {
        return dao.obtenerNombresEstados();
    }

    /**
     * Retorna los ingresos reales agrupados por mes del año actual.
     *
     * @return mapa donde la clave es el nombre del mes y el valor es el total de ingresos.
     */
    public Map<String, Double> obtenerIngresosPorMes() {
        return dao.obtenerIngresosPorMes();
    }

    /**
     * Cambia el estado de una habitación, persiste el cambio en la base de datos
     * y notifica a todos los observadores registrados.
     * <p>
     * Si se proporciona un {@code observadorExtra}, se registra temporalmente antes de
     * la notificación y se elimina inmediatamente después.
     * </p>
     *
     * @param habitacion      la {@link Habitacion} cuyo estado se desea cambiar.
     * @param nuevoEstado     el nombre del nuevo estado a asignar.
     * @param observadorExtra observador adicional temporal, o {@code null} si no se requiere.
     */
    public void cambiarEstado(Habitacion habitacion, String nuevoEstado, HabitacionObserver observadorExtra) {
        String estadoAnterior = habitacion.getEstadoHabitacion() != null
                ? habitacion.getEstadoHabitacion().getNombre()
                : "desconocido";

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

    /**
     * Elimina una habitación de la base de datos según su identificador.
     *
     * @param id el identificador de la habitación a eliminar.
     */
    public void eliminar(int id) {
        dao.eliminar(id);
    }
}