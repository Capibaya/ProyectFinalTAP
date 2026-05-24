package com.hotel.controller;

import com.hotel.model.UsuarioSistema;
import com.hotel.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    private final AuthService authService = new AuthService();

    @FXML
    public void handleLogin() {
        String usuario   = txtUsuario.getText().trim();
        String password  = txtPassword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor completa todos los campos.");
            return;
        }

        UsuarioSistema u = authService.login(usuario, password);

        if (u != null) {
            lblError.setText("");
            abrirDashboard(u);
        } else {
            lblError.setText("Usuario o contraseña incorrectos.");
        }
    }

    private void abrirDashboard(UsuarioSistema usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Dashboard - Hotel");
        } catch (Exception e) {
            lblError.setText("Error al cargar el dashboard.");
            e.printStackTrace();
        }
    }
}
