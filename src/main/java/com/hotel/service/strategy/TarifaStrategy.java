package com.hotel.service.strategy;

/**
 * Interfaz del patrón Strategy para el cálculo de tarifas de reservación.
 * <p>
 * Patrón de diseño: <b>Strategy</b> — define la familia de algoritmos de
 * cálculo de tarifas (temporada alta, temporada baja, días festivos) y
 * permite intercambiarlos de forma transparente sin modificar el servicio
 * que los utiliza.
 * </p>
 * Cada implementación encapsula una política de precios distinta.
 */
public interface TarifaStrategy {

    /**
     * Calcula el costo total de una reservación aplicando la política de
     * tarifas específica de la implementación.
     *
     * @param precioBase precio por noche de la habitación en pesos mexicanos
     * @param noches     número de noches de la estancia
     * @return costo total calculado según la estrategia de tarifa activa
     */
    double calcularTarifa(double precioBase, int noches);
}