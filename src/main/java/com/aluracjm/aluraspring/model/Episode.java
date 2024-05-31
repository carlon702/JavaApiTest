package com.aluracjm.aluraspring.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {

    private Integer temporada;

    public Episode(Integer season, DataEpisode d) {
        this.temporada = season;
        this.titulo = d.titulo();
        try{this.evaluacion = Double.valueOf(d.evaluacion());}catch(NumberFormatException e){
            this.evaluacion = 0.00;
        }
        try{
            this.fechaLanzamiento = LocalDate.parse(d.fechaLanzamiento());
        } catch(DateTimeParseException e) {
            this.fechaLanzamiento = null;
        }

        this.numeroEpisodio = d.numeroEpisodio();
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaLanzamiento=" + fechaLanzamiento;

    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    private String titulo;
    private Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaLanzamiento;
}
