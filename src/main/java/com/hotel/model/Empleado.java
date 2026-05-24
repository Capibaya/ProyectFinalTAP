package com.hotel.model;

import java.time.LocalDate;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private Puesto puesto;
    private double salario;
    private String turno;
    private LocalDate fechaContratacion;

    public Empleado() {}

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

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
}
