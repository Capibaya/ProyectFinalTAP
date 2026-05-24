package com.hotel.model;

public class Puesto {
    private int idPuesto;
    private String nombrePuesto;
    private String descripcion;

    public Puesto() {}

    public Puesto(int idPuesto, String nombrePuesto, String descripcion) {
        this.idPuesto = idPuesto;
        this.nombrePuesto = nombrePuesto;
        this.descripcion = descripcion;
    }

    public int getIdPuesto() { return idPuesto; }
    public void setIdPuesto(int idPuesto) { this.idPuesto = idPuesto; }
    public String getNombrePuesto() { return nombrePuesto; }
    public void setNombrePuesto(String nombrePuesto) { this.nombrePuesto = nombrePuesto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
