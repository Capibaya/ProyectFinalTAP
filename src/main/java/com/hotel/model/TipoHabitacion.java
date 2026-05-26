package com.hotel.model;

/**
 * Modelo que representa el tipo o categoría de una habitación del hotel.
 * Define la capacidad máxima de huéspedes y el precio por noche
 * asociado a esa categoría (p. ej. sencilla, doble, suite).
 */
public class TipoHabitacion {

    /** Identificador único del tipo de habitación en la base de datos. */
    private int idTipo;

    /** Nombre descriptivo del tipo de habitación (p. ej. "Sencilla", "Suite"). */
    private String nombre;

    /** Capacidad máxima de huéspedes permitida para este tipo de habitación. */
    private int capacidad;

    /** Precio por noche en moneda local para este tipo de habitación. */
    private double precioNoche;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code TipoHabitacion}.
     */
    public TipoHabitacion() {}

    /**
     * Constructor con todos los campos del tipo de habitación.
     *
     * @param idTipo      identificador único del tipo de habitación
     * @param nombre      nombre del tipo de habitación
     * @param capacidad   capacidad máxima de huéspedes
     * @param precioNoche precio por noche del tipo de habitación
     */
    public TipoHabitacion(int idTipo, String nombre, int capacidad, double precioNoche) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
    }

    /**
     * Obtiene el identificador único del tipo de habitación.
     *
     * @return identificador del tipo de habitación
     */
    public int getIdTipo() { return idTipo; }

    /**
     * Establece el identificador único del tipo de habitación.
     *
     * @param idTipo nuevo identificador del tipo de habitación
     */
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    /**
     * Obtiene el nombre del tipo de habitación.
     *
     * @return nombre del tipo de habitación
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del tipo de habitación.
     *
     * @param nombre nuevo nombre del tipo de habitación
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene la capacidad máxima de huéspedes de este tipo.
     *
     * @return capacidad máxima de huéspedes
     */
    public int getCapacidad() { return capacidad; }

    /**
     * Establece la capacidad máxima de huéspedes de este tipo.
     *
     * @param capacidad nueva capacidad máxima de huéspedes
     */
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    /**
     * Obtiene el precio por noche de este tipo de habitación.
     *
     * @return precio por noche
     */
    public double getPrecioNoche() { return precioNoche; }

    /**
     * Establece el precio por noche de este tipo de habitación.
     *
     * @param precioNoche nuevo precio por noche
     */
    public void setPrecioNoche(double precioNoche) { this.precioNoche = precioNoche; }
}
