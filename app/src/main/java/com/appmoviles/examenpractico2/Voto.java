package com.appmoviles.examenpractico2;

public class Voto {

    private String nombre;
    private int edad;
    private String genero;
    private String heroe;

    public Voto() {
    }

    public Voto(String nombre, int edad, String genero, String heroe) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.heroe = heroe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getHeroe() {
        return heroe;
    }

    public void setHeroe(String heroe) {
        this.heroe = heroe;
    }
}
