package com.hotel.service.strategy;

/**
 * Estrategia de tarifa para temporada baja.
 * <p>
 * Patrón de diseño: <b>Strategy</b> — implementación concreta de
 * {@link TarifaStrategy} que aplica un descuento del 10 % sobre el
 * subtotal base (precio por noche × número de noches).
 * </p>
 */
public class TarifaTemporadaBaja implements TarifaStrategy {

    /**
     * Porcentaje de descuento aplicado sobre el subtotal en temporada baja (10 %).
     */
    private static final double DESCUENTO = 0.10;

    /**
     * Calcula el costo total de la estancia aplicando el descuento del 10 %
     * correspondiente a temporada baja.
     *
     * @param precioBase precio por noche de la habitación en pesos mexicanos
     * @param noches     número de noches de la estancia
     * @return subtotal menos el 10 % de descuento por temporada baja
     */
    @Override
    public double calcularTarifa(double precioBase, int noches) {
        double subtotal = precioBase * noches;
        return subtotal - (subtotal * DESCUENTO);
    }
}
