package com.hotel.dao;

import com.hotel.model.*;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class HabitacionDAO implements GenericDAO<Habitacion> {
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();
    @Override
    public void guardar(Habitacion h) {
        String sql = "INSERT INTO habitaciones (numero, piso,
        descripcion, id_tipo, id_estado_habitacion) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getNumero());
            ps.setInt(2, h.getPiso());
            ps.setString(3, h.getDescripcion());
            ps.setInt(4, h.getTipoHabitacion().getIdTipo());
            ps.setInt(5,
                    h.getEstadoHabitacion().getIdEstadoHabitacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar habitacion: " +
                    e.getMessage());
        }
    }
    @Override
    public Habitacion buscarPorId(int id) {
        String sql = "SELECT h.*, t.nombre AS tipo_nombre, t.capacidad,
        t.precio_noche, " +
        "e.nombre AS estado_nombre FROM habitaciones h " +
                "JOIN tipos_habitacion t ON h.id_tipo = t.id_tipo "
                +
                "JOIN estados_habitacion e ON h.id_estado_habitacion
                = e.id_estado_habitacion " +
        "WHERE h.id_habitacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar habitacion: " +
                    e.getMessage());
        }
        return null;
    }
    @Override
    public List<Habitacion> obtenerTodos() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT h.*, t.nombre AS tipo_nombre, t.capacidad,
        t.precio_noche, " +
        "e.nombre AS estado_nombre FROM habitaciones h " +
                "JOIN tipos_habitacion t ON h.id_tipo = t.id_tipo "
                +
                "JOIN estados_habitacion e ON h.id_estado_habitacion
                = e.id_estado_habitacion";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones: " +
                    e.getMessage());
        }
        return lista;
    }
    public List<Habitacion> obtenerPorEstado(String estado) {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT h.*, t.nombre AS tipo_nombre, t.capacidad,
        t.precio_noche, " +
        "e.nombre AS estado_nombre FROM habitaciones h " +
                "JOIN tipos_habitacion t ON h.id_tipo = t.id_tipo "
                +
                "JOIN estados_habitacion e ON h.id_estado_habitacion
                = e.id_estado_habitacion " +
        "WHERE e.nombre=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al filtrar habitaciones: " +
                    e.getMessage());
        }
        return lista;
    }
    public int contarPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM habitaciones h " +
                "JOIN estados_habitacion e ON h.id_estado_habitacion
                = e.id_estado_habitacion " +
        "WHERE e.nombre=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error al contar habitaciones: " +
                    e.getMessage());
        }
        return 0;
    }
    @Override
    public void actualizar(Habitacion h) {
        String sql = "UPDATE habitaciones SET numero=?, piso=?,
        descripcion=?, id_tipo=?, id_estado_habitacion=? WHERE id_habitacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getNumero());
            ps.setInt(2, h.getPiso());
            ps.setString(3, h.getDescripcion());
            ps.setInt(4, h.getTipoHabitacion().getIdTipo());
            ps.setInt(5,
                    h.getEstadoHabitacion().getIdEstadoHabitacion());
            ps.setInt(6, h.getIdHabitacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar habitacion: " +
                    e.getMessage());
        }
    }
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM habitaciones WHERE id_habitacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar habitacion: " +
                    e.getMessage());
        }
    }
    private Habitacion mapear(ResultSet rs) throws SQLException {
        TipoHabitacion tipo = new TipoHabitacion();
        tipo.setIdTipo(rs.getInt("id_tipo"));
        tipo.setNombre(rs.getString("tipo_nombre"));
        tipo.setCapacidad(rs.getInt("capacidad"));
        tipo.setPrecioNoche(rs.getDouble("precio_noche"));
        EstadoHabitacion estado = new EstadoHabitacion();
        estado.setIdEstadoHabitacion(rs.getInt("id_estado_habitacion"));
        estado.setNombre(rs.getString("estado_nombre"));
        Habitacion h = new Habitacion();
        h.setIdHabitacion(rs.getInt("id_habitacion"));
        h.setNumero(rs.getString("numero"));
        h.setPiso(rs.getInt("piso"));
        h.setDescripcion(rs.getString("descripcion"));
        h.setTipoHabitacion(tipo);
        h.setEstadoHabitacion(estado);
        return h;
    }
}
