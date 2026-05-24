package com.hotel.controller;


import com.hotel.service.factory.ReporteExcelFactory;
import com.hotel.service.factory.ReporteFactory;
import com.hotel.service.factory.ReportePDFFactory;
import com.hotel.model.Reservacion;
import com.hotel.service.ReservacionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReporteController implements Initializable {

    @FXML private TableView<Reservacion> tablaReporte;
    @FXML private TableColumn<Reservacion, String>    colFecha;
    @FXML private TableColumn<Reservacion, String>    colHabitacion;
    @FXML private TableColumn<Reservacion, String>    colCliente;
    @FXML private TableColumn<Reservacion, Double>    colTotal;
    @FXML private TableColumn<Reservacion, String>    colEstado;
    @FXML private DatePicker       dpDesde;
    @FXML private DatePicker       dpHasta;
    @FXML private ComboBox<String> cmbTipoReporte;
    @FXML private Label            lblEstado;

    private final ReservacionService reservacionService = new ReservacionService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipoReporte.setItems(FXCollections.observableArrayList("PDF", "Excel"));
        cmbTipoReporte.setValue("PDF");
        cargarReporte();
    }

    private void cargarReporte() {
        List<Reservacion> lista = reservacionService.obtenerTodas();
        tablaReporte.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void handleGenerarReporte() {
        if (dpDesde.getValue() == null || dpHasta.getValue() == null) {
            lblEstado.setText("Selecciona el rango de fechas.");
            return;
        }

        LocalDate desde = dpDesde.getValue();
        LocalDate hasta = dpHasta.getValue();
        List<Reservacion> datos = reservacionService.obtenerPorFechas(desde, hasta);

        // Patron Factory — elige el tipo de reporte
        ReporteFactory factory = cmbTipoReporte.getValue().equals("PDF")
                ? new ReportePDFFactory()
                : new ReporteExcelFactory();

        String ruta = System.getProperty("user.home") + "/reporte_hotel." +
                cmbTipoReporte.getValue().toLowerCase();

        factory.generarReporte(datos, ruta);
        tablaReporte.setItems(FXCollections.observableArrayList(datos));
        lblEstado.setText("Reporte generado: " + ruta);
    }

    @FXML
    public void handleFiltrar() {
        if (dpDesde.getValue() == null || dpHasta.getValue() == null) {
            cargarReporte();
            return;
        }
        List<Reservacion> filtrado = reservacionService.obtenerPorFechas(
                dpDesde.getValue(), dpHasta.getValue()
        );
        tablaReporte.setItems(FXCollections.observableArrayList(filtrado));
        lblEstado.setText("Mostrando " + filtrado.size() + " registros.");
    }
}
