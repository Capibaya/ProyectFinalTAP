package com.hotel.controller;

import com.hotel.model.Habitacion;
import com.hotel.service.HabitacionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HabitacionController implements Initializable {

    @FXML private TableView<Habitacion> tablaHabitaciones;
    @FXML private TableColumn<Habitacion, String>  colNumero;
    @FXML private TableColumn<Habitacion, Integer> colPiso;
    @FXML private TableColumn<Habitacion, String>  colTipo;
    @FXML private TableColumn<Habitacion, Double>  colPrecio;
    @FXML private TableColumn<Habitacion, String>  colEstado;
    @FXML private ComboBox<String> cmbFiltroEstado;

    private final HabitacionService habitacionService = new HabitacionService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colPiso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        cmbFiltroEstado.setItems(FXCollections.observableArrayList(
                "Todos", "disponible", "ocupada", "mantenimiento", "limpieza"
        ));
        cmbFiltroEstado.setValue("Todos");
        cargarHabitaciones();
    }

    private void cargarHabitaciones() {
        List<Habitacion> lista = habitacionService.obtenerTodas();
        tablaHabitaciones.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void handleFiltrar() {
        String filtro = cmbFiltroEstado.getValue();
        List<Habitacion> lista = filtro.equals("Todos")
                ? habitacionService.obtenerTodas()
                : habitacionService.obtenerPorEstado(filtro);
        tablaHabitaciones.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void handleNueva() {
        mostrarInfo("Formulario de nueva habitacion en construccion.");
    }

    @FXML
    public void handleCambiarEstado() {
        Habitacion seleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una habitacion.");
            return;
        }
        // Usar Observer para notificar el cambio
        habitacionService.cambiarEstado(seleccionada, "limpieza", null);
        cargarHabitaciones();
        mostrarInfo("Estado cambiado a: limpieza");
    }

    @FXML
    public void handleEliminar() {
        Habitacion seleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una habitacion.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar habitacion " + seleccionada.getNumero() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                habitacionService.eliminar(seleccionada.getIdHabitacion());
                cargarHabitaciones();
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
