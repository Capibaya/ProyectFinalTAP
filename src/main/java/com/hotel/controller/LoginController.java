package com.hotel.controller;

import com.hotel.model.UsuarioSistema;
import com.hotel.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label         lblError;
    @FXML private Button        btnLogin;
    @FXML private Button        btnRecuperar;

    private final AuthService authService = new AuthService();

    @FXML
    public void handleLogin() {
        String usuario  = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError("Por favor completa todos los campos.");
            return;
        }

        UsuarioSistema u = authService.login(usuario, password);

        if (u == null) {
            mostrarError("Usuario o contraseña incorrectos.");
            return;
        }

        abrirDashboard(u);
    }

    @FXML
    public void handleRecuperar() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/RecuperarPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Recuperar Contraseña — Sistema Hotelero");
            stage.setWidth(480);
            stage.setHeight(620);
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (Exception e) {
            mostrarError("Error al abrir recuperación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setStyle("-fx-font-size: 11px; -fx-text-fill: red;");
    }

    private void abrirDashboard(UsuarioSistema usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Dashboard.fxml"));
            Parent root = loader.load();
            DashboardController dc = loader.getController();
            dc.setUsuario(usuario);
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Dashboard — " + usuario.getEmpleado().getNombre()
                    + " " + usuario.getEmpleado().getApellido());
            stage.setWidth(1100);
            stage.setHeight(720);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setResizable(true);
            stage.centerOnScreen();
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}