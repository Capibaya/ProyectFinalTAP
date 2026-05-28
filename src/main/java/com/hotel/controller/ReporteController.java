package com.hotel.controller;

import com.hotel.model.Reservacion;
import com.hotel.model.UsuarioSistema;
import com.hotel.service.HabitacionService;
import com.hotel.service.ReservacionService;
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

/**
 * Controlador JavaFX para la pantalla de reportes de reservaciones.
 * <p>
 * Implementa el patrón <b>Factory Method</b>: la generación del reporte
 * se delega a {@link ReporteFactory}, cuya implementación concreta
 * {@link ReportePDFFactory} produce archivos PDF. Esto permite agregar
 * futuros formatos (Excel, HTML, etc.) sin modificar este controlador.
 * </p>
 * <p>
 * Permite filtrar reservaciones por rango de fechas, visualizarlas en una
 * tabla y exportarlas como PDF al directorio personal del usuario.
 * </p>
 */
public class ReporteController implements Initializable {

    /** Tabla que muestra el listado de reservaciones según el filtro aplicado. */
    @FXML private TableView<Reservacion> tablaReporte;

    /** Columna que muestra la fecha de entrada de cada reservación. */
    @FXML private TableColumn<Reservacion, String> colFecha;

    /** Columna que muestra el número de habitación asociado a la reservación. */
    @FXML private TableColumn<Reservacion, String> colHabitacion;

    /** Columna que muestra el nombre completo del cliente de la reservación. */
    @FXML private TableColumn<Reservacion, String> colCliente;

    /** Columna que muestra el total monetario de la reservación. */
    @FXML private TableColumn<Reservacion, Double> colTotal;

    /** Columna que muestra el estado actual de la reservación (ej. Confirmada, Cancelada). */
    @FXML private TableColumn<Reservacion, String> colEstado;

    /** Selector de fecha que define el inicio del rango de filtrado. */
    @FXML private DatePicker dpDesde;

    /** Selector de fecha que define el fin del rango de filtrado. */
    @FXML private DatePicker dpHasta;

    /** Etiqueta que muestra mensajes informativos o de estado al usuario. */
    @FXML private Label lblEstado;

    /** Servicio de negocio utilizado para obtener y filtrar reservaciones. */
    private final ReservacionService reservacionService =
            new ReservacionService(new HabitacionService());

    /** Usuario del sistema autenticado actualmente, inyectado desde el Dashboard. */
    private UsuarioSistema usuarioActual;

    /**
     * Inyecta el usuario autenticado en el controlador para su uso contextual.
     *
     * @param usuario El {@link UsuarioSistema} que inició sesión en la aplicación.
     */
    public void setUsuario(UsuarioSistema usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Inicializa el controlador tras cargar el FXML.
     * <p>
     * Configura las fábricas de valores ({@code CellValueFactory}) de cada
     * columna de la tabla y carga todas las reservaciones disponibles
     * llamando a {@link #cargarReporte()}.
     * </p>
     *
     * @param url            URL de localización del recurso FXML (no utilizado directamente).
     * @param rb             Paquete de recursos de internacionalización (no utilizado directamente).
     */
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

        cargarReporte();
    }

    /**
     * Carga todas las reservaciones existentes desde el servicio y las muestra
     * en la tabla sin aplicar ningún filtro de fechas.
     */
    private void cargarReporte() {
        tablaReporte.setItems(FXCollections.observableArrayList(reservacionService.obtenerTodas()));
    }

    /**
     * Maneja el evento de clic en el botón "Generar Reporte PDF".
     * <p>
     * Valida que ambos selectores de fecha tengan valor, obtiene las reservaciones
     * del rango especificado mediante {@link ReservacionService#obtenerPorFechas(LocalDate, LocalDate)},
     * y genera un archivo PDF usando el patrón Factory con {@link ReportePDFFactory}.
     * El archivo se guarda en el directorio personal del usuario con un nombre
     * que incluye el rango de fechas. El resultado se refleja en la tabla y en
     * {@link #lblEstado}.
     * </p>
     */
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

        ReporteFactory factory = new ReportePDFFactory();
        String ruta = System.getProperty("user.home") + "/reporte_hotel_"
                + desde + "_" + hasta + ".pdf";

        factory.generarReporte(datos, ruta);
        tablaReporte.setItems(FXCollections.observableArrayList(datos));
        lblEstado.setText("✅ Reporte PDF generado (" + datos.size() + " registros): " + ruta);
        lblEstado.setStyle("-fx-text-fill: green;");
    }

    /**
     * Maneja el evento de clic en el botón "Filtrar".
     * <p>
     * Si ambos selectores de fecha tienen valor, filtra la tabla mostrando
     * únicamente las reservaciones dentro del rango indicado. Si alguno de los
     * selectores está vacío, recarga todas las reservaciones sin filtro.
     * </p>
     */
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