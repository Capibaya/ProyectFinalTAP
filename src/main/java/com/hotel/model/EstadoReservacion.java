package com.hotel.model;

/**
 * Modelo que representa el estado de una reservación en el hotel.
 * Permite clasificar una {@link Reservacion} en estados como pendiente,
 * confirmada, cancelada o completada, facilitando su seguimiento y gestión.
 */
public class EstadoReservacion {

    /** Identificador único del estado de reservación en la base de datos. */
    private int idEstadoReservacion;

    /** Nombre descriptivo del estado (p. ej. "Pendiente", "Confirmada", "Cancelada"). */
    private String nombre;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code EstadoReservacion}.
     */
    public EstadoReservacion() {}

    /**
     * Constructor con todos los campos del estado de reservación.
     *
     * @param idEstadoReservacion identificador único del estado
     * @param nombre              nombre descriptivo del estado
     */
    public EstadoReservacion(int idEstadoReservacion, String nombre) {
        this.idEstadoReservacion = idEstadoReservacion;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del estado de reservación.
     *
     * @return identificador del estado de reservación
     */
    public int getIdEstadoReservacion() { return idEstadoReservacion; }

    /**
     * Establece el identificador único del estado de reservación.
     *
     * @param idEstadoReservacion nuevo identificador del estado de reservación
     */
    public void setIdEstadoReservacion(int idEstadoReservacion) { this.idEstadoReservacion = idEstadoReservacion; }

    /**
     * Obtiene el nombre descriptivo del estado de reservación.
     *
     * @return nombre del estado de reservación
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre descriptivo del estado de reservación.
     *
     * @param nombre nuevo nombre del estado de reservación
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
}