package com.hotel.controller;

import com.hotel.model.UsuarioSistema;
import com.hotel.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controlador JavaFX para la pantalla de inicio de sesión del sistema hotelero.
 * <p>
 * Gestiona la autenticación del usuario mediante {@link AuthService} y,
 * en caso de éxito, carga la vista del Dashboard pasando el objeto
 * {@link UsuarioSistema} autenticado. También permite navegar hacia la
 * pantalla de recuperación de contraseña.
 * </p>
 */
public class LoginController {

    /** Campo de texto para ingresar el nombre de usuario. */
    @FXML private TextField     txtUsuario;

    /** Campo de contraseña (oculta caracteres) para ingresar la clave del usuario. */
    @FXML private PasswordField txtPassword;

    /** Etiqueta donde se muestran los mensajes de error de validación o autenticación. */
    @FXML private Label         lblError;

    /** Botón que dispara el proceso de inicio de sesión. */
    @FXML private Button        btnLogin;

    /** Botón que navega a la pantalla de recuperación de contraseña. */
    @FXML private Button        btnRecuperar;

    /** Servicio de autenticación utilizado para validar credenciales y buscar usuarios. */
    private final AuthService authService = new AuthService();

    /**
     * Maneja el evento de clic en el botón "Iniciar sesión".
     * <p>
     * Valida que los campos no estén vacíos, delega la autenticación a
     * {@link AuthService#login(String, String)} y, si las credenciales son
     * correctas, llama a {@link #abrirDashboard(UsuarioSistema)}.
     * En caso de error muestra un mensaje descriptivo mediante {@link #mostrarError(String)}.
     * </p>
     */
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

    /**
     * Maneja el evento de clic en el botón "Recuperar contraseña".
     * <p>
     * Carga la vista {@code RecuperarPassword.fxml} y reemplaza la escena
     * actual con ella, ajustando dimensiones y título de la ventana.
     * Si ocurre algún error durante la carga, lo muestra en {@link #lblError}.
     * </p>
     */
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

    /**
     * Muestra un mensaje de error en la etiqueta {@link #lblError} con estilo rojo.
     *
     * @param mensaje Texto descriptivo del error que se desea mostrar al usuario.
     */
    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setStyle("-fx-font-size: 11px; -fx-text-fill: red;");
    }

    /**
     * Carga la vista del Dashboard y la establece como escena principal,
     * inyectando el {@link UsuarioSistema} autenticado en el controlador
     * {@link DashboardController}.
     *
     * @param usuario El usuario del sistema que acaba de autenticarse correctamente.
     */
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