package com.hotel.controller;

import com.hotel.model.Reservacion;
import com.hotel.service.ReservacionService;
import com.hotel.service.decorator.ReservacionBase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReservacionController implements Initializable {

    @FXML private TableView<Reservacion> tablaReservaciones;
    @FXML private TableColumn<Reservacion, String>    colCliente;
    @FXML private TableColumn<Reservacion, String>    colHabitacion;
    @FXML private TableColumn<Reservacion, LocalDate> colEntrada;
    @FXML private TableColumn<Reservacion, LocalDate> colSalida;
    @FXML private TableColumn<Reservacion, Double>    colTotal;
    @FXML private TableColumn<Reservacion, String>    colEstado;
    @FXML private DatePicker   dpEntrada;
    @FXML private DatePicker   dpSalida;
    @FXML private ComboBox<String> cmbTemporada;
    @FXML private Label        lblTotal;

    private final ReservacionService reservacionService = new ReservacionService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTemporada.setItems(FXCollections.observableArrayList(
                "baja", "alta", "festivo"
        ));
        cmbTemporada.setValue("baja");
        cargarReservaciones();
    }

    private void cargarReservaciones() {
        List<Reservacion> lista = reservacionService.obtenerTodas();
        tablaReservaciones.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void handleCalcular() {
        LocalDate entrada = dpEntrada.getValue();
        LocalDate salida  = dpSalida.getValue();

        if (entrada == null || salida == null) {
            lblTotal.setText("Selecciona las fechas.");
            return;
        }
        if (!salida.isAfter(entrada)) {
            lblTotal.setText("La salida debe ser posterior a la entrada.");
            return;
        }

        // Precio base de ejemplo (vendria de la habitacion seleccionada)
        double precioBase = 1000.0;
        String temporada  = cmbTemporada.getValue();

        // Usar Strategy para calcular tarifa
        double total = reservacionService.calcularTotal(
                null, entrada, salida, temporada
        );

        // Usar Decorator para agregar servicios
        ReservacionBase conServicios = reservacionService.aplicarServicios(
                total, true, true, false
        );

        lblTotal.setText(String.format("Total: $%.2f | %s",
                conServicios.calcularCosto(),
                conServicios.getDescripcion()));
    }

    @FXML
    public void handleNueva() {
        mostrarInfo("Formulario de nueva reservacion en construccion.");
    }

    @FXML
    public void handleCancelar() {
        Reservacion seleccionada = tablaReservaciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una reservacion para cancelar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Cancelar la reservacion #" + seleccionada.getIdReservacion() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                reservacionService.cancelar(seleccionada.getIdReservacion());
                cargarReservaciones();
                mostrarInfo("Reservacion cancelada.");
            }
        });
    }

    private void mostrarAlerta(String mensaje) {
        new Alert(Alert.AlertType.WARNING, mensaje).showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}