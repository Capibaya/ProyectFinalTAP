package com.hotel.service.strategy;

public class TarifaTemporadaAlta implements TarifaStrategy {

    private static final double INCREMENTO = 0.20;

    @Override
    public double calcularTarifa(double precioBase, int noches) {
        double subtotal = precioBase * noches;
        return subtotal + (subtotal * INCREMENTO);
    }
}