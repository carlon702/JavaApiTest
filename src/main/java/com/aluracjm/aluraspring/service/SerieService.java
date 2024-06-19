package com.aluracjm.aluraspring.service;


import com.aluracjm.aluraspring.dto.SerieDTO;
import com.aluracjm.aluraspring.model.Categoria;
import com.aluracjm.aluraspring.dto.EpisodeDTO;
import com.aluracjm.aluraspring.model.Serie;
import com.aluracjm.aluraspring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerSeries(){
        return conversorDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return conversorDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosRecientes(){
        return conversorDatos(repository.lanzamientosRecientes());
    }

    public List<SerieDTO> conversorDatos(List<Serie> serie){
        return serie.stream().map(s-> new SerieDTO(s.getId(),s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis())).toList();
    }


    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie =  repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis());

        } else {
            return null;
        }
    }

    public List<EpisodeDTO> obtenerTodasLasTemporadas(Long id){
        Optional<Serie> serie =  repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e->new EpisodeDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).toList();
        } else {
            return null;
        }
    }



    public List<EpisodeDTO> obtenerTemporadaPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadasPorNumero(id, numeroTemporada).stream().map(e-> new EpisodeDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).toList();
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String genero) {
        Categoria categoria = Categoria.fromEspanol(genero);
        return conversorDatos(repository.findByGenero(categoria));
    }
}
