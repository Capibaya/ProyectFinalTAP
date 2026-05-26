package com.hotel.model;

/**
 * Modelo que representa un usuario del sistema de gestión del hotel.
 * Asocia un {@link Empleado} con sus credenciales de acceso (nombre de usuario
 * y contraseña hasheada) y el {@link Rol} que determina sus permisos dentro
 * del sistema.
 */
public class UsuarioSistema {

    /** Identificador único del usuario en la base de datos. */
    private int idUsuario;

    /** Empleado al que pertenece este usuario del sistema. */
    private Empleado empleado;

    /** Nombre de usuario utilizado para iniciar sesión. */
    private String usuario;

    /** Hash de la contraseña del usuario (no se almacena en texto plano). */
    private String passwordHash;

    /** Rol asignado al usuario que define sus permisos en el sistema. */
    private Rol rol;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code UsuarioSistema}.
     */
    public UsuarioSistema() {}

    /**
     * Constructor con todos los campos del usuario del sistema.
     *
     * @param idUsuario    identificador único del usuario
     * @param empleado     empleado asociado al usuario
     * @param usuario      nombre de usuario para inicio de sesión
     * @param passwordHash hash de la contraseña del usuario
     * @param rol          rol asignado al usuario
     */
    public UsuarioSistema(int idUsuario, Empleado empleado, String usuario,
                          String passwordHash, Rol rol) {
        this.idUsuario = idUsuario;
        this.empleado = empleado;
        this.usuario = usuario;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return identificador del usuario
     */
    public int getIdUsuario() { return idUsuario; }

    /**
     * Establece el identificador único del usuario.
     *
     * @param idUsuario nuevo identificador del usuario
     */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    /**
     * Obtiene el empleado asociado a este usuario del sistema.
     *
     * @return empleado asociado
     */
    public Empleado getEmpleado() { return empleado; }

    /**
     * Establece el empleado asociado a este usuario del sistema.
     *
     * @param empleado nuevo empleado asociado
     */
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    /**
     * Obtiene el nombre de usuario para el inicio de sesión.
     *
     * @return nombre de usuario
     */
    public String getUsuario() { return usuario; }

    /**
     * Establece el nombre de usuario para el inicio de sesión.
     *
     * @param usuario nuevo nombre de usuario
     */
    public void setUsuario(String usuario) { this.usuario = usuario; }

    /**
     * Obtiene el hash de la contraseña del usuario.
     *
     * @return hash de la contraseña
     */
    public String getPasswordHash() { return passwordHash; }

    /**
     * Establece el hash de la contraseña del usuario.
     *
     * @param passwordHash nuevo hash de la contraseña
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    /**
     * Obtiene el rol asignado al usuario.
     *
     * @return rol del usuario
     */
    public Rol getRol() { return rol; }

    /**
     * Establece el rol asignado al usuario.
     *
     * @param rol nuevo rol del usuario
     */
    public void setRol(Rol rol) { this.rol = rol; }
}
