package com.hotel.dao;

import com.hotel.model.TipoHabitacion;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TipoHabitacionDAO implements GenericDAO<TipoHabitacion> {
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();
    @Override
    public void guardar(TipoHabitacion t) {
        String sql = "INSERT INTO tipos_habitacion (nombre, capacidad,
        precio_noche) VALUES (?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getCapacidad());
            ps.setDouble(3, t.getPrecioNoche());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar tipo habitacion: " +
                    e.getMessage());
        }
    }
    @Override
    public TipoHabitacion buscarPorId(int id) {
        String sql = "SELECT * FROM tipos_habitacion WHERE id_tipo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar tipo habitacion: " +
                    e.getMessage());
        }
        return null;
    }
    @Override
    public List<TipoHabitacion> obtenerTodos() {
        List<TipoHabitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos_habitacion";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener tipos habitacion: " +
                    e.getMessage());
        }
        return lista;
    }
    @Override
    public void actualizar(TipoHabitacion t) {
        String sql = "UPDATE tipos_habitacion SET nombre=?, capacidad=?,
        precio_noche=? WHERE id_tipo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getCapacidad());
            ps.setDouble(3, t.getPrecioNoche());
            ps.setInt(4, t.getIdTipo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar tipo habitacion: " +
                    e.getMessage());
        }
    }
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tipos_habitacion WHERE id_tipo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar tipo habitacion: " +
                    e.getMessage());
        }
    }
    private TipoHabitacion mapear(ResultSet rs) throws SQLException {
        TipoHabitacion t = new TipoHabitacion();
        t.setIdTipo(rs.getInt("id_tipo"));
        t.setNombre(rs.getString("nombre"));
        t.setCapacidad(rs.getInt("capacidad"));
        t.setPrecioNoche(rs.getDouble("precio_noche"));
        return t;
    }
}