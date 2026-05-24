package com.hotel.service.factory;

import java.util.List;

/**
 * Interfaz Factory para generacion de reportes.
 * Permite crear distintos tipos de reporte sin cambiar el controlador.
 */
public interface ReporteFactory {
    void generarReporte(List<?> datos, String rutaDestino);
}
