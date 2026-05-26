package com.hotel.controller;

import com.hotel.model.Reservacion;
import com.hotel.model.UsuarioSistema;
import com.hotel.service.ReservacionService;
import com.hotel.service.factory.ReporteExcelFactory;
import com.hotel.service.factory.ReporteFactory;
import com.hotel.service.factory.ReportePDFFactory;
import javafx.beans.property.SimpleStringProperty;
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
    @FXML private TableColumn<Reservacion, String> colFecha;
    @FXML private TableColumn<Reservacion, String> colHabitacion;
    @FXML private TableColumn<Reservacion, String> colCliente;
    @FXML private TableColumn<Reservacion, Double> colTotal;
    @FXML private TableColumn<Reservacion, String> colEstado;
    @FXML private DatePicker dpDesde;
    @FXML private DatePicker dpHasta;
    @FXML private ComboBox<String> cmbTipoReporte;
    @FXML private Label lblEstado;

    private final ReservacionService reservacionService = new ReservacionService();
    private UsuarioSistema usuarioActual;

    public void setUsuario(UsuarioSistema usuario) {
        this.usuarioActual = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colFecha.setCellValueFactory(data -> {
            LocalDate f = data.getValue().getFechaEntrada();
            return new SimpleStringProperty(f != null ? f.toString() : "");
        });
        colHabitacion.setCellValueFactory(data -> {
            var h = data.getValue().getHabitacion();
            return new SimpleStringProperty(h != null ? h.getNumero() : "");
        });
        colCliente.setCellValueFactory(data -> {
            var c = data.getValue().getCliente();
            return new SimpleStringProperty(c != null ? c.getNombre() + " " + c.getApellido() : "");
        });
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colEstado.setCellValueFactory(data -> {
            var e = data.getValue().getEstadoReservacion();
            return new SimpleStringProperty(e != null ? e.getNombre() : "");
        });

        cmbTipoReporte.setItems(FXCollections.observableArrayList("PDF", "Excel (CSV)"));
        cmbTipoReporte.setValue("PDF");
        cargarReporte();
    }

    private void cargarReporte() {
        tablaReporte.setItems(FXCollections.observableArrayList(reservacionService.obtenerTodas()));
    }

    @FXML
    public void handleGenerarReporte() {
        if (dpDesde.getValue() == null || dpHasta.getValue() == null) {
            lblEstado.setText("Selecciona el rango de fechas.");
            lblEstado.setStyle("-fx-text-fill: red;");
            return;
        }

        LocalDate desde = dpDesde.getValue();
        LocalDate hasta = dpHasta.getValue();
        List<Reservacion> datos = reservacionService.obtenerPorFechas(desde, hasta);

        boolean esPDF = cmbTipoReporte.getValue().startsWith("PDF");
        ReporteFactory factory = esPDF ? new ReportePDFFactory() : new ReporteExcelFactory();
        // Bug corregido: extensión real .pdf en lugar de .txt
        String extension = esPDF ? "pdf" : "csv";
        String ruta = System.getProperty("user.home") + "/reporte_hotel_"
                + desde + "_" + hasta + "." + extension;

        factory.generarReporte(datos, ruta);
        tablaReporte.setItems(FXCollections.observableArrayList(datos));
        lblEstado.setText("✅ Reporte generado (" + datos.size() + " registros): " + ruta);
        lblEstado.setStyle("-fx-text-fill: green;");
    }

    @FXML
    public void handleFiltrar() {
        if (dpDesde.getValue() == null || dpHasta.getValue() == null) {
            cargarReporte();
            lblEstado.setText("");
            return;
        }
        List<Reservacion> filtrado = reservacionService.obtenerPorFechas(
                dpDesde.getValue(), dpHasta.getValue());
        tablaReporte.setItems(FXCollections.observableArrayList(filtrado));
        lblEstado.setText("Mostrando " + filtrado.size() + " registros.");
        lblEstado.setStyle("-fx-text-fill: #555;");
    }
}