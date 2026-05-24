package com.hotel.service.strategy;

/**
 * Interfaz Strategy para calculo de tarifas.
 * Permite intercambiar el algoritmo de calculo sin modificar el servicio.
 */
public interface TarifaStrategy {
    double calcularTarifa(double precioBase, int noches);
}