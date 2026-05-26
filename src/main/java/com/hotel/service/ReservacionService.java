package com.hotel.service;

import com.hotel.dao.ReservacionDAO;
import com.hotel.model.EstadoReservacion;
import com.hotel.model.Habitacion;
import com.hotel.model.Reservacion;
import com.hotel.service.decorator.*;
import com.hotel.service.strategy.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Servicio de negocio para la gestión de reservaciones del hotel.
 * <p>
 * Centraliza la lógica de cálculo de tarifas, aplicación de servicios adicionales
 * y operaciones CRUD sobre reservaciones.
 * </p>
 * <p>
 * Patrón de diseño: <b>Strategy</b> — selecciona dinámicamente la implementación de
 * {@link TarifaStrategy} (temporada alta, baja o festivo) para calcular el costo total
 * según la temporada indicada.
 * </p>
 * <p>
 * Patrón de diseño: <b>Decorator</b> — permite añadir servicios adicionales
 * (desayuno, parking, spa) al precio base de la reservación mediante decoradores
 * que implementan {@link ReservacionBase}.
 * </p>
 */
public class ReservacionService {

    /**
     * Precio base por noche utilizado cuando la habitación o su tipo no están definidos.
     */
    private static final double PRECIO_BASE_DEFAULT = 1000.0;

    /**
     * Objeto de acceso a datos para operaciones CRUD sobre reservaciones.
     */
    private final ReservacionDAO dao = new ReservacionDAO();

    /**
     * Obtiene la lista completa de reservaciones registradas en el sistema.
     *
     * @return lista de {@link Reservacion}; puede estar vacía si no hay registros.
     */
    public List<Reservacion> obtenerTodas() {
        return dao.obtenerTodos();
    }

    /**
     * Obtiene las reservaciones cuyas fechas se encuentran dentro del rango indicado.
     *
     * @param desde fecha de inicio del rango de búsqueda (inclusiva).
     * @param hasta fecha de fin del rango de búsqueda (inclusiva).
     * @return lista de {@link Reservacion} que caen dentro del rango de fechas.
     */
    public List<Reservacion> obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        return dao.obtenerPorFechas(desde, hasta);
    }

    /**
     * Calcula el costo total de una reservación aplicando la estrategia de tarifa
     * correspondiente a la temporada indicada.
     * <p>
     * Utiliza el patrón <b>Strategy</b> para seleccionar entre {@link TarifaTemporadaAlta},
     * {@link TarifaFestivo} o {@link TarifaTemporadaBaja} según el valor de {@code temporada}.
     * </p>
     *
     * @param habitacion la {@link Habitacion} reservada; si es {@code null} o su tipo no tiene precio,
     *                   se usa {@link #PRECIO_BASE_DEFAULT}.
     * @param entrada    fecha de entrada (check-in).
     * @param salida     fecha de salida (check-out).
     * @param temporada  temporada de la reservación: {@code "alta"}, {@code "festivo"} o cualquier
     *                   otro valor para temporada baja.
     * @return el costo total calculado según la estrategia y el número de noches.
     */
    public double calcularTotal(Habitacion habitacion, LocalDate entrada, LocalDate salida, String temporada) {
        double precioBase = (habitacion != null && habitacion.getTipoHabitacion() != null)
                ? habitacion.getTipoHabitacion().getPrecioNoche()
                : PRECIO_BASE_DEFAULT;

        int noches = (int) ChronoUnit.DAYS.between(entrada, salida);
        if (noches <= 0) noches = 1;

        TarifaStrategy strategy = switch (temporada.toLowerCase()) {
            case "alta"    -> new TarifaTemporadaAlta();
            case "festivo" -> new TarifaFestivo();
            default        -> new TarifaTemporadaBaja();
        };

        return strategy.calcularTarifa(precioBase, noches);
    }

    /**
     * Aplica los servicios adicionales seleccionados al costo base de la reservación
     * usando el patrón <b>Decorator</b>.
     * <p>
     * Cada servicio habilitado envuelve la instancia anterior con un decorador que
     * incrementa el costo total y agrega el servicio a la descripción.
     * </p>
     *
     * @param total     el costo base calculado previamente (hospedaje puro).
     * @param desayuno  {@code true} si se debe incluir el servicio de desayuno.
     * @param parking   {@code true} si se debe incluir el servicio de estacionamiento.
     * @param spa       {@code true} si se debe incluir el servicio de spa.
     * @return un {@link ReservacionBase} decorado con los servicios seleccionados.
     */
    public ReservacionBase aplicarServicios(double total, boolean desayuno, boolean parking, boolean spa) {
        ReservacionBase base = new ReservacionConcreto(total, "Hospedaje");
        if (desayuno) base = new DesayunoDecorator(base);
        if (parking)  base = new ParkingDecorator(base);
        if (spa)      base = new SpaDecorator(base);
        return base;
    }

    /**
     * Persiste una nueva reservación en la base de datos.
     *
     * @param r el objeto {@link Reservacion} con todos los datos a guardar.
     */
    public void guardar(Reservacion r) {
        dao.guardar(r);
    }

    /**
     * Cancela una reservación existente cambiando su estado a {@code "cancelada"} (id=5).
     * <p>
     * Si la reservación no existe en la base de datos, el método no realiza ninguna acción.
     * </p>
     *
     * @param idReservacion el identificador de la reservación a cancelar.
     */
    public void cancelar(int idReservacion) {
        Reservacion r = dao.buscarPorId(idReservacion);
        if (r != null) {
            EstadoReservacion cancelada = new EstadoReservacion(5, "cancelada");
            r.setEstadoReservacion(cancelada);
            dao.actualizar(r);
        }
    }
}