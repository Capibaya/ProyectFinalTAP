package com.hotel.controller;

import com.hotel.dao.PagoDAO;
import com.hotel.model.UsuarioSistema;
import com.hotel.service.HabitacionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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

    // Barra lateral
    @FXML private Label  lblNombreUsuario;
    @FXML private Label  lblRolUsuario;
    @FXML private Button btnReportes;
    @FXML private Button btnPagos;

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
            // Mostrar meses vacíos como referencia visual
            for (String m : new String[]{"Ene", "Feb", "Mar", "Abr", "May", "Jun",
                                         "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"}) {
                series.getData().add(new XYChart.Data<>(m, 0));
            }
        } else {
            ingresos.forEach((mes, total) ->
                    series.getData().add(new XYChart.Data<>(mes, total)));
        }
        barIngresos.getData().add(series);
    }

    // ─── Privilegios ─────────────────────────────────────────────────────────

    /**
     * Fix: ya no compara contra el string "admin" exacto.
     * Ahora se considera admin si el id_rol == 1, que es el estándar en la BD.
     * Esto soluciona el bug cuando el nombre del rol es "Administrador", "Admin", etc.
     */
    private boolean esAdmin() {
        if (usuarioActual == null || usuarioActual.getRol() == null) return false;
        // Criterio robusto: rol ID 1 = administrador
        return usuarioActual.getRol().getIdRol() == 1;
    }

    private boolean esRecepcionista() {
        if (usuarioActual == null || usuarioActual.getRol() == null) return false;
        // Criterio robusto: rol ID 2 = recepcionista
        return usuarioActual.getRol().getIdRol() == 2;
    }

    private void aplicarPrivilegiosPorRol() {
        boolean admin = esAdmin();
        boolean recep = esRecepcionista();
        // Solo admin ve Reportes
        if (btnReportes != null) { btnReportes.setVisible(admin); btnReportes.setManaged(admin); }
        // Admin y Recepcionista ven Pagos
        if (btnPagos    != null) {
            boolean accesoPagos = admin || recep;
            btnPagos.setVisible(accesoPagos);
            btnPagos.setManaged(accesoPagos);
        }
    }

    // ─── Navegación ──────────────────────────────────────────────────────────

    @FXML public void irDashboard() {
        contenidoCentral.getChildren().setAll(dashboardHome);
        cargarIndicadores();
        cargarGraficas();
    }

    @FXML public void irClientes()      { cargarVista("/fxml/Huespedes.fxml");     }
    @FXML public void irHabitaciones()  { cargarVista("/fxml/Habitaciones.fxml");  }
    @FXML public void irReservaciones() { cargarVista("/fxml/Reservaciones.fxml"); }
    @FXML public void irReportes()      { cargarVista("/fxml/Reportes.fxml");      }
    @FXML public void irPagos()         { cargarVista("/fxml/Pagos.fxml");         }

    private void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node content = loader.load();
            Object ctrl = loader.getController();
            // Propagar usuario a las vistas que lo necesiten
            if (ctrl instanceof HabitacionController hc) hc.setUsuario(usuarioActual);
            else if (ctrl instanceof ReporteController rc)  rc.setUsuario(usuarioActual);
            else if (ctrl instanceof PagoController    pc)  pc.setUsuario(usuarioActual);
            contenidoCentral.getChildren().setAll(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}