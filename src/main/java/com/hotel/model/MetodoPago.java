package com.hotel.model;

/**
 * Modelo que representa un método de pago aceptado en el hotel.
 * Cataloga las distintas formas de pago disponibles para liquidar
 * una reservación (p. ej. efectivo, tarjeta de crédito, transferencia bancaria).
 */
public class MetodoPago {

    /** Identificador único del método de pago en la base de datos. */
    private int idMetodoPago;

    /** Nombre descriptivo del método de pago (p. ej. "Efectivo", "Tarjeta de Crédito"). */
    private String nombre;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code MetodoPago}.
     */
    public MetodoPago() {}

    /**
     * Constructor con todos los campos del método de pago.
     *
     * @param idMetodoPago identificador único del método de pago
     * @param nombre       nombre descriptivo del método de pago
     */
    public MetodoPago(int idMetodoPago, String nombre) {
        this.idMetodoPago = idMetodoPago;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del método de pago.
     *
     * @return identificador del método de pago
     */
    public int getIdMetodoPago() { return idMetodoPago; }

    /**
     * Establece el identificador único del método de pago.
     *
     * @param idMetodoPago nuevo identificador del método de pago
     */
    public void setIdMetodoPago(int idMetodoPago) { this.idMetodoPago = idMetodoPago; }

    /**
     * Obtiene el nombre descriptivo del método de pago.
     *
     * @return nombre del método de pago
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre descriptivo del método de pago.
     *
     * @param nombre nuevo nombre del método de pago
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
}
