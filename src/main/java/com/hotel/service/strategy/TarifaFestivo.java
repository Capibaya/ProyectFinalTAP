package com.hotel.service.strategy;

public class TarifaFestivo implements TarifaStrategy {

    private static final double INCREMENTO = 0.35;

    @Override
    public double calcularTarifa(double precioBase, int noches) {
        double subtotal = precioBase * noches;
        return subtotal + (subtotal * INCREMENTO);
    }
}