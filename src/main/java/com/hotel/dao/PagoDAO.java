package com.hotel.dao;

import com.hotel.model.*;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO implements GenericDAO<Pago> {
    private final Connection con = DatabaseConnection.getInstancia().getConexion();

    @Override
    public void guardar(Pago p) {
        String sql = "INSERT INTO pagos (id_reservacion, id_metodo_pago, monto, fecha_pago) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getReservacion().getIdReservacion());
            ps.setInt(2, p.getMetodoPago().getIdMetodoPago());
            ps.setDouble(3, p.getMonto());
            ps.setTimestamp(4, Timestamp.valueOf(p.getFechaPago()));
            ps.executeUpdate();

            // Actualizar estado de la reservación a 'confirmada' (ID 2)
            String sqlRes = "UPDATE reservaciones SET id_estado_reservacion = 2 WHERE id_reservacion = ?";
            try (PreparedStatement psRes = con.prepareStatement(sqlRes)) {
                psRes.setInt(1, p.getReservacion().getIdReservacion());
                psRes.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar pago: " + e.getMessage());
        }
    }

    @Override
    public Pago buscarPorId(int id) {
        String sql = "SELECT p.*, r.id_cliente, r.id_habitacion, r.fecha_entrada, r.fecha_salida, " +
                     "r.total AS res_total, m.nombre AS metodo_nombre " +
                     "FROM pagos p " +
                     "JOIN metodos_pago m ON p.id_metodo_pago = m.id_metodo_pago " +
                     "JOIN reservaciones r ON p.id_reservacion = r.id_reservacion " +
                     "WHERE p.id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar pago: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pago> obtenerTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT p.*, r.id_cliente, r.fecha_entrada, r.fecha_salida, " +
                     "r.total AS res_total, m.nombre AS metodo_nombre " +
                     "FROM pagos p " +
                     "JOIN metodos_pago m ON p.id_metodo_pago = m.id_metodo_pago " +
                     "JOIN reservaciones r ON p.id_reservacion = r.id_reservacion " +
                     "ORDER BY p.fecha_pago DESC";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener pagos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Pago p) {
        String sql = "UPDATE pagos SET monto=?, id_metodo_pago=? WHERE id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, p.getMonto());
            ps.setInt(2, p.getMetodoPago().getIdMetodoPago());
            ps.setInt(3, p.getIdPago());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar pago: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        int idReservacion = -1;
        String sqlFind = "SELECT id_reservacion FROM pagos WHERE id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sqlFind)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idReservacion = rs.getInt("id_reservacion");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar reservacion del pago: " + e.getMessage());
        }

        String sql = "DELETE FROM pagos WHERE id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

            // Si se eliminó el pago, verificar si quedan otros pagos para la reservación
            if (idReservacion != -1) {
                String sqlCount = "SELECT COUNT(*) FROM pagos WHERE id_reservacion=?";
                boolean tienePagos = false;
                try (PreparedStatement psCount = con.prepareStatement(sqlCount)) {
                    psCount.setInt(1, idReservacion);
                    try (ResultSet rs = psCount.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            tienePagos = true;
                        }
                    }
                }
                if (!tienePagos) {
                    String sqlUpdateRes = "UPDATE reservaciones SET id_estado_reservacion = 1 WHERE id_reservacion = ?";
                    try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateRes)) {
                        psUpdate.setInt(1, idReservacion);
                        psUpdate.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " + e.getMessage());
        }
    }

    /** Total acumulado de todos los pagos registrados. */
    public double obtenerTotalIngresos() {
        String sql = "SELECT COALESCE(SUM(monto), 0) FROM pagos";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Error al sumar ingresos: " + e.getMessage());
        }
        return 0;
    }

    /** Lista de métodos de pago disponibles. */
    public List<MetodoPago> obtenerMetodosPago() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pago ORDER BY id_metodo_pago";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                MetodoPago m = new MetodoPago();
                m.setIdMetodoPago(rs.getInt("id_metodo_pago"));
                m.setNombre(rs.getString("nombre"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener métodos de pago: " + e.getMessage());
        }
        return lista;
    }

    /** Guarda un nuevo método de pago. */
    public void guardarMetodoPago(MetodoPago m) {
        String sql = "INSERT INTO metodos_pago (nombre) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar método de pago: " + e.getMessage());
        }
    }

    /** Elimina un método de pago por ID. */
    public void eliminarMetodoPago(int id) {
        String sql = "DELETE FROM metodos_pago WHERE id_metodo_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar método de pago: " + e.getMessage());
        }
    }

    private Pago mapear(ResultSet rs) throws SQLException {
        Reservacion r = new Reservacion();
        r.setIdReservacion(rs.getInt("id_reservacion"));
        if (rs.getDate("fecha_entrada") != null)
            r.setFechaEntrada(rs.getDate("fecha_entrada").toLocalDate());
        if (rs.getDate("fecha_salida") != null)
            r.setFechaSalida(rs.getDate("fecha_salida").toLocalDate());
        r.setTotal(rs.getDouble("res_total"));

        MetodoPago m = new MetodoPago();
        m.setIdMetodoPago(rs.getInt("id_metodo_pago"));
        m.setNombre(rs.getString("metodo_nombre"));

        Pago p = new Pago();
        p.setIdPago(rs.getInt("id_pago"));
        p.setReservacion(r);
        p.setMetodoPago(m);
        p.setMonto(rs.getDouble("monto"));
        p.setFechaPago(rs.getTimestamp("fecha_pago").toLocalDateTime());
        return p;
    }
}