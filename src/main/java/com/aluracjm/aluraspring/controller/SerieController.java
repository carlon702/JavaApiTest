package com.aluracjm.aluraspring.controller;


import com.aluracjm.aluraspring.dto.SerieDTO;
import com.aluracjm.aluraspring.dto.EpisodeDTO;
import com.aluracjm.aluraspring.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")

public class SerieController {
    @Autowired
    private SerieService service;


    @GetMapping()
    public List<SerieDTO> obtenerSeries(){
        return service.obtenerSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5Series(){
        return service.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosRecientes(){
        return service.obtenerLanzamientosRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> obtenerTodasLasTemporadas(@PathVariable Long id){
        return service.obtenerTodasLasTemporadas(id);
    }
    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodeDTO> obtenerTemporada(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return service.obtenerTemporadaPorNumero(id, numeroTemporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriePorCategoria(@PathVariable String nombreGenero){
        return service.obtenerSeriesPorCategoria(nombreGenero);
    }
}
