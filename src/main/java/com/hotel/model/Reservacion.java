package com.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo que representa una reservación de habitación en el hotel.
 * Relaciona un {@link Cliente} con una {@link Habitacion} para un periodo
 * determinado, e incluye información sobre el número de huéspedes,
 * el total a pagar y el estado actual de la reservación.
 */
public class Reservacion {

    /** Identificador único de la reservación en la base de datos. */
    private int idReservacion;

    /** Cliente que realizó la reservación. */
    private Cliente cliente;

    /** Habitación asignada a la reservación. */
    private Habitacion habitacion;

    /** Fecha y hora exacta en la que se realizó la reservación. */
    private LocalDateTime fechaReservacion;

    /** Fecha de entrada o check-in del cliente. */
    private LocalDate fechaEntrada;

    /** Fecha de salida o check-out del cliente. */
    private LocalDate fechaSalida;

    /** Número de huéspedes que ocuparán la habitación. */
    private int numeroHuespedes;

    /** Monto total calculado para la reservación. */
    private double total;

    /** Estado actual de la reservación (pendiente, confirmada, cancelada, etc.). */
    private EstadoReservacion estadoReservacion;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code Reservacion}.
     */
    public Reservacion() {}

    /**
     * Constructor con todos los campos de la reservación.
     *
     * @param idReservacion     identificador único de la reservación
     * @param cliente           cliente que realiza la reservación
     * @param habitacion        habitación reservada
     * @param fechaReservacion  fecha y hora en que se realizó la reservación
     * @param fechaEntrada      fecha de check-in
     * @param fechaSalida       fecha de check-out
     * @param numeroHuespedes   número de huéspedes
     * @param total             monto total de la reservación
     * @param estadoReservacion estado de la reservación
     */
    public Reservacion(int idReservacion, Cliente cliente, Habitacion habitacion,
                       LocalDateTime fechaReservacion, LocalDate fechaEntrada,
                       LocalDate fechaSalida, int numeroHuespedes,
                       double total, EstadoReservacion estadoReservacion) {
        this.idReservacion = idReservacion;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaReservacion = fechaReservacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numeroHuespedes = numeroHuespedes;
        this.total = total;
        this.estadoReservacion = estadoReservacion;
    }

    /**
     * Obtiene el identificador único de la reservación.
     *
     * @return identificador de la reservación
     */
    public int getIdReservacion() { return idReservacion; }

    /**
     * Establece el identificador único de la reservación.
     *
     * @param idReservacion nuevo identificador de la reservación
     */
    public void setIdReservacion(int idReservacion) { this.idReservacion = idReservacion; }

    /**
     * Obtiene el cliente asociado a la reservación.
     *
     * @return cliente de la reservación
     */
    public Cliente getCliente() { return cliente; }

    /**
     * Establece el cliente asociado a la reservación.
     *
     * @param cliente nuevo cliente de la reservación
     */
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    /**
     * Obtiene la habitación asignada en la reservación.
     *
     * @return habitación de la reservación
     */
    public Habitacion getHabitacion() { return habitacion; }

    /**
     * Establece la habitación asignada en la reservación.
     *
     * @param habitacion nueva habitación de la reservación
     */
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }

    /**
     * Obtiene la fecha y hora en que se realizó la reservación.
     *
     * @return fecha y hora de la reservación
     */
    public LocalDateTime getFechaReservacion() { return fechaReservacion; }

    /**
     * Establece la fecha y hora en que se realizó la reservación.
     *
     * @param fechaReservacion nueva fecha y hora de la reservación
     */
    public void setFechaReservacion(LocalDateTime fechaReservacion) { this.fechaReservacion = fechaReservacion; }

    /**
     * Obtiene la fecha de entrada (check-in) del cliente.
     *
     * @return fecha de entrada
     */
    public LocalDate getFechaEntrada() { return fechaEntrada; }

    /**
     * Establece la fecha de entrada (check-in) del cliente.
     *
     * @param fechaEntrada nueva fecha de entrada
     */
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    /**
     * Obtiene la fecha de salida (check-out) del cliente.
     *
     * @return fecha de salida
     */
    public LocalDate getFechaSalida() { return fechaSalida; }

    /**
     * Establece la fecha de salida (check-out) del cliente.
     *
     * @param fechaSalida nueva fecha de salida
     */
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    /**
     * Obtiene el número de huéspedes de la reservación.
     *
     * @return número de huéspedes
     */
    public int getNumeroHuespedes() { return numeroHuespedes; }

    /**
     * Establece el número de huéspedes de la reservación.
     *
     * @param numeroHuespedes nuevo número de huéspedes
     */
    public void setNumeroHuespedes(int numeroHuespedes) { this.numeroHuespedes = numeroHuespedes; }

    /**
     * Obtiene el monto total de la reservación.
     *
     * @return monto total
     */
    public double getTotal() { return total; }

    /**
     * Establece el monto total de la reservación.
     *
     * @param total nuevo monto total
     */
    public void setTotal(double total) { this.total = total; }

    /**
     * Obtiene el estado actual de la reservación.
     *
     * @return estado de la reservación
     */
    public EstadoReservacion getEstadoReservacion() { return estadoReservacion; }

    /**
     * Establece el estado actual de la reservación.
     *
     * @param estadoReservacion nuevo estado de la reservación
     */
    public void setEstadoReservacion(EstadoReservacion estadoReservacion) { this.estadoReservacion = estadoReservacion; }
}