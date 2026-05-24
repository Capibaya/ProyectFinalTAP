package com.hotel.controller;

import com.hotel.dao.ClienteDAO;
import com.hotel.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
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
        List<Cliente> lista = clienteDAO.obtenerTodos();
        tablaClientes.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void handleNuevo() {
        Cliente nuevo = new Cliente();
        nuevo.setNombre("Nuevo");
        nuevo.setApellido("Cliente");
        nuevo.setCorreo("cliente@email.com");
        nuevo.setTelefono("0000000000");
        clienteDAO.guardar(nuevo);
        cargarClientes();
        mostrarInfo("Cliente agregado correctamente.");
    }

    @FXML
    public void handleEditar() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un cliente para editar.");
            return;
        }
        seleccionado.setNombre(seleccionado.getNombre() + " (editado)");
        clienteDAO.actualizar(seleccionado);
        cargarClientes();
        mostrarInfo("Cliente actualizado correctamente.");
    }

    @FXML
    public void handleEliminar() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un cliente para eliminar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar a " + seleccionado.getNombre() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                clienteDAO.eliminar(seleccionado.getIdCliente());
                cargarClientes();
            }
        });
    }

    @FXML
    public void handleBuscar() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarClientes();
            return;
        }
        List<Cliente> resultados = clienteDAO.buscarPorNombre(texto);
        tablaClientes.setItems(FXCollections.observableArrayList(resultados));
    }

    private void mostrarAlerta(String mensaje) {
        new Alert(Alert.AlertType.WARNING, mensaje).showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}
