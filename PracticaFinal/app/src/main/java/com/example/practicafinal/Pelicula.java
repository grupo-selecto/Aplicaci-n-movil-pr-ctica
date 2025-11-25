package com.example.practicafinal;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pelicula implements Serializable {
    String titulo, director, sinopsis, sala, idYoutube;
    int clasi, portada, duracion;
    Date fecha;
    boolean favorita;

    private transient SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    public Pelicula(String titulo, String director, int duracion, Date fecha, String sala, int clasi, int portada) {
        this.clasi=clasi;
        this.director=director;
        this.fecha=fecha;
        this.portada=portada;
        this.sala=sala;
        this.titulo=titulo;
        this.duracion=duracion;
        this.favorita=false;
    }

    public String getIdYoutube() {
        return idYoutube;
    }
    public void setIdYoutube(String idYoutube) {
        this.idYoutube=idYoutube;
    }
    public int getClasi() {
        return clasi;
    }
    public void setClasi(int clasi) {
        this.clasi=clasi;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director=director;
    }
    public boolean getFavorita() {
        return favorita;
    }
    public void setFavorita(Boolean favorita) {
        this.favorita=favorita;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha=fecha;
    }
    public int getPortada() {
        return portada;
    }
    public void setPortada(int portada) {
        this.portada=portada;
    }
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala=sala;
    }
    public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis=sinopsis;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo=titulo;
    }

    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion=duracion;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitulo();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pelicula)) return false;
        Pelicula other = (Pelicula) obj;
        return titulo.equals(other.titulo); // O cualquier otro campo único
    }

    @Override
    public int hashCode() {
        return titulo.hashCode(); // Debe coincidir con equals
    }
}
