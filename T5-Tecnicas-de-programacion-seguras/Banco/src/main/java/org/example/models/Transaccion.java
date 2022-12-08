package org.example.models;

public class Transaccion {

    private int id;
    private String fecha;
    private String descripcion;
    private double importe;

    public Transaccion(int id, String fecha, String descripcion, double importe) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.importe = importe;
    }

    public Transaccion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                ", fecha='" + fecha + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", importe=" + importe +
                '}';
    }
}
