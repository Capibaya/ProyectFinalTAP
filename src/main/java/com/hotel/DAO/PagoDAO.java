package com.hotel.DAO;

import com.hotel.model.*;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PagoDAO implements GenericDAO<Pago> {
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();
    @Override
    public void guardar(Pago p) {
        String sql = "INSERT INTO pagos (id_reservacion, id_metodo_pago,
        monto, fecha_pago) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getReservacion().getIdReservacion());
            ps.setInt(2, p.getMetodoPago().getIdMetodoPago());
            ps.setDouble(3, p.getMonto());
            ps.setTimestamp(4, Timestamp.valueOf(p.getFechaPago()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar pago: " +
                    e.getMessage());
        }
    }
    @Override
    public Pago buscarPorId(int id) {
        String sql = "SELECT p.*, m.nombre AS metodo_nombre FROM pagos p
        " +
        "JOIN metodos_pago m ON p.id_metodo_pago =
        m.id_metodo_pago " +
        "WHERE p.id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar pago: " +
                    e.getMessage());
        }
        return null;
    }
    @Override
    public List<Pago> obtenerTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT p.*, m.nombre AS metodo_nombre FROM pagos p
        " +
        "JOIN metodos_pago m ON p.id_metodo_pago =
        m.id_metodo_pago";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener pagos: " +
                    e.getMessage());
        }
        return lista;
    }
    @Override
    public void actualizar(Pago p) {
        String sql = "UPDATE pagos SET monto=?, id_metodo_pago=? WHERE
        id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, p.getMonto());
            ps.setInt(2, p.getMetodoPago().getIdMetodoPago());
            ps.setInt(3, p.getIdPago());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar pago: " +
                    e.getMessage());
        }
    }
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM pagos WHERE id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " +
                    e.getMessage());
        }
    }
    private Pago mapear(ResultSet rs) throws SQLException {
        Reservacion r = new Reservacion();
        r.setIdReservacion(rs.getInt("id_reservacion"));
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