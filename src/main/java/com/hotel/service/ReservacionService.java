package com.hotel.service;

import com.hotel.dao.ReservacionDAO;
import com.hotel.decorator.*;
import com.hotel.model.Habitacion;
import com.hotel.model.Reservacion;
import com.hotel.strategy.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
/**
 * Servicio de reservaciones.
 * Usa Strategy para calcular tarifas y Decorator para agregar servicios.
 */
public class ReservacionService {
    private final ReservacionDAO reservacionDAO = new ReservacionDAO();
    /**
     * Obtiene todas las reservaciones.
     */
    public List<Reservacion> obtenerTodas() {
        return reservacionDAO.obtenerTodos();
    }
    /**
     * Obtiene reservaciones en un rango de fechas.
     */
    public List<Reservacion> obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        return reservacionDAO.obtenerPorFechas(desde, hasta);
    }
    /**
     * Calcula el total usando Strategy segun la temporada.
     * @param habitacion Habitacion reservada
     * @param fechaEntrada Fecha de entrada
     * @param fechaSalida Fecha de salida
     * @param temporada "baja", "alta" o "festivo"
     * @return Total calculado
     */
    public double calcularTotal(Habitacion habitacion, LocalDate fechaEntrada,
                                LocalDate fechaSalida, String temporada) {
        int noches = (int) ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        double precioBase = habitacion.getTipoHabitacion().getPrecioNoche();
        // Patron Strategy — selecciona algoritmo segun temporada
        TarifaStrategy estrategia = switch (temporada.toLowerCase()) {
            case "alta" -> new TarifaTemporadaAlta();
            case "festivo" -> new TarifaFestivo();
            default -> new TarifaTemporadaBaja();
        };
        return estrategia.calcularTarifa(precioBase, noches);
    }
    /**
     * Aplica servicios adicionales usando Decorator.
     * @param costoBase Costo calculado por Strategy
     * @param conDesayuno Si incluye desayuno
     * @param conParking Si incluye estacionamiento
     * @param conSpa Si incluye spa
     * @return ReservacionBase decorada con los servicios seleccionados
     */
    public ReservacionBase aplicarServicios(double costoBase,
                                            boolean conDesayuno,
                                            boolean conParking,
                                            boolean conSpa) {
        // Costo base como implementacion anonima
        ReservacionBase reservacion = new ReservacionBase() {
            @Override public double calcularCosto() { return costoBase; }
            @Override public String getDescripcion() { return "Hospedaje"; }
        };
        // Patron Decorator — agrega servicios dinamicamente
        if (conDesayuno) reservacion = new DesayunoDecorator(reservacion);
        if (conParking) reservacion = new ParkingDecorator(reservacion);
        if (conSpa) reservacion = new SpaDecorator(reservacion);
        return reservacion;
    }
    /**
     * Guarda una nueva reservacion en BD.
     */
    public void guardar(Reservacion reservacion) {
        reservacionDAO.guardar(reservacion);
    }
    /**
     * Cancela una reservacion cambiando su estado.
     */
    public void cancelar(int idReservacion) {
        Reservacion r = reservacionDAO.buscarPorId(idReservacion);
        if (r != null) {
            r.getEstadoReservacion().setNombre("cancelada");
            r.getEstadoReservacion().setIdEstadoReservacion(5);
            reservacionDAO.actualizar(r);
        }
    }
    /**
     * Actualiza una reservacion existente.
     */
    public void actualizar(Reservacion reservacion) {
        reservacionDAO.actualizar(reservacion);
    }
}
