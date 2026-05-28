package com.hotel.dao;

import com.hotel.model.*;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservacionDAO implements GenericDAO<Reservacion> {
    private final Connection con = DatabaseConnection.getInstancia().getConexion();

    private static final String SELECT_BASE =
        "SELECT r.*, c.nombre AS cli_nombre, c.apellido AS cli_apellido, " +
        "h.numero AS hab_numero, e.nombre AS estado_nombre " +
        "FROM reservaciones r " +
        "JOIN clientes c ON r.id_cliente = c.id_cliente " +
        "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
        "JOIN estados_reservacion e ON r.id_estado_reservacion = e.id_estado_reservacion";

    @Override
    public void guardar(Reservacion r) {
        String sql = "INSERT INTO reservaciones (id_cliente, id_habitacion, fecha_reservacion, fecha_entrada, fecha_salida, numero_huespedes, total, id_estado_reservacion) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getCliente().getIdCliente());
            ps.setInt(2, r.getHabitacion().getIdHabitacion());
            ps.setTimestamp(3, Timestamp.valueOf(r.getFechaReservacion()));
            ps.setDate(4, Date.valueOf(r.getFechaEntrada()));
            ps.setDate(5, Date.valueOf(r.getFechaSalida()));
            ps.setInt(6, r.getNumeroHuespedes());
            ps.setDouble(7, r.getTotal());
            ps.setInt(8, r.getEstadoReservacion().getIdEstadoReservacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar reservacion: " + e.getMessage());
        }
    }

    @Override
    public Reservacion buscarPorId(int id) {
        String sql = SELECT_BASE + " WHERE r.id_reservacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar reservacion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reservacion> obtenerTodos() {
        List<Reservacion> lista = new ArrayList<>();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SELECT_BASE)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener reservaciones: " + e.getMessage());
        }
        return lista;
    }

    public List<Reservacion> obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        List<Reservacion> lista = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE r.fecha_entrada >= ? AND r.fecha_salida <= ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(desde));
            ps.setDate(2, Date.valueOf(hasta));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al filtrar reservaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Reservacion r) {
        String sql = "UPDATE reservaciones SET id_estado_reservacion=?, total=? WHERE id_reservacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getEstadoReservacion().getIdEstadoReservacion());
            ps.setDouble(2, r.getTotal());
            ps.setInt(3, r.getIdReservacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar reservacion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM reservaciones WHERE id_reservacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar reservacion: " + e.getMessage());
        }
    }

    /**
     * Busca el id del estado de reservación dado su nombre (p. ej. "cancelada", "confirmada").
     *
     * @param nombre nombre del estado a buscar
     * @return id del estado encontrado, o -1 si no existe
     */
    public int buscarIdEstadoReservacionPorNombre(String nombre) {
        String sql = "SELECT id_estado_reservacion FROM estados_reservacion WHERE nombre=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error al buscar id de estado reservacion: " + e.getMessage());
        }
        return -1;
    }

    private Reservacion mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNombre(rs.getString("cli_nombre"));
        c.setApellido(rs.getString("cli_apellido"));
        Habitacion h = new Habitacion();
        h.setIdHabitacion(rs.getInt("id_habitacion"));
        h.setNumero(rs.getString("hab_numero"));
        EstadoReservacion estado = new EstadoReservacion();
        estado.setIdEstadoReservacion(rs.getInt("id_estado_reservacion"));
        estado.setNombre(rs.getString("estado_nombre"));
        Reservacion r = new Reservacion();
        r.setIdReservacion(rs.getInt("id_reservacion"));
        r.setCliente(c);
        r.setHabitacion(h);
        r.setFechaReservacion(rs.getTimestamp("fecha_reservacion").toLocalDateTime());
        r.setFechaEntrada(rs.getDate("fecha_entrada").toLocalDate());
        r.setFechaSalida(rs.getDate("fecha_salida").toLocalDate());
        r.setNumeroHuespedes(rs.getInt("numero_huespedes"));
        r.setTotal(rs.getDouble("total"));
        r.setEstadoReservacion(estado);
        return r;
    }
}