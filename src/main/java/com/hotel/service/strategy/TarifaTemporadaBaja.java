package com.hotel.service.strategy;

public class TarifaTemporadaBaja implements TarifaStrategy {

    private static final double DESCUENTO = 0.10;

    @Override
    public double calcularTarifa(double precioBase, int noches) {
        double subtotal = precioBase * noches;
        return subtotal - (subtotal * DESCUENTO);
    }
}
