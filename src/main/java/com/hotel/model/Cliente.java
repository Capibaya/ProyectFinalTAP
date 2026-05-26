package com.hotel.model;

/**
 * Modelo que representa a un cliente del hotel.
 * Almacena la información personal y de contacto del cliente,
 * incluyendo su dirección completa.
 */
public class Cliente {

    /** Identificador único del cliente en la base de datos. */
    private int idCliente;

    /** Nombre(s) del cliente. */
    private String nombre;

    /** Apellido(s) del cliente. */
    private String apellido;

    /** Dirección de correo electrónico del cliente. */
    private String correo;

    /** Número de teléfono de contacto del cliente. */
    private String telefono;

    /** Nombre de la calle donde reside el cliente. */
    private String calle;

    /** Ciudad de residencia del cliente. */
    private String ciudad;

    /** Estado o provincia de residencia del cliente. */
    private String estado;

    /** Código postal del domicilio del cliente. */
    private String cp;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code Cliente}.
     */
    public Cliente() {}

    /**
     * Constructor con todos los campos del cliente.
     *
     * @param idCliente identificador único del cliente
     * @param nombre    nombre(s) del cliente
     * @param apellido  apellido(s) del cliente
     * @param correo    correo electrónico del cliente
     * @param telefono  número de teléfono del cliente
     * @param calle     calle del domicilio del cliente
     * @param ciudad    ciudad del domicilio del cliente
     * @param estado    estado o provincia del domicilio del cliente
     * @param cp        código postal del domicilio del cliente
     */
    public Cliente(int idCliente, String nombre, String apellido, String correo,
                   String telefono, String calle, String ciudad, String estado, String cp) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.calle = calle;
        this.ciudad = ciudad;
        this.estado = estado;
        this.cp = cp;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return identificador del cliente
     */
    public int getIdCliente() { return idCliente; }

    /**
     * Establece el identificador único del cliente.
     *
     * @param idCliente nuevo identificador del cliente
     */
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return nombre del cliente
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre nuevo nombre del cliente
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el apellido del cliente.
     *
     * @return apellido del cliente
     */
    public String getApellido() { return apellido; }

    /**
     * Establece el apellido del cliente.
     *
     * @param apellido nuevo apellido del cliente
     */
    public void setApellido(String apellido) { this.apellido = apellido; }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return correo electrónico del cliente
     */
    public String getCorreo() { return correo; }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo nuevo correo electrónico del cliente
     */
    public void setCorreo(String correo) { this.correo = correo; }

    /**
     * Obtiene el teléfono de contacto del cliente.
     *
     * @return teléfono del cliente
     */
    public String getTelefono() { return telefono; }

    /**
     * Establece el teléfono de contacto del cliente.
     *
     * @param telefono nuevo número de teléfono del cliente
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Obtiene la calle del domicilio del cliente.
     *
     * @return calle del domicilio
     */
    public String getCalle() { return calle; }

    /**
     * Establece la calle del domicilio del cliente.
     *
     * @param calle nueva calle del domicilio
     */
    public void setCalle(String calle) { this.calle = calle; }

    /**
     * Obtiene la ciudad del domicilio del cliente.
     *
     * @return ciudad del domicilio
     */
    public String getCiudad() { return ciudad; }

    /**
     * Establece la ciudad del domicilio del cliente.
     *
     * @param ciudad nueva ciudad del domicilio
     */
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    /**
     * Obtiene el estado o provincia del domicilio del cliente.
     *
     * @return estado del domicilio
     */
    public String getEstado() { return estado; }

    /**
     * Establece el estado o provincia del domicilio del cliente.
     *
     * @param estado nuevo estado del domicilio
     */
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Obtiene el código postal del domicilio del cliente.
     *
     * @return código postal del domicilio
     */
    public String getCp() { return cp; }

    /**
     * Establece el código postal del domicilio del cliente.
     *
     * @param cp nuevo código postal del domicilio
     */
    public void setCp(String cp) { this.cp = cp; }
}
