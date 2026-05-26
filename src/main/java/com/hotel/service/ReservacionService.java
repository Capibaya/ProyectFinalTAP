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

public class ReservacionService {

    private static final double PRECIO_BASE_DEFAULT = 1000.0;

    private final ReservacionDAO dao = new ReservacionDAO();

    public List<Reservacion> obtenerTodas() {
        return dao.obtenerTodos();
    }

    public List<Reservacion> obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        return dao.obtenerPorFechas(desde, hasta);
    }

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

    public ReservacionBase aplicarServicios(double total, boolean desayuno, boolean parking, boolean spa) {
        ReservacionBase base = new ReservacionConcreto(total, "Hospedaje");
        if (desayuno) base = new DesayunoDecorator(base);
        if (parking)  base = new ParkingDecorator(base);
        if (spa)      base = new SpaDecorator(base);
        return base;
    }

    public void guardar(Reservacion r) {
        dao.guardar(r);
    }

    public void cancelar(int idReservacion) {
        Reservacion r = dao.buscarPorId(idReservacion);
        if (r != null) {
            EstadoReservacion cancelada = new EstadoReservacion(5, "cancelada");
            r.setEstadoReservacion(cancelada);
            dao.actualizar(r);
        }
    }
}