package com.hotel.service.factory;

import com.hotel.model.Reservacion;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReporteExcelFactory implements ReporteFactory {

    @Override
    public void generarReporte(List<?> datos, String rutaDestino) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaDestino))) {
            pw.println("ID,Cliente,Habitacion,Fecha Entrada,Fecha Salida,Total,Estado");
            for (Object obj : datos) {
                if (obj instanceof Reservacion r) {
                    String cliente = r.getCliente() != null
                            ? r.getCliente().getNombre() + " " + r.getCliente().getApellido() : "";
                    String hab    = r.getHabitacion() != null ? r.getHabitacion().getNumero() : "";
                    String estado = r.getEstadoReservacion() != null ? r.getEstadoReservacion().getNombre() : "";
                    pw.printf("%d,%s,%s,%s,%s,%.2f,%s%n",
                            r.getIdReservacion(), cliente, hab,
                            r.getFechaEntrada(), r.getFechaSalida(),
                            r.getTotal(), estado);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al generar reporte CSV: " + e.getMessage());
        }
    }
}