package com.aluracjm.aluraspring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(@JsonAlias("Title") String titulo, @JsonAlias("Season") Integer temporada, @JsonAlias("Episode") Integer numeroEpisodio, @JsonAlias("imdbRating") String evaluacion, @JsonAlias("Released") String fechaLanzamiento) {
}
