package com.hotel.service.factory;

import java.util.List;

/**
 * Factory concreto para reportes en Excel.
 */
public class ReporteExcelFactory implements ReporteFactory {

    @Override
    public void generarReporte(List<?> datos, String rutaDestino) {
        System.out.println("Generando reporte Excel en: " + rutaDestino);
        System.out.println("Total de registros: " + datos.size());
    }
}
