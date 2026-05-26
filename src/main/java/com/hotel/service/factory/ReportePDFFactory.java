package com.hotel.service.factory;

import com.hotel.model.Reservacion;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportePDFFactory implements ReporteFactory {

    private static final DeviceRgb COLOR_ENCABEZADO  = new DeviceRgb(38, 50, 56);   // #263238
    private static final DeviceRgb COLOR_FILA_PAR    = new DeviceRgb(236, 239, 241); // #ECEFF1
    private static final DeviceRgb COLOR_TOTAL_FILA  = new DeviceRgb(76, 175, 80);   // #4CAF50

    @Override
    public void generarReporte(List<?> datos, String rutaDestino) {
        try {
            PdfFont fontBold    = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontNormal  = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            PdfWriter   writer   = new PdfWriter(rutaDestino);
            PdfDocument pdfDoc   = new PdfDocument(writer);
            Document    document = new Document(pdfDoc);
            document.setMargins(36, 36, 36, 36);

            // ── Encabezado ────────────────────────────────────────────────
            Paragraph titulo = new Paragraph("HOTEL — REPORTE DE RESERVACIONES")
                    .setFont(fontBold).setFontSize(18)
                    .setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(COLOR_ENCABEZADO)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(14);
            document.add(titulo);

            String fechaGen = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            document.add(new Paragraph("Generado: " + fechaGen + "   |   Total de registros: " + datos.size())
                    .setFont(fontNormal).setFontSize(9)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginBottom(12));

            // ── Tabla ─────────────────────────────────────────────────────
            float[] anchos = {40f, 120f, 80f, 130f, 90f, 90f, 80f, 90f};
            Table tabla = new Table(UnitValue.createPercentArray(anchos))
                    .setWidth(UnitValue.createPercentValue(100));

            // Encabezados de columna
            String[] cols = {"ID", "Cliente", "Hab.", "Fecha entrada", "Fecha salida", "Huéspedes", "Total ($)", "Estado"};
            for (String col : cols) {
                tabla.addHeaderCell(
                        new Cell().add(new Paragraph(col).setFont(fontBold).setFontSize(9)
                                .setFontColor(ColorConstants.WHITE))
                                .setBackgroundColor(COLOR_ENCABEZADO)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setPadding(6)
                );
            }

            // Filas de datos
            double totalGeneral = 0;
            int rowIndex = 0;
            for (Object obj : datos) {
                if (!(obj instanceof Reservacion r)) continue;

                String cliente = r.getCliente() != null
                        ? r.getCliente().getNombre() + " " + r.getCliente().getApellido() : "—";
                String hab     = r.getHabitacion()  != null ? r.getHabitacion().getNumero()  : "—";
                String estado  = r.getEstadoReservacion() != null ? r.getEstadoReservacion().getNombre() : "—";
                String entrada = r.getFechaEntrada() != null ? r.getFechaEntrada().toString() : "—";
                String salida  = r.getFechaSalida()  != null ? r.getFechaSalida().toString()  : "—";
                double total   = r.getTotal();
                totalGeneral  += total;

                DeviceRgb bgColor = (rowIndex % 2 == 0) ? null : COLOR_FILA_PAR;
                rowIndex++;

                String[] celdas = {
                        String.valueOf(r.getIdReservacion()),
                        cliente, hab, entrada, salida,
                        String.valueOf(r.getNumeroHuespedes()),
                        String.format("%.2f", total),
                        estado
                };

                for (int i = 0; i < celdas.length; i++) {
                    Cell cell = new Cell()
                            .add(new Paragraph(celdas[i]).setFont(fontNormal).setFontSize(9))
                            .setPadding(5);
                    if (bgColor != null) cell.setBackgroundColor(bgColor);
                    if (i == 6) cell.setTextAlignment(TextAlignment.RIGHT);
                    tabla.addCell(cell);
                }
            }

            // Fila de total general
            tabla.addCell(new Cell(1, 7)
                    .add(new Paragraph("TOTAL GENERAL:").setFont(fontBold).setFontSize(10)
                            .setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(COLOR_TOTAL_FILA)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setPadding(6));
            tabla.addCell(new Cell()
                    .add(new Paragraph(String.format("$%.2f", totalGeneral)).setFont(fontBold).setFontSize(10)
                            .setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(COLOR_TOTAL_FILA)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setPadding(6));

            document.add(tabla);

            // ── Pie de página ─────────────────────────────────────────────
            document.add(new Paragraph("\nDocumento generado automáticamente por el Sistema de Gestión Hotelera.")
                    .setFont(fontNormal).setFontSize(8)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(16));

            document.close();

        } catch (IOException e) {
            System.err.println("Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}