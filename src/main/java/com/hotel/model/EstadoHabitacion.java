package com.hotel.model;

/**
 * Modelo que representa el estado de disponibilidad de una habitación del hotel.
 * Permite clasificar una {@link Habitacion} en estados como disponible,
 * ocupada, en mantenimiento, entre otros.
 */
public class EstadoHabitacion {

    /** Identificador único del estado de habitación en la base de datos. */
    private int idEstadoHabitacion;

    /** Nombre descriptivo del estado (p. ej. "Disponible", "Ocupada", "Mantenimiento"). */
    private String nombre;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code EstadoHabitacion}.
     */
    public EstadoHabitacion() {}

    /**
     * Constructor con todos los campos del estado de habitación.
     *
     * @param idEstadoHabitacion identificador único del estado
     * @param nombre             nombre descriptivo del estado
     */
    public EstadoHabitacion(int idEstadoHabitacion, String nombre) {
        this.idEstadoHabitacion = idEstadoHabitacion;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del estado de habitación.
     *
     * @return identificador del estado de habitación
     */
    public int getIdEstadoHabitacion() { return idEstadoHabitacion; }

    /**
     * Establece el identificador único del estado de habitación.
     *
     * @param idEstadoHabitacion nuevo identificador del estado de habitación
     */
    public void setIdEstadoHabitacion(int idEstadoHabitacion) { this.idEstadoHabitacion = idEstadoHabitacion; }

    /**
     * Obtiene el nombre descriptivo del estado de habitación.
     *
     * @return nombre del estado de habitación
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre descriptivo del estado de habitación.
     *
     * @param nombre nuevo nombre del estado de habitación
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
}
