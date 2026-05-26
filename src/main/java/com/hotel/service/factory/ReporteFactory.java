package com.hotel.service.factory;

import java.util.List;

/**
 * Interfaz del patrón Factory para la generación de reportes.
 * <p>
 * Patrón de diseño: <b>Factory</b> — define el contrato para crear distintos
 * tipos de reportes (PDF, Excel, etc.) sin que el controlador deba conocer
 * los detalles de implementación de cada formato. Cada implementación
 * concreta encapsula la lógica de generación de un tipo de reporte específico.
 * </p>
 */
public interface ReporteFactory {

    /**
     * Genera un reporte con los datos proporcionados y lo almacena en la
     * ruta de destino indicada.
     *
     * @param datos        lista de objetos con la información a incluir en el reporte;
     *                     el tipo concreto depende de la implementación
     * @param rutaDestino  ruta completa del archivo de salida donde se guardará
     *                     el reporte generado
     */
    void generarReporte(List<?> datos, String rutaDestino);
}
