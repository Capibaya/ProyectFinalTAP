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
 * Controlador JavaFX para la pantalla de recuperación de contraseña.
 * <p>
 * Implementa un flujo de dos pasos:
 * <ol>
 *   <li><b>Paso 1</b> — El usuario ingresa su nombre de usuario para verificar
 *       que existe en el sistema mediante {@link AuthService#buscarUsuario(String)}.</li>
 *   <li><b>Paso 2</b> — Si el usuario existe, puede ingresar y confirmar una nueva
 *       contraseña que se persiste mediante {@link AuthService#cambiarPassword(String, String)}.</li>
 * </ol>
 * </p>
 */
public class RecuperarPasswordController {

    // ── Paso 1 ──────────────────────────────────────────────────────

    /** Panel que contiene los controles del Paso 1 (verificación de usuario). */
    @FXML private VBox    pnlPaso1;

    /** Campo de texto donde el usuario ingresa su nombre de usuario para ser verificado. */
    @FXML private TextField txtUsuario;

    /** Etiqueta que muestra mensajes de error durante el Paso 1. */
    @FXML private Label   lblError1;

    /** Botón que dispara la verificación del nombre de usuario en el Paso 1. */
    @FXML private Button  btnVerificar;

    // ── Paso 2 ──────────────────────────────────────────────────────

    /** Panel que contiene los controles del Paso 2 (cambio de contraseña). */
    @FXML private VBox         pnlPaso2;

    /** Etiqueta que muestra el nombre completo del usuario encontrado en el Paso 1. */
    @FXML private Label        lblBienvenida;

    /** Campo de contraseña donde el usuario ingresa la nueva clave. */
    @FXML private PasswordField txtNueva;

    /** Campo de contraseña donde el usuario confirma la nueva clave. */
    @FXML private PasswordField txtConfirmar;

    /** Etiqueta que muestra mensajes de error durante el Paso 2. */
    @FXML private Label        lblError2;

    /** Botón que dispara el cambio de contraseña en el Paso 2. */
    @FXML private Button       btnCambiar;

    // ── Paso visual (círculos numerados) ─────────────────────────────

    /** Indicador visual circular del Paso 1 (cambia de color al completarse). */
    @FXML private Label lblPaso1Num;

    /** Indicador visual circular del Paso 2 (se activa al avanzar al Paso 2). */
    @FXML private Label lblPaso2Num;

    // ── Botón volver ─────────────────────────────────────────────────

    /** Botón que regresa a la pantalla de inicio de sesión. */
    @FXML private Button btnVolver;

    // ── Estado interno ───────────────────────────────────────────────

    /** Servicio de autenticación utilizado para buscar usuarios y cambiar contraseñas. */
    private final AuthService authService = new AuthService();

    /** Almacena el usuario encontrado en el Paso 1 para su uso durante el Paso 2. */
    private UsuarioSistema usuarioEncontrado;

    // ────────────────────────────────────────────────────────────────
    //  Paso 1 — Verificar usuario
    // ────────────────────────────────────────────────────────────────

    /**
     * Maneja el evento de clic en el botón "Verificar" (Paso 1).
     * <p>
     * Valida que el campo de usuario no esté vacío y delega la búsqueda a
     * {@link AuthService#buscarUsuario(String)}. Si el usuario es encontrado,
     * transiciona al Paso 2 mediante {@link #irAPaso2()}.
     * En caso contrario, muestra un mensaje de error en {@link #lblError1}.
     * </p>
     */
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

    /**
     * Maneja el evento de clic en el botón "Cambiar contraseña" (Paso 2).
     * <p>
     * Realiza las siguientes validaciones antes de persistir la nueva clave:
     * <ul>
     *   <li>Ningún campo puede estar vacío.</li>
     *   <li>La contraseña debe tener al menos 6 caracteres.</li>
     *   <li>Ambos campos deben coincidir.</li>
     * </ul>
     * Si todas las validaciones pasan, invoca
     * {@link AuthService#cambiarPassword(String, String)} y, en caso de éxito,
     * llama a {@link #mostrarExito()} para informar al usuario y regresar al login.
     * </p>
     */
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

    /**
     * Maneja el evento de clic en el botón "Volver".
     * <p>
     * Carga la vista {@code Login.fxml} y la establece como escena actual,
     * restaurando las dimensiones y el título propios de la pantalla de login.
     * Si ocurre algún error durante la carga, imprime la traza de excepción.
     * </p>
     */
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

    /**
     * Realiza la transición visual del Paso 1 al Paso 2.
     * <p>
     * Oculta el panel del Paso 1, marca el indicador circular del Paso 1 en verde
     * (completado) y activa el del Paso 2 en azul (en progreso). También
     * personaliza la etiqueta de bienvenida con el nombre completo del usuario.
     * </p>
     */
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

    /**
     * Muestra un diálogo de información indicando que la contraseña fue cambiada
     * con éxito y, al cerrarlo, redirige automáticamente al usuario a la pantalla
     * de inicio de sesión mediante {@link #handleVolver()}.
     */
    private void mostrarExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contraseña actualizada");
        alert.setHeaderText("¡Contraseña cambiada con éxito!");
        alert.setContentText("Tu contraseña ha sido actualizada correctamente.\n"
                + "Ahora puedes iniciar sesión con tu nueva contraseña.");
        alert.showAndWait();
        handleVolver();
    }

    /**
     * Muestra un mensaje de error en la etiqueta del Paso 1 ({@link #lblError1})
     * con estilo de texto en color rojo oscuro.
     *
     * @param msg Texto del mensaje de error a mostrar.
     */
    private void mostrarError1(String msg) {
        lblError1.setText(msg);
        lblError1.setStyle("-fx-font-size: 11px; -fx-text-fill: #C62828;");
    }

    /**
     * Muestra un mensaje de error en la etiqueta del Paso 2 ({@link #lblError2})
     * con estilo de texto en color rojo oscuro.
     *
     * @param msg Texto del mensaje de error a mostrar.
     */
    private void mostrarError2(String msg) {
        lblError2.setText(msg);
        lblError2.setStyle("-fx-font-size: 11px; -fx-text-fill: #C62828;");
    }

}
