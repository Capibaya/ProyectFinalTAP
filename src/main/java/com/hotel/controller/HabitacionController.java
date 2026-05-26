package com.hotel.controller;

import com.hotel.dao.TipoHabitacionDAO;
import com.hotel.model.*;
import com.hotel.service.HabitacionService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HabitacionController implements Initializable {

    @FXML private TableView<Habitacion>         tablaHabitaciones;
    @FXML private TableColumn<Habitacion, String>  colNumero;
    @FXML private TableColumn<Habitacion, Integer> colPiso;
    @FXML private TableColumn<Habitacion, String>  colTipo;
    @FXML private TableColumn<Habitacion, Double>  colPrecio;
    @FXML private TableColumn<Habitacion, String>  colEstado;
    @FXML private ComboBox<String>  cmbFiltroEstado;
    @FXML private Button btnCambiarEstado;
    @FXML private Button btnEliminar;
    @FXML private Button btnNueva;

    private final HabitacionService habitacionService = new HabitacionService();
    private UsuarioSistema usuarioActual;

    /** Recibe el usuario autenticado para aplicar restricciones por rol. */
    public void setUsuario(UsuarioSistema usuario) {
        this.usuarioActual = usuario;
        aplicarPrivilegios();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Bug corregido: usar lambdas para columnas con objetos anidados
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colPiso.setCellValueFactory(new PropertyValueFactory<>("piso"));

        // Tipo: tipoHabitacion.nombre
        colTipo.setCellValueFactory(data -> {
            TipoHabitacion t = data.getValue().getTipoHabitacion();
            return new SimpleStringProperty(t != null ? t.getNombre() : "—");
        });

        // Precio: tipoHabitacion.precioNoche
        colPrecio.setCellValueFactory(data -> {
            TipoHabitacion t = data.getValue().getTipoHabitacion();
            return new SimpleDoubleProperty(t != null ? t.getPrecioNoche() : 0.0).asObject();
        });
        colPrecio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                setText(empty || precio == null ? null : String.format("$%.2f", precio));
            }
        });

        // Estado: estadoHabitacion.nombre
        colEstado.setCellValueFactory(data -> {
            EstadoHabitacion e = data.getValue().getEstadoHabitacion();
            return new SimpleStringProperty(e != null ? e.getNombre() : "—");
        });

        // Cargar estados disponibles desde BD
        List<String> estados = habitacionService.obtenerNombresEstados();
        estados.add(0, "Todos");
        cmbFiltroEstado.setItems(FXCollections.observableArrayList(estados));
        cmbFiltroEstado.setValue("Todos");

        cargarHabitaciones();
    }

    /** Oculta/deshabilita controles sensibles según el rol del usuario. */
    private void aplicarPrivilegios() {
        // Fix: usa idRol==1 (admin) igual que DashboardController,
        // sin depender del nombre exacto del rol en la BD.
        boolean esAdmin = usuarioActual != null
                && usuarioActual.getRol() != null
                && usuarioActual.getRol().getIdRol() == 1;
        if (btnEliminar != null) { btnEliminar.setVisible(esAdmin); btnEliminar.setManaged(esAdmin); }
        if (btnNueva    != null) { btnNueva.setVisible(esAdmin);    btnNueva.setManaged(esAdmin);    }
    }

    private void cargarHabitaciones() {
        List<Habitacion> lista = habitacionService.obtenerTodas();
        tablaHabitaciones.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void handleFiltrar() {
        String filtro = cmbFiltroEstado.getValue();
        List<Habitacion> lista = (filtro == null || filtro.equals("Todos"))
                ? habitacionService.obtenerTodas()
                : habitacionService.obtenerPorEstado(filtro);
        tablaHabitaciones.setItems(FXCollections.observableArrayList(lista));
    }

    /** Formulario de nueva habitación. */
    @FXML
    public void handleNueva() {
        mostrarDialogoHabitacion(null).ifPresent(h -> {
            // Guardar via DAO directamente (el service delega)
            new com.hotel.dao.HabitacionDAO().guardar(h);
            cargarHabitaciones();
            mostrarInfo("Habitación '" + h.getNumero() + "' creada correctamente.");
        });
    }

    /** Cambiar estado con ComboBox de estados reales desde la BD. */
    @FXML
    public void handleCambiarEstado() {
        Habitacion seleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una habitación de la tabla.");
            return;
        }

        List<String> estados = habitacionService.obtenerNombresEstados();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                seleccionada.getEstadoHabitacion() != null
                        ? seleccionada.getEstadoHabitacion().getNombre()
                        : estados.get(0),
                estados);
        dialog.setTitle("Cambiar Estado");
        dialog.setHeaderText("Habitación " + seleccionada.getNumero());
        dialog.setContentText("Nuevo estado:");

        dialog.showAndWait().ifPresent(nuevoEstado -> {
            habitacionService.cambiarEstado(seleccionada, nuevoEstado, null);
            cargarHabitaciones();
            mostrarInfo("Estado cambiado a: " + nuevoEstado);
        });
    }

    @FXML
    public void handleEliminar() {
        Habitacion seleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una habitación de la tabla.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar habitación " + seleccionada.getNumero() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirmar eliminación");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                habitacionService.eliminar(seleccionada.getIdHabitacion());
                cargarHabitaciones();
            }
        });
    }

    // ─── Diálogo de nueva habitación ────────────────────────────────────────

    private Optional<Habitacion> mostrarDialogoHabitacion(Habitacion existente) {
        Dialog<Habitacion> dialog = new Dialog<>();
        dialog.setTitle(existente == null ? "Nueva Habitación" : "Editar Habitación");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20, 40, 10, 20));

        TextField txtNumero = new TextField(existente != null ? existente.getNumero() : "");
        txtNumero.setPromptText("Ej: 101");
        Spinner<Integer> spnPiso = new Spinner<>(1, 20, existente != null ? existente.getPiso() : 1);
        spnPiso.setEditable(true);
        TextField txtDescripcion = new TextField(existente != null ? existente.getDescripcion() : "");
        txtDescripcion.setPromptText("Descripción opcional");

        // Cargar tipos de habitación desde BD
        List<TipoHabitacion> tipos = new TipoHabitacionDAO().obtenerTodos();
        ComboBox<TipoHabitacion> cmbTipo = new ComboBox<>(FXCollections.observableArrayList(tipos));
        cmbTipo.setPrefWidth(200);
        cmbTipo.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(TipoHabitacion t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? null : t.getNombre() + " — $" + String.format("%.0f", t.getPrecioNoche()));
            }
        });
        cmbTipo.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(TipoHabitacion t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? null : t.getNombre() + " — $" + String.format("%.0f", t.getPrecioNoche()));
            }
        });
        if (existente != null && existente.getTipoHabitacion() != null) {
            tipos.stream().filter(t -> t.getIdTipo() == existente.getTipoHabitacion().getIdTipo())
                    .findFirst().ifPresent(cmbTipo::setValue);
        }

        // Cargar estados desde BD
        List<String> nombresEstados = habitacionService.obtenerNombresEstados();
        ComboBox<String> cmbEstado = new ComboBox<>(FXCollections.observableArrayList(nombresEstados));
        cmbEstado.setPrefWidth(200);
        if (existente != null && existente.getEstadoHabitacion() != null) {
            cmbEstado.setValue(existente.getEstadoHabitacion().getNombre());
        } else if (!nombresEstados.isEmpty()) {
            cmbEstado.setValue(nombresEstados.get(0));
        }

        grid.add(new Label("Número:"),     0, 0); grid.add(txtNumero,     1, 0);
        grid.add(new Label("Piso:"),       0, 1); grid.add(spnPiso,       1, 1);
        grid.add(new Label("Tipo:"),       0, 2); grid.add(cmbTipo,       1, 2);
        grid.add(new Label("Estado:"),     0, 3); grid.add(cmbEstado,     1, 3);
        grid.add(new Label("Descripción:"),0, 4); grid.add(txtDescripcion,1, 4);

        dialog.getDialogPane().setContent(grid);

        // Validar que los campos obligatorios estén completos
        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(true);
        Runnable check = () -> okBtn.setDisable(
                txtNumero.getText().trim().isEmpty()
                        || cmbTipo.getValue() == null
                        || cmbEstado.getValue() == null);
        txtNumero.textProperty().addListener((o, ov, nv) -> check.run());
        cmbTipo.valueProperty().addListener((o, ov, nv) -> check.run());
        cmbEstado.valueProperty().addListener((o, ov, nv) -> check.run());

        dialog.setResultConverter(bt -> {
            if (bt != ButtonType.OK) return null;
            int idEstado = new com.hotel.dao.HabitacionDAO().buscarIdEstadoPorNombre(cmbEstado.getValue());
            EstadoHabitacion estado = new EstadoHabitacion();
            estado.setIdEstadoHabitacion(idEstado);
            estado.setNombre(cmbEstado.getValue());

            Habitacion h = existente != null ? existente : new Habitacion();
            h.setNumero(txtNumero.getText().trim());
            h.setPiso(spnPiso.getValue());
            h.setDescripcion(txtDescripcion.getText().trim());
            h.setTipoHabitacion(cmbTipo.getValue());
            h.setEstadoHabitacion(estado);
            return h;
        });

        return dialog.showAndWait();
    }

    private void mostrarAlerta(String mensaje) {
        new Alert(Alert.AlertType.WARNING, mensaje).showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}
