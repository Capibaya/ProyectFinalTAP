package com.hotel.dao;

import com.hotel.model.*;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO implements GenericDAO<UsuarioSistema> {
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();
    @Override
    public void guardar(UsuarioSistema u) {
        String sql = "INSERT INTO usuarios_sistema (id_empleado, usuario,
        password_hash, id_rol) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getEmpleado().getIdEmpleado());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getPasswordHash());
            ps.setInt(4, u.getRol().getIdRol());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " +
                    e.getMessage());
        }
    }
    @Override
    public UsuarioSistema buscarPorId(int id) {
        String sql = "SELECT u.*, e.nombre, e.apellido, r.nombre_rol " +
                "FROM usuarios_sistema u " +
                "JOIN empleados e ON u.id_empleado = e.id_empleado "
                +
                "JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.id_usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " +
                    e.getMessage());
        }
        return null;
    }
    public UsuarioSistema buscarPorUsuario(String usuario) {
        String sql = "SELECT u.*, e.nombre, e.apellido, r.nombre_rol " +
                "FROM usuarios_sistema u " +
                "JOIN empleados e ON u.id_empleado = e.id_empleado "
                +
                "JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar por usuario: " +
                    e.getMessage());
        }
        return null;
    }
    @Override
    public List<UsuarioSistema> obtenerTodos() {
        List<UsuarioSistema> lista = new ArrayList<>();
        String sql = "SELECT u.*, e.nombre, e.apellido, r.nombre_rol " +
                "FROM usuarios_sistema u " +
                "JOIN empleados e ON u.id_empleado = e.id_empleado "
                +
                "JOIN roles r ON u.id_rol = r.id_rol";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " +
                    e.getMessage());
        }
        return lista;
    }
    @Override
    public void actualizar(UsuarioSistema u) {
        String sql = "UPDATE usuarios_sistema SET usuario=?,
        password_hash=?, id_rol=? WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getPasswordHash());
            ps.setInt(3, u.getRol().getIdRol());
            ps.setInt(4, u.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " +
                    e.getMessage());
        }
    }
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios_sistema WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " +
                    e.getMessage());
        }
    }
    private UsuarioSistema mapear(ResultSet rs) throws SQLException {
        Empleado emp = new Empleado();
        emp.setIdEmpleado(rs.getInt("id_empleado"));
        emp.setNombre(rs.getString("nombre"));
        emp.setApellido(rs.getString("apellido"));
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("id_rol"));
        rol.setNombreRol(rs.getString("nombre_rol"));
        UsuarioSistema u = new UsuarioSistema();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setUsuario(rs.getString("usuario"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setEmpleado(emp);
        u.setRol(rol);
        return u;
    }
}
