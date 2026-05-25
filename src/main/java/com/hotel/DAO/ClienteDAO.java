package com.hotel.DAO;

import com.hotel.model.Cliente;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ClienteDAO implements GenericDAO<Cliente> {
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();
    @Override
    public void guardar(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo,
        telefono, calle, ciudad, estado, cp) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCalle());
            ps.setString(6, c.getCiudad());
            ps.setString(7, c.getEstado());
            ps.setString(8, c.getCp());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " +
                    e.getMessage());
        }
    }
    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " +
                    e.getMessage());
        }
        return null;
    }
    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " +
                    e.getMessage());
        }
        return lista;
    }
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? OR
        apellido LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " +
                    e.getMessage());
        }
        return lista;
    }
    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, correo=?,
        telefono=?, calle=?, ciudad=?, estado=?, cp=? WHERE id_cliente=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCalle());
            ps.setString(6, c.getCiudad());
            ps.setString(7, c.getEstado());
            ps.setString(8, c.getCp());
            ps.setInt(9, c.getIdCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " +
                    e.getMessage());
        }
    }
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " +
                    e.getMessage());
        }
    }
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNombre(rs.getString("nombre"));
        c.setApellido(rs.getString("apellido"));
        c.setCorreo(rs.getString("correo"));
        c.setTelefono(rs.getString("telefono"));
        c.setCalle(rs.getString("calle"));
        c.setCiudad(rs.getString("ciudad"));
        c.setEstado(rs.getString("estado"));
        c.setCp(rs.getString("cp"));
        return c;
    }
}
