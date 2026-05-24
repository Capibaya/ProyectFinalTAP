package com.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Patron Singleton — garantiza una sola instancia de conexion
 * a la base de datos durante toda la ejecucion de la aplicacion.
 */
public class DatabaseConnection {

    // Datos de conexion
    private static final String URL      = "jdbc:mysql://localhost:3306/hotel_reservaciones?useSSL=false&serverTimezone=America/Mexico_City";
    private static final String USER     = "root";
    private static final String PASSWORD = "tu_contraseña";

    // Unica instancia de la clase
    private static DatabaseConnection instancia;

    // Conexion activa
    private Connection conexion;

    /**
     * Constructor privado — impide instanciacion externa.
     */
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la BD: " + e.getMessage());
        }
    }

    /**
     * Devuelve la unica instancia. Si no existe la crea.
     * Sincronizado para entornos multihilo.
     */
    public static synchronized DatabaseConnection getInstancia() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    /**
     * Devuelve la conexion activa.
     * Si la conexion se cerro, la restablece.
     */
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                instancia = null;
                return getInstancia().conexion;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar conexion: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * Cierra la conexion activa.
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                instancia = null;
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexion: " + e.getMessage());
        }
    }
}
