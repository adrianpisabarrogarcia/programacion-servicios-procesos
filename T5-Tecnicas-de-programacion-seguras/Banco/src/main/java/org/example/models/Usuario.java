package org.example.models;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
    private String password;
    private String credentialsPath;
    private boolean documentoFirmado;
    private CuentaBancaria cuentaBancaria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCredentialsPath() {
        return credentialsPath;
    }

    public void setCredentialsPath(String credentialsPath) {
        this.credentialsPath = credentialsPath;
    }

    public boolean isDocumentoFirmado() {
        return documentoFirmado;
    }

    public void setDocumentoFirmado(boolean documentoFirmado) {
        this.documentoFirmado = documentoFirmado;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", credentialsPath='" + credentialsPath + '\'' +
                ", documentoFirmado=" + documentoFirmado +
                ", cuentaBancaria=" + cuentaBancaria +
                '}';
    }
}
