package com.hotel.controller;

import com.hotel.dao.ClienteDAO;
import com.hotel.dao.HabitacionDAO;
import com.hotel.model.*;
import com.hotel.service.HabitacionService;
import com.hotel.service.ReservacionService;
import com.hotel.service.decorator.ReservacionBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReservacionController implements Initializable {

    @FXML private TableView<Reservacion>         tablaReservaciones;
    @FXML private TableColumn<Reservacion, String>    colCliente;
    @FXML private TableColumn<Reservacion, String>    colHabitacion;
    @FXML private TableColumn<Reservacion, LocalDate> colEntrada;
    @FXML private TableColumn<Reservacion, LocalDate> colSalida;
    @FXML private TableColumn<Reservacion, Double>    colTotal;
    @FXML private TableColumn<Reservacion, String>    colEstado;
    @FXML private DatePicker   dpEntrada;
    @FXML private DatePicker   dpSalida;
    @FXML private ComboBox<String> cmbTemporada;
    @FXML private CheckBox     chkDesayuno;
    @FXML private CheckBox     chkEstacionamiento;
    @FXML private CheckBox     chkSpa;
    @FXML private Label        lblTotal;

    // HabitacionService compartido: contiene los observadores registrados (DashboardObserver, etc.)
    private final HabitacionService habitacionService = new HabitacionService();
    // ReservacionService recibe HabitacionService para poder disparar el Observer al reservar/cancelar
    private final ReservacionService reservacionService = new ReservacionService(habitacionService);
    private final ClienteDAO    clienteDAO    = new ClienteDAO();
    private final HabitacionDAO habitacionDAO = new HabitacionDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCliente.setCellValueFactory(data -> {
            Cliente c = data.getValue().getCliente();
            return new SimpleStringProperty(c != null ? c.getNombre() + " " + c.getApellido() : "");
        });
        colHabitacion.setCellValueFactory(data -> {
            Habitacion h = data.getValue().getHabitacion();
            return new SimpleStringProperty(h != null ? h.getNumero() : "");
        });
        colEntrada.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));
        colSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colEstado.setCellValueFactory(data -> {
            EstadoReservacion e = data.getValue().getEstadoReservacion();
            return new SimpleStringProperty(e != null ? e.getNombre() : "");
        });

        // ── Filas con color según estado de la estadía ───────────────────────
        tablaReservaciones.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Reservacion r, boolean empty) {
                super.updateItem(r, empty);
                if (empty || r == null) {
                    setStyle("");
                    setTooltip(null);
                    return;
                }
                LocalDate hoy = LocalDate.now();
                // Estadía finalizada: fecha_salida antes de hoy
                if (r.getFechaSalida() != null && r.getFechaSalida().isBefore(hoy)) {
                    setStyle("-fx-background-color: #FFEBEE;"); // rojo claro
                    setTooltip(new Tooltip("⚠ Estadía finalizada el " + r.getFechaSalida()));
                }
                // Estadía activa hoy
                else if (r.getFechaEntrada() != null && r.getFechaSalida() != null
                        && !hoy.isBefore(r.getFechaEntrada()) && !hoy.isAfter(r.getFechaSalida())) {
                    setStyle("-fx-background-color: #E8F5E9;"); // verde claro
                    setTooltip(new Tooltip("✅ Estadía activa — sale el " + r.getFechaSalida()));
                }
                // Reservación futura
                else if (r.getFechaEntrada() != null && r.getFechaEntrada().isAfter(hoy)) {
                    setStyle("-fx-background-color: #E3F2FD;"); // azul claro
                    setTooltip(new Tooltip("📅 Próxima estadía — entra el " + r.getFechaEntrada()));
                } else {
                    setStyle("");
                    setTooltip(null);
                }
            }
        });

        cmbTemporada.setItems(FXCollections.observableArrayList("baja", "alta", "festivo"));
        cmbTemporada.setValue("baja");
        cargarReservaciones();
    }

    private void cargarReservaciones() {
        tablaReservaciones.setItems(
                FXCollections.observableArrayList(reservacionService.obtenerTodas()));
    }

    @FXML
    public void handleCalcular() {
        LocalDate entrada = dpEntrada.getValue();
        LocalDate salida  = dpSalida.getValue();
        if (entrada == null || salida == null) { lblTotal.setText("Selecciona las fechas."); return; }
        if (!salida.isAfter(entrada)) { lblTotal.setText("La salida debe ser posterior a la entrada."); return; }
        double total = reservacionService.calcularTotal(null, entrada, salida, cmbTemporada.getValue());
        ReservacionBase con = reservacionService.aplicarServicios(
                total,
                chkDesayuno.isSelected(),
                chkEstacionamiento.isSelected(),
                chkSpa.isSelected()
        );
        lblTotal.setText(String.format("Total: $%.2f | %s", con.calcularCosto(), con.getDescripcion()));
    }

    @FXML
    public void handleNueva() {
        mostrarDialogReservacion().ifPresent(r -> {
            reservacionService.guardar(r);
            cargarReservaciones();
        });
    }

    @FXML
    public void handleCancelar() {
        Reservacion sel = tablaReservaciones.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Selecciona una reservación para cancelar."); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Cancelar la reservación #" + sel.getIdReservacion() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) { reservacionService.cancelar(sel.getIdReservacion()); cargarReservaciones(); }
        });
    }

    private Optional<Reservacion> mostrarDialogReservacion() {
        Dialog<Reservacion> dialog = new Dialog<>();
        dialog.setTitle("Nueva Reservación");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);
        grid.setPadding(new Insets(20, 40, 10, 20));

        List<Cliente>    clientes    = clienteDAO.obtenerTodos();
        // Solo mostrar habitaciones disponibles para evitar reservar habitaciones ya ocupadas
        List<Habitacion> habitaciones = habitacionDAO.obtenerPorEstado("disponible");

        ComboBox<Cliente>    cmbCliente    = new ComboBox<>(FXCollections.observableArrayList(clientes));
        ComboBox<Habitacion> cmbHabitacion = new ComboBox<>(FXCollections.observableArrayList(habitaciones));
        DatePicker dpEnt  = new DatePicker();
        DatePicker dpSal  = new DatePicker();
        Spinner<Integer> spnHuespedes = new Spinner<>(1, 20, 1);
        ComboBox<String> cmbTemp = new ComboBox<>(FXCollections.observableArrayList("baja", "alta", "festivo"));
        
        // Checkboxes de servicios adicionales
        CheckBox chkDes = new CheckBox("🍳 Desayuno ($180)");
        CheckBox chkEst = new CheckBox("🚗 Estacionamiento ($120)");
        CheckBox chkSpa = new CheckBox("💆 Spa ($500)");
        chkDes.setSelected(true);        // Desayuno activado por defecto
        chkEst.setSelected(false);       // Estacionamiento desactivado por defecto (si no tienen carro)
        chkSpa.setSelected(false);       // Spa desactivado por defecto

        Label lblTotalDialog = new Label("Total: $0.00");
        cmbTemp.setValue("baja");
        cmbCliente.setPrefWidth(220);
        cmbHabitacion.setPrefWidth(220);

        cmbCliente.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNombre() + " " + c.getApellido());
            }
        });
        cmbCliente.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNombre() + " " + c.getApellido());
            }
        });
        cmbHabitacion.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Habitacion h, boolean empty) {
                super.updateItem(h, empty);
                if (empty || h == null) { setText(null); return; }
                String tipo = h.getTipoHabitacion() != null ? " — " + h.getTipoHabitacion().getNombre() : "";
                setText("Hab. " + h.getNumero() + tipo);
            }
        });
        cmbHabitacion.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Habitacion h, boolean empty) {
                super.updateItem(h, empty);
                if (empty || h == null) { setText(null); return; }
                String tipo = h.getTipoHabitacion() != null ? " — " + h.getTipoHabitacion().getNombre() : "";
                setText("Hab. " + h.getNumero() + tipo);
            }
        });

        Runnable recalcular = () -> {
            LocalDate ent = dpEnt.getValue();
            LocalDate sal = dpSal.getValue();
            if (ent != null && sal != null && sal.isAfter(ent)) {
                double t = reservacionService.calcularTotal(
                        cmbHabitacion.getValue(), ent, sal,
                        cmbTemp.getValue() != null ? cmbTemp.getValue() : "baja");
                
                // Aplicar decoradores
                ReservacionBase rb = reservacionService.aplicarServicios(t, chkDes.isSelected(), chkEst.isSelected(), chkSpa.isSelected());
                double totalConServicios = rb.calcularCosto();

                lblTotalDialog.setText(String.format("Total calculado: $%.2f | %s", totalConServicios, rb.getDescripcion()));
                lblTotalDialog.setStyle("-fx-font-weight: bold;");
            }
        };
        dpEnt.valueProperty().addListener((o, ov, nv)         -> recalcular.run());
        dpSal.valueProperty().addListener((o, ov, nv)         -> recalcular.run());
        cmbHabitacion.valueProperty().addListener((o, ov, nv) -> recalcular.run());
        cmbTemp.valueProperty().addListener((o, ov, nv)       -> recalcular.run());
        chkDes.selectedProperty().addListener((o, ov, nv)     -> recalcular.run());
        chkEst.selectedProperty().addListener((o, ov, nv)     -> recalcular.run());
        chkSpa.selectedProperty().addListener((o, ov, nv)     -> recalcular.run());

        grid.add(new Label("Cliente:"),       0, 0); grid.add(cmbCliente,    1, 0);
        grid.add(new Label("Habitación:"),    0, 1); grid.add(cmbHabitacion, 1, 1);
        grid.add(new Label("Fecha entrada:"), 0, 2); grid.add(dpEnt,         1, 2);
        grid.add(new Label("Fecha salida:"),  0, 3); grid.add(dpSal,         1, 3);
        grid.add(new Label("Huéspedes:"),     0, 4); grid.add(spnHuespedes,  1, 4);
        grid.add(new Label("Temporada:"),     0, 5); grid.add(cmbTemp,       1, 5);
        grid.add(new Label("Servicios extra:"), 0, 6); 
        javafx.scene.layout.HBox srvBox = new javafx.scene.layout.HBox(8, chkDes, chkEst, chkSpa);
        grid.add(srvBox, 1, 6);
        grid.add(lblTotalDialog,              0, 7, 2, 1);

        dialog.getDialogPane().setContent(grid);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(true);
        Runnable check = () -> {
            boolean falta = cmbCliente.getValue() == null || cmbHabitacion.getValue() == null
                    || dpEnt.getValue() == null || dpSal.getValue() == null;
            if (!falta) falta = !dpSal.getValue().isAfter(dpEnt.getValue());
            okBtn.setDisable(falta);
        };
        cmbCliente.valueProperty().addListener((o, ov, nv)    -> check.run());
        cmbHabitacion.valueProperty().addListener((o, ov, nv) -> check.run());
        dpEnt.valueProperty().addListener((o, ov, nv)         -> check.run());
        dpSal.valueProperty().addListener((o, ov, nv)         -> check.run());

        dialog.setResultConverter(bt -> {
            if (bt != ButtonType.OK) return null;
            Habitacion hab  = cmbHabitacion.getValue();
            String     temp = cmbTemp.getValue();
            double     total = reservacionService.calcularTotal(hab, dpEnt.getValue(), dpSal.getValue(), temp);
            
            // Aplicar decoradores para costo final
            ReservacionBase rb = reservacionService.aplicarServicios(total, chkDes.isSelected(), chkEst.isSelected(), chkSpa.isSelected());
            double totalConServicios = rb.calcularCosto();

            Reservacion r = new Reservacion();
            r.setCliente(cmbCliente.getValue());
            r.setHabitacion(hab);
            r.setFechaReservacion(LocalDateTime.now());
            r.setFechaEntrada(dpEnt.getValue());
            r.setFechaSalida(dpSal.getValue());
            r.setNumeroHuespedes(spnHuespedes.getValue());
            r.setTotal(totalConServicios);
            r.setEstadoReservacion(new EstadoReservacion(1, "pendiente"));
            return r;
        });

        return dialog.showAndWait();
    }

    private void mostrarAlerta(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
}