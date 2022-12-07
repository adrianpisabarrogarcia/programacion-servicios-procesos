package org.example.models;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
    private byte[] password;
    private String credentialsPath;
    private boolean documentoFirmado;
    private CuentaBancaria cuentaBancaria;

    public Usuario(int id, String nombre, String apellido, int edad, String email, byte[] password, String credentialsPath, boolean documentoFirmado, CuentaBancaria cuentaBancaria) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.email = email;
        this.password = password;
        this.credentialsPath = credentialsPath;
        this.documentoFirmado = documentoFirmado;
        this.cuentaBancaria = cuentaBancaria;
    }

    public Usuario() {
    }


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

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
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

    //Validaciones del usuario
    public boolean validarNombre(String nombre) {
        if (nombre.length() > 0 && nombre.length() < 20) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarApellido(String apellido) {
        if (apellido.length() > 0 && apellido.length() < 20) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarEdad(String edad) {
        //Validar que la edad sea un numero y que sea mayor a 18
        try {
            return Integer.parseInt(edad) > 18;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validarEmail(String email) {
        //Validar con regex
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean validarPassword(String password) {
        //Validar con regex la contraseña
        //Debe tener al menos una letra mayuscula, una minuscula, un numero y un caracter especial
        //mínimo 8 caracteres
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}$");
    }
}
