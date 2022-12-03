package org.example.models;

import java.util.ArrayList;

public class CuentaBancaria {

    private int id;
    private String numeroCuenta;
    private ArrayList<Transaccion> transacciones;

    public CuentaBancaria(int id, String numeroCuenta, ArrayList<Transaccion> transacciones) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.transacciones = transacciones;
    }

    public CuentaBancaria() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(ArrayList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }


    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "id=" + id +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", transacciones=" + transacciones +
                '}';
    }
}
