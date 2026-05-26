package com.hotel.service.strategy;

/**
 * Estrategia de tarifa para días festivos.
 * <p>
 * Patrón de diseño: <b>Strategy</b> — implementación concreta de
 * {@link TarifaStrategy} que aplica un incremento del 35 % sobre el
 * subtotal base (precio por noche × número de noches) durante períodos
 * vacacionales o días festivos especiales.
 * </p>
 */
public class TarifaFestivo implements TarifaStrategy {

    /**
     * Porcentaje de incremento aplicado sobre el subtotal en días festivos (35 %).
     */
    private static final double INCREMENTO = 0.35;

    /**
     * Calcula el costo total de la estancia aplicando el incremento del 35 %
     * correspondiente a días festivos.
     *
     * @param precioBase precio por noche de la habitación en pesos mexicanos
     * @param noches     número de noches de la estancia
     * @return subtotal más el 35 % de incremento por día festivo
     */
    @Override
    public double calcularTarifa(double precioBase, int noches) {
        double subtotal = precioBase * noches;
        return subtotal + (subtotal * INCREMENTO);
    }
}