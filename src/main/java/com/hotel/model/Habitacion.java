package com.hotel.model;

/**
 * Modelo que representa una habitación del hotel.
 * Contiene la información física de la habitación, su tipo
 * y su estado de disponibilidad actual.
 */
public class Habitacion {

    /** Identificador único de la habitación en la base de datos. */
    private int idHabitacion;

    /** Número o identificador visible de la habitación (p. ej. "101", "202B"). */
    private String numero;

    /** Piso del hotel en el que se encuentra la habitación. */
    private int piso;

    /** Descripción general de las características de la habitación. */
    private String descripcion;

    /** Tipo de habitación que define la categoría y precio por noche. */
    private TipoHabitacion tipoHabitacion;

    /** Estado actual de la habitación (disponible, ocupada, en mantenimiento, etc.). */
    private EstadoHabitacion estadoHabitacion;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code Habitacion}.
     */
    public Habitacion() {}

    /**
     * Constructor con todos los campos de la habitación.
     *
     * @param idHabitacion     identificador único de la habitación
     * @param numero           número o código de la habitación
     * @param piso             piso en el que se ubica la habitación
     * @param descripcion      descripción de la habitación
     * @param tipoHabitacion   tipo de habitación asignado
     * @param estadoHabitacion estado actual de la habitación
     */
    public Habitacion(int idHabitacion, String numero, int piso, String descripcion,
                      TipoHabitacion tipoHabitacion, EstadoHabitacion estadoHabitacion) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.piso = piso;
        this.descripcion = descripcion;
        this.tipoHabitacion = tipoHabitacion;
        this.estadoHabitacion = estadoHabitacion;
    }

    /**
     * Obtiene el identificador único de la habitación.
     *
     * @return identificador de la habitación
     */
    public int getIdHabitacion() { return idHabitacion; }

    /**
     * Establece el identificador único de la habitación.
     *
     * @param idHabitacion nuevo identificador de la habitación
     */
    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }

    /**
     * Obtiene el número o código visible de la habitación.
     *
     * @return número de la habitación
     */
    public String getNumero() { return numero; }

    /**
     * Establece el número o código visible de la habitación.
     *
     * @param numero nuevo número de la habitación
     */
    public void setNumero(String numero) { this.numero = numero; }

    /**
     * Obtiene el piso en el que se ubica la habitación.
     *
     * @return piso de la habitación
     */
    public int getPiso() { return piso; }

    /**
     * Establece el piso en el que se ubica la habitación.
     *
     * @param piso nuevo piso de la habitación
     */
    public void setPiso(int piso) { this.piso = piso; }

    /**
     * Obtiene la descripción de la habitación.
     *
     * @return descripción de la habitación
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Establece la descripción de la habitación.
     *
     * @param descripcion nueva descripción de la habitación
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Obtiene el tipo de habitación.
     *
     * @return tipo de habitación
     */
    public TipoHabitacion getTipoHabitacion() { return tipoHabitacion; }

    /**
     * Establece el tipo de habitación.
     *
     * @param tipoHabitacion nuevo tipo de habitación
     */
    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) { this.tipoHabitacion = tipoHabitacion; }

    /**
     * Obtiene el estado actual de la habitación.
     *
     * @return estado de la habitación
     */
    public EstadoHabitacion getEstadoHabitacion() { return estadoHabitacion; }

    /**
     * Establece el estado actual de la habitación.
     *
     * @param estadoHabitacion nuevo estado de la habitación
     */
    public void setEstadoHabitacion(EstadoHabitacion estadoHabitacion) { this.estadoHabitacion = estadoHabitacion; }
}
