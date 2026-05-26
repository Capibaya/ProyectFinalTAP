package com.hotel.service.strategy;

/**
 * Estrategia de tarifa para temporada alta.
 * <p>
 * Patrón de diseño: <b>Strategy</b> — implementación concreta de
 * {@link TarifaStrategy} que aplica un incremento del 20 % sobre el
 * subtotal base (precio por noche × número de noches).
 * </p>
 */
public class TarifaTemporadaAlta implements TarifaStrategy {

    /**
     * Porcentaje de incremento aplicado sobre el subtotal en temporada alta (20 %).
     */
    private static final double INCREMENTO = 0.20;

    /**
     * Calcula el costo total de la estancia aplicando el incremento del 20 %
     * correspondiente a temporada alta.
     *
     * @param precioBase precio por noche de la habitación en pesos mexicanos
     * @param noches     número de noches de la estancia
     * @return subtotal más el 20 % de incremento por temporada alta
     */
    @Override
    public double calcularTarifa(double precioBase, int noches) {
        double subtotal = precioBase * noches;
        return subtotal + (subtotal * INCREMENTO);
    }
}