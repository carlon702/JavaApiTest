package com.aluracjm.aluraspring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(@JsonAlias("Title") String titulo, @JsonAlias("totalSeasons") int totalTemporadas, @JsonAlias("imdbRating") String evaluacion) {
}
