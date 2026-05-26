package com.hotel.model;

import java.time.LocalDate;

/**
 * Modelo que representa a un empleado del hotel.
 * Contiene la información laboral y personal del empleado,
 * incluyendo su puesto, salario, turno y fecha de contratación.
 */
public class Empleado {

    /** Identificador único del empleado en la base de datos. */
    private int idEmpleado;

    /** Nombre(s) del empleado. */
    private String nombre;

    /** Apellido(s) del empleado. */
    private String apellido;

    /** Puesto o cargo que desempeña el empleado dentro del hotel. */
    private Puesto puesto;

    /** Salario del empleado expresado en moneda local. */
    private double salario;

    /** Turno de trabajo asignado al empleado (p. ej. matutino, vespertino, nocturno). */
    private String turno;

    /** Fecha en la que el empleado fue contratado. */
    private LocalDate fechaContratacion;

    /**
     * Constructor por defecto. Crea una instancia vacía de {@code Empleado}.
     */
    public Empleado() {}

    /**
     * Constructor con todos los campos del empleado.
     *
     * @param idEmpleado        identificador único del empleado
     * @param nombre            nombre(s) del empleado
     * @param apellido          apellido(s) del empleado
     * @param puesto            puesto o cargo del empleado
     * @param salario           salario del empleado
     * @param turno             turno de trabajo del empleado
     * @param fechaContratacion fecha de contratación del empleado
     */
    public Empleado(int idEmpleado, String nombre, String apellido, Puesto puesto,
                    double salario, String turno, LocalDate fechaContratacion) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.salario = salario;
        this.turno = turno;
        this.fechaContratacion = fechaContratacion;
    }

    /**
     * Obtiene el identificador único del empleado.
     *
     * @return identificador del empleado
     */
    public int getIdEmpleado() { return idEmpleado; }

    /**
     * Establece el identificador único del empleado.
     *
     * @param idEmpleado nuevo identificador del empleado
     */
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return nombre del empleado
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre nuevo nombre del empleado
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el apellido del empleado.
     *
     * @return apellido del empleado
     */
    public String getApellido() { return apellido; }

    /**
     * Establece el apellido del empleado.
     *
     * @param apellido nuevo apellido del empleado
     */
    public void setApellido(String apellido) { this.apellido = apellido; }

    /**
     * Obtiene el puesto o cargo del empleado.
     *
     * @return puesto del empleado
     */
    public Puesto getPuesto() { return puesto; }

    /**
     * Establece el puesto o cargo del empleado.
     *
     * @param puesto nuevo puesto del empleado
     */
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }

    /**
     * Obtiene el salario del empleado.
     *
     * @return salario del empleado
     */
    public double getSalario() { return salario; }

    /**
     * Establece el salario del empleado.
     *
     * @param salario nuevo salario del empleado
     */
    public void setSalario(double salario) { this.salario = salario; }

    /**
     * Obtiene el turno de trabajo del empleado.
     *
     * @return turno de trabajo
     */
    public String getTurno() { return turno; }

    /**
     * Establece el turno de trabajo del empleado.
     *
     * @param turno nuevo turno de trabajo
     */
    public void setTurno(String turno) { this.turno = turno; }

    /**
     * Obtiene la fecha de contratación del empleado.
     *
     * @return fecha de contratación
     */
    public LocalDate getFechaContratacion() { return fechaContratacion; }

    /**
     * Establece la fecha de contratación del empleado.
     *
     * @param fechaContratacion nueva fecha de contratación
     */
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
}
