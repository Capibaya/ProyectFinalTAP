package com.hotel.controller;

import com.hotel.service.HabitacionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label lblDisponibles;
    @FXML private Label lblOcupadas;
    @FXML private Label lblMantenimiento;
    @FXML private Label lblLimpieza;
    @FXML private PieChart pieOcupacion;
    @FXML private BarChart<String, Number> barIngresos;

    private final HabitacionService habitacionService = new HabitacionService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIndicadores();
        cargarGraficas();
    }

    private void cargarIndicadores() {
        lblDisponibles.setText(String.valueOf(habitacionService.contarPorEstado("disponible")));
        lblOcupadas.setText(String.valueOf(habitacionService.contarPorEstado("ocupada")));
        lblMantenimiento.setText(String.valueOf(habitacionService.contarPorEstado("mantenimiento")));
        lblLimpieza.setText(String.valueOf(habitacionService.contarPorEstado("limpieza")));
    }

    private void cargarGraficas() {
        // PieChart de ocupacion
        pieOcupacion.getData().addAll(
                new PieChart.Data("Disponibles",   habitacionService.contarPorEstado("disponible")),
                new PieChart.Data("Ocupadas",      habitacionService.contarPorEstado("ocupada")),
                new PieChart.Data("Mantenimiento", habitacionService.contarPorEstado("mantenimiento")),
                new PieChart.Data("Limpieza",      habitacionService.contarPorEstado("limpieza"))
        );

        // BarChart de ingresos (datos de ejemplo)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos 2025");
        series.getData().add(new XYChart.Data<>("Ene", 45000));
        series.getData().add(new XYChart.Data<>("Feb", 52000));
        series.getData().add(new XYChart.Data<>("Mar", 48000));
        series.getData().add(new XYChart.Data<>("Abr", 61000));
        series.getData().add(new XYChart.Data<>("May", 55000));
        series.getData().add(new XYChart.Data<>("Jun", 70000));
        barIngresos.getData().add(series);
    }

    @FXML public void irDashboard() {}

    @FXML
    public void irClientes() {
        cargarVista("/fxml/Huespedes.fxml", "Clientes");
    }

    @FXML
    public void irHabitaciones() {
        cargarVista("/fxml/Habitaciones.fxml", "Habitaciones");
    }

    @FXML
    public void irReservaciones() {
        cargarVista("/fxml/Reservaciones.fxml", "Reservaciones");
    }

    @FXML
    public void irReportes() {
        cargarVista("/fxml/Reportes.fxml", "Reportes");
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Stage stage = (Stage) pieOcupacion.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(titulo + " - Hotel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}