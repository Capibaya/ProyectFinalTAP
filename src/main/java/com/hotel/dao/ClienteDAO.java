package com.hotel.dao;

import com.hotel.model.Cliente;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO (Data Access Object) para la entidad {@link Cliente}.
 * <p>
 * Patrón de diseño: <b>DAO</b> — encapsula todas las operaciones de acceso a la tabla
 * {@code clientes} en la base de datos, aislando la lógica de persistencia del resto
 * de la aplicación.
 * </p>
 * <p>
 * Obtiene la conexión a través del Singleton {@link DatabaseConnection}.
 * </p>
 */
public class ClienteDAO implements GenericDAO<Cliente> {

    /**
     * Conexión activa a la base de datos, obtenida mediante el Singleton {@link DatabaseConnection}.
     */
    private final Connection con = DatabaseConnection.getInstancia().getConexion();

    /**
     * Inserta un nuevo cliente en la tabla {@code clientes}.
     *
     * @param c El objeto {@link Cliente} con los datos a persistir.
     */
    @Override
    public void guardar(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo, telefono, calle, ciudad, estado, cp) VALUES (?,?,?,?,?,?,?,?)";
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
            System.err.println("Error al guardar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca un cliente en la base de datos por su identificador único.
     *
     * @param id Identificador único del cliente ({@code id_cliente}).
     * @return El objeto {@link Cliente} encontrado, o {@code null} si no existe.
     */
    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los clientes registrados en la base de datos.
     *
     * @return Lista de objetos {@link Cliente}; lista vacía si no hay registros.
     */
    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca clientes cuyo nombre o apellido contenga la cadena indicada (búsqueda parcial).
     *
     * @param nombre Cadena de texto a buscar dentro de los campos {@code nombre} y {@code apellido}.
     * @return Lista de {@link Cliente} que coinciden con el criterio; lista vacía si no hay resultados.
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? OR apellido LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param c El objeto {@link Cliente} con los valores actualizados. Debe contener un {@code idCliente} válido.
     */
    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, correo=?, telefono=?, calle=?, ciudad=?, estado=?, cp=? WHERE id_cliente=?";
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
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Elimina el registro de un cliente de la base de datos por su identificador único.
     *
     * @param id Identificador único del cliente a eliminar ({@code id_cliente}).
     */
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Cliente}.
     * <p>
     * Método auxiliar privado utilizado internamente por los métodos de consulta.
     * </p>
     *
     * @param rs El {@link ResultSet} posicionado en la fila a convertir.
     * @return Un objeto {@link Cliente} con los datos de la fila actual.
     * @throws SQLException Si ocurre un error al leer las columnas del {@link ResultSet}.
     */
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