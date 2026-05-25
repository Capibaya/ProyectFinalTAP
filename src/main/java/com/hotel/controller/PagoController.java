package com.hotel.controller;

import com.hotel.DAO.PagoDAO;
import com.hotel.DAO.ReservacionDAO;
import com.hotel.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PagoController implements Initializable {

    // ── Tabla Pagos ──────────────────────────────────────────────────────────
    @FXML private TableView<Pago>              tablaPagos;
    @FXML private TableColumn<Pago, String>    colIdPago;
    @FXML private TableColumn<Pago, String>    colReservacion;
    @FXML private TableColumn<Pago, String>    colMetodo;
    @FXML private TableColumn<Pago, String>    colMonto;
    @FXML private TableColumn<Pago, String>    colFechaPago;

    // ── Botones ──────────────────────────────────────────────────────────────
    @FXML private Button btnRegistrarPago;
    @FXML private Button btnEliminarPago;

    // ── Resumen ──────────────────────────────────────────────────────────────
    @FXML private Label lblTotalIngresos;
    @FXML private Label lblCantPagos;

    private final PagoDAO       pagoDAO       = new PagoDAO();
    private final ReservacionDAO reservacionDAO = new ReservacionDAO();
    private UsuarioSistema usuarioActual;

    public void setUsuario(UsuarioSistema u) {
        this.usuarioActual = u;
        aplicarRol();
    }

    private void aplicarRol() {
        if (usuarioActual != null && usuarioActual.getRol() != null) {
            boolean admin = usuarioActual.getRol().getIdRol() == 1;
            // Solo administrador puede eliminar registros de pago
            if (btnEliminarPago != null) {
                btnEliminarPago.setVisible(admin);
                btnEliminarPago.setManaged(admin);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaPagos();
        cargarDatos();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Configuración de tablas
    // ─────────────────────────────────────────────────────────────────────────

    private void configurarTablaPagos() {
        colIdPago.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIdPago())));
        colReservacion.setCellValueFactory(d -> {
            Reservacion r = d.getValue().getReservacion();
            if (r == null) return new SimpleStringProperty("—");
            String info = "Res. #" + r.getIdReservacion();
            if (r.getFechaEntrada() != null)
                info += " (" + r.getFechaEntrada() + " → " + r.getFechaSalida() + ")";
            return new SimpleStringProperty(info);
        });
        colMetodo.setCellValueFactory(d -> {
            MetodoPago m = d.getValue().getMetodoPago();
            return new SimpleStringProperty(m != null ? m.getNombre() : "—");
        });
        colMonto.setCellValueFactory(d ->
                new SimpleStringProperty(String.format("$%.2f", d.getValue().getMonto())));
        colFechaPago.setCellValueFactory(d -> {
            LocalDateTime f = d.getValue().getFechaPago();
            return new SimpleStringProperty(f != null
                    ? f.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "—");
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Carga de datos
    // ─────────────────────────────────────────────────────────────────────────

    private void cargarDatos() {
        // Pagos
        List<Pago> pagos = pagoDAO.obtenerTodos();
        tablaPagos.setItems(FXCollections.observableArrayList(pagos));

        // Resumen
        double total = pagoDAO.obtenerTotalIngresos();
        lblTotalIngresos.setText(String.format("$%.2f", total));
        lblCantPagos.setText(String.valueOf(pagos.size()));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Acciones — Pagos
    // ─────────────────────────────────────────────────────────────────────────

    @FXML
    public void handleNuevoPago() {
        mostrarDialogoPago(null).ifPresent(p -> {
            pagoDAO.guardar(p);
            cargarDatos();
            mostrarInfo("Pago registrado correctamente.");
        });
    }

    @FXML
    public void handleEliminarPago() {
        Pago sel = tablaPagos.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Selecciona un pago de la tabla."); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar pago #" + sel.getIdPago() + " de $" + String.format("%.2f", sel.getMonto()) + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirmar eliminación");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) { pagoDAO.eliminar(sel.getIdPago()); cargarDatos(); }
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Diálogo nuevo pago
    // ─────────────────────────────────────────────────────────────────────────

    private Optional<Pago> mostrarDialogoPago(Pago existente) {
        Dialog<Pago> dialog = new Dialog<>();
        dialog.setTitle("Registrar Pago");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20, 40, 10, 20));

        // ComboBox de reservaciones
        List<Reservacion> reservaciones = reservacionDAO.obtenerTodos();
        ComboBox<Reservacion> cmbRes = new ComboBox<>(FXCollections.observableArrayList(reservaciones));
        cmbRes.setPrefWidth(260);
        cmbRes.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Reservacion r, boolean empty) {
                super.updateItem(r, empty); if (empty || r == null) { setText(null); return; }
                String cliente = r.getCliente() != null
                        ? r.getCliente().getNombre() + " " + r.getCliente().getApellido() : "—";
                setText("#" + r.getIdReservacion() + " — " + cliente
                        + " (" + r.getFechaEntrada() + ")");
            }
        });
        cmbRes.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Reservacion r, boolean empty) {
                super.updateItem(r, empty); if (empty || r == null) { setText(null); return; }
                String cliente = r.getCliente() != null
                        ? r.getCliente().getNombre() + " " + r.getCliente().getApellido() : "—";
                setText("#" + r.getIdReservacion() + " — " + cliente);
            }
        });

        // ComboBox de métodos de pago
        List<MetodoPago> metodos = pagoDAO.obtenerMetodosPago();
        ComboBox<MetodoPago> cmbMetodo = new ComboBox<>(FXCollections.observableArrayList(metodos));
        cmbMetodo.setPrefWidth(200);
        cmbMetodo.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(MetodoPago m, boolean empty) {
                super.updateItem(m, empty);
                setText(empty || m == null ? null : m.getNombre());
            }
        });
        cmbMetodo.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(MetodoPago m, boolean empty) {
                super.updateItem(m, empty);
                setText(empty || m == null ? null : m.getNombre());
            }
        });

        // Monto
        TextField txtMonto = new TextField(existente != null ? String.format("%.2f", existente.getMonto()) : "");
        txtMonto.setPromptText("Ej: 1500.00");

        // Autocompletar monto al seleccionar reservación
        cmbRes.valueProperty().addListener((o, ov, nv) -> {
            if (nv != null && txtMonto.getText().isBlank())
                txtMonto.setText(String.format("%.2f", nv.getTotal()));
        });

        grid.add(new Label("Reservación:"),      0, 0); grid.add(cmbRes,    1, 0);
        grid.add(new Label("Método de pago:"),   0, 1); grid.add(cmbMetodo, 1, 1);
        grid.add(new Label("Monto ($):"),        0, 2); grid.add(txtMonto,  1, 2);

        dialog.getDialogPane().setContent(grid);

        // Validación OK
        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(true);
        Runnable check = () -> {
            try {
                boolean ok = cmbRes.getValue() != null && cmbMetodo.getValue() != null
                        && !txtMonto.getText().isBlank()
                        && Double.parseDouble(txtMonto.getText().replace(",", ".")) > 0;
                okBtn.setDisable(!ok);
            } catch (NumberFormatException e) { okBtn.setDisable(true); }
        };
        cmbRes.valueProperty().addListener((o, ov, nv) -> check.run());
        cmbMetodo.valueProperty().addListener((o, ov, nv) -> check.run());
        txtMonto.textProperty().addListener((o, ov, nv) -> check.run());

        dialog.setResultConverter(bt -> {
            if (bt != ButtonType.OK) return null;
            Pago p = existente != null ? existente : new Pago();
            p.setReservacion(cmbRes.getValue());
            p.setMetodoPago(cmbMetodo.getValue());
            p.setMonto(Double.parseDouble(txtMonto.getText().replace(",", ".")));
            p.setFechaPago(LocalDateTime.now());
            return p;
        });

        return dialog.showAndWait();
    }

    private void mostrarAlerta(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
    private void mostrarInfo(String msg)   { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}
