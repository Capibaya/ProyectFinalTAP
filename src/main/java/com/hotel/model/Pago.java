package com.hotel.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa un pago realizado por una reservación en el hotel.
 * Asocia una {@link Reservacion} con el método de pago utilizado,
 * el monto pagado y la fecha en que se efectuó el pago.
 */
public class Pago {

    /** Identificador único del pago en la base de datos. */
    private int idPago;

    /** Reservación a la que corresponde este pago. */
    private Reservacion reservacion;

    /** Método de pago utilizado (efectivo, tarjeta, transferencia, etc.). */
    private MetodoPago metodoPago;

    /** Monto total pagado en esta transacción. */
    private double monto;

    /** Fecha y hora exacta en que se realizó el pago. */
    private LocalDateTime fechaPago;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code Pago}.
     */
    public Pago() {}

    /**
     * Constructor con todos los campos del pago.
     *
     * @param idPago      identificador único del pago
     * @param reservacion reservación asociada al pago
     * @param metodoPago  método de pago utilizado
     * @param monto       monto pagado
     * @param fechaPago   fecha y hora en que se realizó el pago
     */
    public Pago(int idPago, Reservacion reservacion, MetodoPago metodoPago,
                double monto, LocalDateTime fechaPago) {
        this.idPago = idPago;
        this.reservacion = reservacion;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    /**
     * Obtiene el identificador único del pago.
     *
     * @return identificador del pago
     */
    public int getIdPago() { return idPago; }

    /**
     * Establece el identificador único del pago.
     *
     * @param idPago nuevo identificador del pago
     */
    public void setIdPago(int idPago) { this.idPago = idPago; }

    /**
     * Obtiene la reservación asociada a este pago.
     *
     * @return reservación del pago
     */
    public Reservacion getReservacion() { return reservacion; }

    /**
     * Establece la reservación asociada a este pago.
     *
     * @param reservacion nueva reservación del pago
     */
    public void setReservacion(Reservacion reservacion) { this.reservacion = reservacion; }

    /**
     * Obtiene el método de pago utilizado.
     *
     * @return método de pago
     */
    public MetodoPago getMetodoPago() { return metodoPago; }

    /**
     * Establece el método de pago utilizado.
     *
     * @param metodoPago nuevo método de pago
     */
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    /**
     * Obtiene el monto pagado en esta transacción.
     *
     * @return monto del pago
     */
    public double getMonto() { return monto; }

    /**
     * Establece el monto pagado en esta transacción.
     *
     * @param monto nuevo monto del pago
     */
    public void setMonto(double monto) { this.monto = monto; }

    /**
     * Obtiene la fecha y hora en que se realizó el pago.
     *
     * @return fecha y hora del pago
     */
    public LocalDateTime getFechaPago() { return fechaPago; }

    /**
     * Establece la fecha y hora en que se realizó el pago.
     *
     * @param fechaPago nueva fecha y hora del pago
     */
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}
