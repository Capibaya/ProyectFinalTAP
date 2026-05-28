package com.hotel.controller;

import com.hotel.dao.PagoDAO;
import com.hotel.model.UsuarioSistema;
import com.hotel.service.HabitacionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label lblDisponibles;
    @FXML private Label lblOcupadas;
    @FXML private Label lblMantenimiento;
    @FXML private Label lblLimpieza;
    @FXML private Label lblTotalGanancias;
    @FXML private PieChart pieOcupacion;
    @FXML private BarChart<String, Number> barIngresos;
    @FXML private StackPane contenidoCentral;

    // Sidebar labels
    @FXML private Label  lblNombreUsuario;
    @FXML private Label  lblRolUsuario;

    // Botones del sidebar (para mostrar/ocultar según rol)
    @FXML private Button btnClientes;
    @FXML private Button btnHabitaciones;
    @FXML private Button btnReservaciones;
    @FXML private Button btnPagos;
    @FXML private Button btnEmpleados;
    @FXML private Button btnServicios;
    @FXML private Button btnConsumos;
    @FXML private Button btnReportes;

    private final HabitacionService habitacionService = new HabitacionService();
    private final PagoDAO           pagoDAO           = new PagoDAO();
    private UsuarioSistema usuarioActual;
    private Node           dashboardHome;

    // ─── Usuario ─────────────────────────────────────────────────────────────

    public void setUsuario(UsuarioSistema usuario) {
        this.usuarioActual = usuario;
        if (lblNombreUsuario != null)
            lblNombreUsuario.setText(
                    usuario.getEmpleado().getNombre() + " " + usuario.getEmpleado().getApellido());
        if (lblRolUsuario != null && usuario.getRol() != null)
            lblRolUsuario.setText(usuario.getRol().getNombreRol().toUpperCase());
        aplicarPrivilegiosPorRol();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboardHome = contenidoCentral.getChildren().get(0);
        cargarIndicadores();
        cargarGraficas();
    }

    // ─── Indicadores ─────────────────────────────────────────────────────────

    private void cargarIndicadores() {
        lblDisponibles.setText(String.valueOf(habitacionService.contarPorEstado("disponible")));
        lblOcupadas.setText(String.valueOf(habitacionService.contarPorEstado("ocupada")));
        lblMantenimiento.setText(String.valueOf(habitacionService.contarPorEstado("mantenimiento")));
        lblLimpieza.setText(String.valueOf(habitacionService.contarPorEstado("limpieza")));
        if (lblTotalGanancias != null) {
            double total = pagoDAO.obtenerTotalIngresos();
            lblTotalGanancias.setText(String.format("$%.2f", total));
        }
    }

    private void cargarGraficas() {
        // PieChart
        pieOcupacion.getData().clear();
        int disp = habitacionService.contarPorEstado("disponible");
        int ocup = habitacionService.contarPorEstado("ocupada");
        int mant = habitacionService.contarPorEstado("mantenimiento");
        int limp = habitacionService.contarPorEstado("limpieza");
        if (disp + ocup + mant + limp > 0) {
            if (disp > 0) pieOcupacion.getData().add(new PieChart.Data("Disponibles (" + disp + ")", disp));
            if (ocup > 0) pieOcupacion.getData().add(new PieChart.Data("Ocupadas (" + ocup + ")", ocup));
            if (mant > 0) pieOcupacion.getData().add(new PieChart.Data("Mantenimiento (" + mant + ")", mant));
            if (limp > 0) pieOcupacion.getData().add(new PieChart.Data("Limpieza (" + limp + ")", limp));
        } else {
            pieOcupacion.getData().add(new PieChart.Data("Sin datos", 1));
        }

        // BarChart — ingresos reales del año actual
        barIngresos.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos " + java.time.Year.now().getValue());
        Map<String, Double> ingresos = habitacionService.obtenerIngresosPorMes();
        if (ingresos.isEmpty()) {
            for (String m : new String[]{"Ene","Feb","Mar","Abr","May","Jun",
                                         "Jul","Ago","Sep","Oct","Nov","Dic"}) {
                series.getData().add(new XYChart.Data<>(m, 0));
            }
        } else {
            ingresos.forEach((mes, total) ->
                    series.getData().add(new XYChart.Data<>(mes, total)));
        }
        barIngresos.getData().add(series);
    }

    // ─── Privilegios por Rol ─────────────────────────────────────────────────

    private int getRolId() {
        if (usuarioActual == null || usuarioActual.getRol() == null) return -1;
        return usuarioActual.getRol().getIdRol();
    }

    private boolean esAdmin()          { return getRolId() == 1; }
    private boolean esRecepcionista()  { return getRolId() == 2; }
    private boolean esLimpieza()       { return getRolId() == 3; }
    private boolean esMantenimiento()  { return getRolId() == 4; }

    /**
     * Aplica visibilidad del sidebar según el rol del usuario autenticado.
     * <ul>
     *   <li>Admin (1): ve todo</li>
     *   <li>Recepcionista (2): ve Clientes, Habitaciones, Reservaciones, Pagos</li>
     *   <li>Limpieza (3): solo ve su panel especializado (se carga automáticamente)</li>
     *   <li>Mantenimiento (4): solo ve su panel especializado (se carga automáticamente)</li>
     * </ul>
     */
    private void aplicarPrivilegiosPorRol() {
        boolean admin  = esAdmin();
        boolean recep  = esRecepcionista();
        boolean limp   = esLimpieza();
        boolean manto  = esMantenimiento();

        // Configurar visibilidad de cada botón
        setVisible(btnClientes,      admin || recep);
        setVisible(btnHabitaciones,  admin || recep);
        setVisible(btnReservaciones, admin || recep);
        setVisible(btnPagos,         admin || recep);
        setVisible(btnEmpleados,     admin);
        setVisible(btnServicios,     admin);
        setVisible(btnConsumos,      admin || recep);
        setVisible(btnReportes,      admin);

        // Para limpieza y mantenimiento: cargar su vista especializada de inmediato
        if (limp || manto) {
            irLimpiezaMantenimiento();
        }
    }

    private void setVisible(Button btn, boolean visible) {
        if (btn != null) { btn.setVisible(visible); btn.setManaged(visible); }
    }

    // ─── Navegación ──────────────────────────────────────────────────────────

    @FXML public void irDashboard() {
        contenidoCentral.getChildren().setAll(dashboardHome);
        cargarIndicadores();
        cargarGraficas();
    }

    @FXML public void irClientes()      { cargarVista("/fxml/Huespedes.fxml");              }
    @FXML public void irHabitaciones()  { cargarVista("/fxml/Habitaciones.fxml");            }
    @FXML public void irReservaciones() { cargarVista("/fxml/Reservaciones.fxml");           }
    @FXML public void irReportes()      { cargarVista("/fxml/Reportes.fxml");                }
    @FXML public void irPagos()         { cargarVista("/fxml/Pagos.fxml");                   }
    @FXML public void irEmpleados()     { cargarVista("/fxml/Empleados.fxml");               }
    @FXML public void irServicios()     { cargarVista("/fxml/Servicios.fxml");               }
    @FXML public void irConsumos()      { cargarVista("/fxml/Consumos.fxml");                }
    @FXML public void irLimpiezaMantenimiento() { cargarVista("/fxml/LimpiezaMantenimiento.fxml"); }

    /**
     * Cierra la sesión actual y regresa a la pantalla de Login.
     */
    @FXML
    public void cerrarSesion() {
        try {
            // Obtener el Stage actual desde cualquier nodo del contenido central
            Stage stageActual = (Stage) contenidoCentral.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Scene sceneLogin = new Scene(loader.load(), 400, 500);
            sceneLogin.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stageActual.setScene(sceneLogin);
            stageActual.setTitle("Hotel - Inicio de Sesión");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node content = loader.load();
            Object ctrl = loader.getController();
            // Propagar usuario a todas las vistas que lo necesiten
            if      (ctrl instanceof HabitacionController hc)             hc.setUsuario(usuarioActual);
            else if (ctrl instanceof ReporteController rc)                 rc.setUsuario(usuarioActual);
            else if (ctrl instanceof PagoController pc)                    pc.setUsuario(usuarioActual);
            else if (ctrl instanceof EmpleadoController ec)               ec.setUsuario(usuarioActual);
            else if (ctrl instanceof ServicioController sc)               {} // sin usuario
            else if (ctrl instanceof LimpiezaMantenimientoController lmc) lmc.setUsuario(usuarioActual);
            contenidoCentral.getChildren().setAll(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}