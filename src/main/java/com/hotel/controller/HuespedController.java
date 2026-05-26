package com.hotel.controller;

import com.hotel.dao.ClienteDAO;
import com.hotel.model.Cliente;
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

public class HuespedController implements Initializable {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellido;
    @FXML private TableColumn<Cliente, String> colCorreo;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TextField txtBuscar;

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        cargarClientes();
    }

    private void cargarClientes() {
        tablaClientes.setItems(FXCollections.observableArrayList(clienteDAO.obtenerTodos()));
    }

    @FXML
    public void handleNuevo() {
        mostrarDialogCliente(null).ifPresent(c -> {
            clienteDAO.guardar(c);
            cargarClientes();
        });
    }

    @FXML
    public void handleEditar() {
        Cliente sel = tablaClientes.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Selecciona un cliente para editar."); return; }
        mostrarDialogCliente(sel).ifPresent(c -> {
            clienteDAO.actualizar(c);
            cargarClientes();
        });
    }

    @FXML
    public void handleEliminar() {
        Cliente sel = tablaClientes.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Selecciona un cliente para eliminar."); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar a " + sel.getNombre() + " " + sel.getApellido() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) { clienteDAO.eliminar(sel.getIdCliente()); cargarClientes(); }
        });
    }

    @FXML
    public void handleBuscar() {
        String texto = txtBuscar.getText().trim();
        List<Cliente> resultados = texto.isEmpty() ? clienteDAO.obtenerTodos() : clienteDAO.buscarPorNombre(texto);
        tablaClientes.setItems(FXCollections.observableArrayList(resultados));
    }

    private Optional<Cliente> mostrarDialogCliente(Cliente existente) {
        boolean esEdicion = existente != null;
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle(esEdicion ? "Editar Cliente" : "Nuevo Cliente");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20, 40, 10, 20));

        TextField txtNombre   = new TextField(esEdicion ? nvl(existente.getNombre())   : "");
        TextField txtApellido = new TextField(esEdicion ? nvl(existente.getApellido()) : "");
        TextField txtCorreo   = new TextField(esEdicion ? nvl(existente.getCorreo())   : "");
        TextField txtTelefono = new TextField(esEdicion ? nvl(existente.getTelefono()) : "");
        TextField txtCiudad   = new TextField(esEdicion ? nvl(existente.getCiudad())   : "");
        txtNombre.setPrefWidth(220);

        grid.add(new Label("Nombre:"),   0, 0); grid.add(txtNombre,   1, 0);
        grid.add(new Label("Apellido:"), 0, 1); grid.add(txtApellido, 1, 1);
        grid.add(new Label("Correo:"),   0, 2); grid.add(txtCorreo,   1, 2);
        grid.add(new Label("Teléfono:"), 0, 3); grid.add(txtTelefono, 1, 3);
        grid.add(new Label("Ciudad:"),   0, 4); grid.add(txtCiudad,   1, 4);

        dialog.getDialogPane().setContent(grid);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(!esEdicion);
        Runnable check = () -> okBtn.setDisable(
                txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty());
        txtNombre.textProperty().addListener((o, ov, nv) -> check.run());
        txtApellido.textProperty().addListener((o, ov, nv) -> check.run());

        dialog.setResultConverter(bt -> {
            if (bt != ButtonType.OK) return null;
            Cliente c = esEdicion ? existente : new Cliente();
            c.setNombre(txtNombre.getText().trim());
            c.setApellido(txtApellido.getText().trim());
            c.setCorreo(txtCorreo.getText().trim());
            c.setTelefono(txtTelefono.getText().trim());
            c.setCiudad(txtCiudad.getText().trim());
            return c;
        });

        return dialog.showAndWait();
    }

    private String nvl(String s) { return s != null ? s : ""; }

    private void mostrarAlerta(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
}