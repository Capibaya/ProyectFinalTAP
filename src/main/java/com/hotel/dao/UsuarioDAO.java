package com.hotel.dao;

import com.hotel.model.*;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO (Data Access Object) para la entidad {@link UsuarioSistema}.
 * <p>
 * Proporciona operaciones CRUD sobre la tabla {@code usuarios_sistema} de la base de datos,
 * realizando joins con las tablas {@code empleados} y {@code roles} para obtener información completa.
 * </p>
 * <p>
 * Patrón de diseño: <b>DAO (Data Access Object)</b> — centraliza el acceso a datos de usuarios
 * e implementa la interfaz genérica {@link GenericDAO}.
 * </p>
 */
public class UsuarioDAO implements GenericDAO<UsuarioSistema> {

    /**
     * Conexión activa a la base de datos obtenida mediante el Singleton {@link DatabaseConnection}.
     */
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();

    /**
     * Persiste un nuevo usuario en la tabla {@code usuarios_sistema}.
     *
     * @param u el objeto {@link UsuarioSistema} con los datos a insertar;
     *          debe contener un {@link Empleado} y un {@link Rol} válidos.
     */
    @Override
    public void guardar(UsuarioSistema u) {
        String sql = "INSERT INTO usuarios_sistema (id_empleado, usuario, password_hash, id_rol) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getEmpleado().getIdEmpleado());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getPasswordHash());
            ps.setInt(4, u.getRol().getIdRol());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    /**
     * Busca un usuario del sistema por su identificador único.
     *
     * @param id el identificador del usuario ({@code id_usuario}) a buscar.
     * @return el {@link UsuarioSistema} encontrado, o {@code null} si no existe.
     */
    @Override
    public UsuarioSistema buscarPorId(int id) {
        String sql = "SELECT u.*, e.nombre, e.apellido, r.nombre_rol " +
                "FROM usuarios_sistema u " +
                "JOIN empleados e ON u.id_empleado = e.id_empleado " +
                "JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.id_usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un usuario del sistema por su nombre de usuario (login).
     *
     * @param usuario el nombre de usuario a buscar (campo {@code usuario} en la BD).
     * @return el {@link UsuarioSistema} encontrado, o {@code null} si no existe.
     */
    public UsuarioSistema buscarPorUsuario(String usuario) {
        String sql = "SELECT u.*, e.nombre, e.apellido, r.nombre_rol " +
                "FROM usuarios_sistema u " +
                "JOIN empleados e ON u.id_empleado = e.id_empleado " +
                "JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar por usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Recupera la lista completa de usuarios del sistema registrados en la base de datos.
     *
     * @return lista de objetos {@link UsuarioSistema}; puede estar vacía si no hay registros.
     */
    @Override
    public List<UsuarioSistema> obtenerTodos() {
        List<UsuarioSistema> lista = new ArrayList<>();
        String sql = "SELECT u.*, e.nombre, e.apellido, r.nombre_rol " +
                "FROM usuarios_sistema u " +
                "JOIN empleados e ON u.id_empleado = e.id_empleado " +
                "JOIN roles r ON u.id_rol = r.id_rol";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     * <p>
     * Actualiza los campos {@code usuario}, {@code password_hash} e {@code id_rol}.
     * </p>
     *
     * @param u el objeto {@link UsuarioSistema} con los datos actualizados;
     *          debe contener un {@code idUsuario} válido.
     */
    @Override
    public void actualizar(UsuarioSistema u) {
        String sql = "UPDATE usuarios_sistema SET usuario=?, password_hash=?, id_rol=? WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getPasswordHash());
            ps.setInt(3, u.getRol().getIdRol());
            ps.setInt(4, u.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    /**
     * Elimina un usuario del sistema de la base de datos según su identificador.
     *
     * @param id el identificador del usuario ({@code id_usuario}) a eliminar.
     */
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios_sistema WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link UsuarioSistema}.
     * <p>
     * Método auxiliar interno que construye también los objetos anidados
     * {@link Empleado} y {@link Rol} a partir de los datos del join.
     * </p>
     *
     * @param rs el {@link ResultSet} posicionado en la fila a mapear.
     * @return un objeto {@link UsuarioSistema} completamente poblado.
     * @throws SQLException si ocurre un error al leer las columnas del {@link ResultSet}.
     */
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
