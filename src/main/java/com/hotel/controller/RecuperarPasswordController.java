package com.hotel.controller;

import com.hotel.model.UsuarioSistema;
import com.hotel.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador para la pantalla de recuperación de contraseña.
 * Flujo en 2 pasos:
 *   Paso 1 — El usuario ingresa su nombre de usuario para verificar que existe.
 *   Paso 2 — Si existe, puede establecer una nueva contraseña.
 */
public class RecuperarPasswordController {

    // ── Paso 1 ──────────────────────────────────────────────────────
    @FXML private VBox    pnlPaso1;
    @FXML private TextField txtUsuario;
    @FXML private Label   lblError1;
    @FXML private Button  btnVerificar;

    // ── Paso 2 ──────────────────────────────────────────────────────
    @FXML private VBox         pnlPaso2;
    @FXML private Label        lblBienvenida;
    @FXML private PasswordField txtNueva;
    @FXML private PasswordField txtConfirmar;
    @FXML private Label        lblError2;
    @FXML private Button       btnCambiar;

    // ── Paso visual (círculos numerados) ─────────────────────────────
    @FXML private Label lblPaso1Num;
    @FXML private Label lblPaso2Num;

    // ── Botón volver ─────────────────────────────────────────────────
    @FXML private Button btnVolver;

    // ── Estado interno ───────────────────────────────────────────────
    private final AuthService authService = new AuthService();
    private UsuarioSistema usuarioEncontrado;

    // ────────────────────────────────────────────────────────────────
    //  Inicialización
    // ────────────────────────────────────────────────────────────────


    // ────────────────────────────────────────────────────────────────
    //  Paso 1 — Verificar usuario
    // ────────────────────────────────────────────────────────────────

    @FXML
    public void handleVerificar() {
        String usuario = txtUsuario.getText().trim();

        if (usuario.isEmpty()) {
            mostrarError1("Por favor ingresa tu nombre de usuario.");
            return;
        }

        usuarioEncontrado = authService.buscarUsuario(usuario);

        if (usuarioEncontrado == null) {
            mostrarError1("No se encontró ningún usuario con ese nombre.");
            return;
        }

        // Pasar al paso 2
        irAPaso2();
    }

    // ────────────────────────────────────────────────────────────────
    //  Paso 2 — Cambiar contraseña
    // ────────────────────────────────────────────────────────────────

    @FXML
    public void handleCambiarPassword() {
        String nueva     = txtNueva.getText();
        String confirmar = txtConfirmar.getText();

        if (nueva.isEmpty() || confirmar.isEmpty()) {
            mostrarError2("Por favor completa todos los campos.");
            return;
        }

        if (nueva.length() < 6) {
            mostrarError2("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        if (!nueva.equals(confirmar)) {
            mostrarError2("Las contraseñas no coinciden.");
            return;
        }

        boolean exito = authService.cambiarPassword(
                usuarioEncontrado.getUsuario(), nueva);

        if (!exito) {
            mostrarError2("Error al guardar la contraseña. Inténtalo de nuevo.");
            return;
        }

        mostrarExito();
    }

    // ────────────────────────────────────────────────────────────────
    //  Volver al Login
    // ────────────────────────────────────────────────────────────────

    @FXML
    public void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Iniciar Sesión — Sistema Hotelero");
            stage.setWidth(480);
            stage.setHeight(520);
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ────────────────────────────────────────────────────────────────
    //  Helpers de UI
    // ────────────────────────────────────────────────────────────────

    /** Transición de Paso 1 → Paso 2 */
    private void irAPaso2() {
        // Ocultar paso 1
        pnlPaso1.setVisible(false);
        pnlPaso1.setManaged(false);

        // Actualizar indicador visual del paso
        lblPaso1Num.setStyle(
                "-fx-background-color: #43A047; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; " +
                "-fx-background-radius: 50; -fx-min-width: 32; " +
                "-fx-min-height: 32; -fx-alignment: center;");
        lblPaso2Num.setStyle(
                "-fx-background-color: #42A5F5; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; " +
                "-fx-background-radius: 50; -fx-min-width: 32; " +
                "-fx-min-height: 32; -fx-alignment: center;");

        // Personalizar bienvenida
        String nombreCompleto = usuarioEncontrado.getEmpleado().getNombre()
                + " " + usuarioEncontrado.getEmpleado().getApellido();
        lblBienvenida.setText("👤 " + nombreCompleto);

        // Mostrar paso 2
        pnlPaso2.setVisible(true);
        pnlPaso2.setManaged(true);
    }

    /** Muestra éxito y regresa al login automáticamente */
    private void mostrarExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contraseña actualizada");
        alert.setHeaderText("¡Contraseña cambiada con éxito!");
        alert.setContentText("Tu contraseña ha sido actualizada correctamente.\n"
                + "Ahora puedes iniciar sesión con tu nueva contraseña.");
        alert.showAndWait();
        handleVolver();
    }

    private void mostrarError1(String msg) {
        lblError1.setText(msg);
        lblError1.setStyle("-fx-font-size: 11px; -fx-text-fill: #C62828;");
    }

    private void mostrarError2(String msg) {
        lblError2.setText(msg);
        lblError2.setStyle("-fx-font-size: 11px; -fx-text-fill: #C62828;");
    }

}
