package com.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación JavaFX del sistema de reservaciones de hotel.
 * <p>
 * Extiende {@link javafx.application.Application} y actúa como punto de entrada
 * de la interfaz gráfica. Carga la vista de inicio de sesión (Login.fxml) al arrancar.
 * </p>
 */
public class MainApp extends Application {

    /**
     * Método de inicio de la aplicación JavaFX.
     * <p>
     * Carga el archivo FXML de la pantalla de inicio de sesión, aplica la hoja
     * de estilos CSS y configura el escenario principal ({@link Stage}).
     * </p>
     *
     * @param stage El escenario principal proporcionado por el framework JavaFX.
     * @throws Exception Si ocurre un error al cargar el archivo FXML o el recurso CSS.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(loader.load(), 400, 500);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setTitle("Hotel - Inicio de Sesión");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada principal de la aplicación Java.
     * <p>
     * Delega el arranque al método {@link #launch(String[])} de JavaFX.
     * </p>
     *
     * @param args Argumentos de línea de comandos pasados a la aplicación.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
