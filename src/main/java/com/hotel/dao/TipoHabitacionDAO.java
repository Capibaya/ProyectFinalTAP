package com.hotel.dao;

import com.hotel.model.TipoHabitacion;
import com.hotel.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO (Data Access Object) para la entidad {@link TipoHabitacion}.
 * <p>
 * Patrón de diseño: <b>DAO</b> — encapsula todas las operaciones de acceso a la tabla
 * {@code tipos_habitacion} en la base de datos, desacoplando la lógica de persistencia
 * del resto de la aplicación.
 * </p>
 * <p>
 * Obtiene la conexión a través del Singleton {@link DatabaseConnection}.
 * </p>
 */
public class TipoHabitacionDAO implements GenericDAO<TipoHabitacion> {

    /**
     * Conexión activa a la base de datos, obtenida mediante el Singleton {@link DatabaseConnection}.
     */
    private final Connection con =
            DatabaseConnection.getInstancia().getConexion();

    /**
     * Inserta un nuevo tipo de habitación en la tabla {@code tipos_habitacion}.
     *
     * @param t El objeto {@link TipoHabitacion} con los datos a persistir.
     */
    @Override
    public void guardar(TipoHabitacion t) {
        String sql = "INSERT INTO tipos_habitacion (nombre, capacidad, precio_noche) VALUES (?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getCapacidad());
            ps.setDouble(3, t.getPrecioNoche());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar tipo habitacion: " + e.getMessage());
        }
    }

    /**
     * Busca un tipo de habitación en la base de datos por su identificador único.
     *
     * @param id Identificador único del tipo de habitación ({@code id_tipo}).
     * @return El objeto {@link TipoHabitacion} encontrado, o {@code null} si no existe.
     */
    @Override
    public TipoHabitacion buscarPorId(int id) {
        String sql = "SELECT * FROM tipos_habitacion WHERE id_tipo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar tipo habitacion: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los tipos de habitación registrados en la base de datos.
     *
     * @return Lista de objetos {@link TipoHabitacion}; lista vacía si no hay registros.
     */
    @Override
    public List<TipoHabitacion> obtenerTodos() {
        List<TipoHabitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos_habitacion";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al obtener tipos habitacion: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza los datos de un tipo de habitación existente en la base de datos.
     *
     * @param t El objeto {@link TipoHabitacion} con los valores actualizados.
     *          Debe contener un {@code idTipo} válido.
     */
    @Override
    public void actualizar(TipoHabitacion t) {
        String sql = "UPDATE tipos_habitacion SET nombre=?, capacidad=?, precio_noche=? WHERE id_tipo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getCapacidad());
            ps.setDouble(3, t.getPrecioNoche());
            ps.setInt(4, t.getIdTipo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar tipo habitacion: " + e.getMessage());
        }
    }

    /**
     * Elimina el registro de un tipo de habitación de la base de datos por su identificador único.
     *
     * @param id Identificador único del tipo de habitación a eliminar ({@code id_tipo}).
     */
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tipos_habitacion WHERE id_tipo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar tipo habitacion: " + e.getMessage());
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link TipoHabitacion}.
     * <p>
     * Método auxiliar privado utilizado internamente por los métodos de consulta.
     * </p>
     *
     * @param rs El {@link ResultSet} posicionado en la fila a convertir.
     * @return Un objeto {@link TipoHabitacion} con los datos de la fila actual.
     * @throws SQLException Si ocurre un error al leer las columnas del {@link ResultSet}.
     */
    private TipoHabitacion mapear(ResultSet rs) throws SQLException {
        TipoHabitacion t = new TipoHabitacion();
        t.setIdTipo(rs.getInt("id_tipo"));
        t.setNombre(rs.getString("nombre"));
        t.setCapacidad(rs.getInt("capacidad"));
        t.setPrecioNoche(rs.getDouble("precio_noche"));
        return t;
    }
}