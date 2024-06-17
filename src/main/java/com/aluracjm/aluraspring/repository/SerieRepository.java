package com.aluracjm.aluraspring.repository;

import com.aluracjm.aluraspring.model.Categoria;
import com.aluracjm.aluraspring.model.Episode;
import com.aluracjm.aluraspring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);
    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categloria);
    //List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titutlo ILIKE %:nombreEpisodio%")
    List<Episode> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e From Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episode> top5Episodios(Serie serie);

}
