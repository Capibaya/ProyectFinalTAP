package com.hotel.model;

/**
 * Modelo que representa un rol de acceso dentro del sistema del hotel.
 * Define los distintos niveles de permisos que puede tener un
 * {@link UsuarioSistema} (p. ej. administrador, recepcionista, cajero).
 */
public class Rol {

    /** Identificador único del rol en la base de datos. */
    private int idRol;

    /** Nombre descriptivo del rol (p. ej. "Administrador", "Recepcionista"). */
    private String nombreRol;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code Rol}.
     */
    public Rol() {}

    /**
     * Constructor con todos los campos del rol.
     *
     * @param idRol     identificador único del rol
     * @param nombreRol nombre descriptivo del rol
     */
    public Rol(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    /**
     * Obtiene el identificador único del rol.
     *
     * @return identificador del rol
     */
    public int getIdRol() { return idRol; }

    /**
     * Establece el identificador único del rol.
     *
     * @param idRol nuevo identificador del rol
     */
    public void setIdRol(int idRol) { this.idRol = idRol; }

    /**
     * Obtiene el nombre descriptivo del rol.
     *
     * @return nombre del rol
     */
    public String getNombreRol() { return nombreRol; }

    /**
     * Establece el nombre descriptivo del rol.
     *
     * @param nombreRol nuevo nombre del rol
     */
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}
