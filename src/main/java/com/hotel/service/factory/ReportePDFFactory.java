package com.hotel.service.factory;

import java.util.List;

/**
 * Factory concreto para reportes en PDF.
 */
public class ReportePDFFactory implements ReporteFactory {

    @Override
    public void generarReporte(List<?> datos, String rutaDestino) {
        System.out.println("Generando reporte PDF en: " + rutaDestino);
        System.out.println("Total de registros: " + datos.size());
    }
}
