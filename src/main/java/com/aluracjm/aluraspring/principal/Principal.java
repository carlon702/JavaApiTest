package com.aluracjm.aluraspring.principal;

import com.aluracjm.aluraspring.model.DataEpisode;
import com.aluracjm.aluraspring.model.DataSeason;
import com.aluracjm.aluraspring.model.DataSeries;
import com.aluracjm.aluraspring.model.Episode;
import com.aluracjm.aluraspring.service.DataConversion;
import com.aluracjm.aluraspring.service.RequestAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private DataConversion conversor = new DataConversion();
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ba27516a";

    public void showMenu(){

        System.out.println("Por favor escribe el nombre de la serie que desea buscar");
        //Busqueda datos generales
        String seriesName = sc.nextLine();
        String json = RequestAPI.obtenerDatos(URL_BASE+seriesName.replace(" ", "+")+API_KEY);
        DataSeries data = conversor.getData(json, DataSeries.class);
        System.out.println(data);

        //Busca datos temporadas y crea lista
        List<DataSeason> temporadas = new ArrayList<>();
        for (int i = 1; i < data.totalTemporadas()+1; i++) {
            String jsonSeason = RequestAPI.obtenerDatos((URL_BASE+seriesName.replace(" ", "+")+"&season="+ i + API_KEY));
            DataSeason datosTemporada = conversor.getData(jsonSeason, DataSeason.class);
            temporadas.add(datosTemporada);
        }
       // temporadas.forEach(System.out::println);

        //Mostrar titulo episodios

//        for (int i = 0; i < data.totalTemporadas(); i++) {
//            List<DataEpisode> episodios = temporadas.get(i).episodes();
//            System.out.println("season:"+ (i+1));
//            for (int j = 0; j < episodios.size(); j++) {
//                System.out.println(episodios.get(j).titulo());
//            }
//        }
        //temporadas.forEach(t-> t.episodes().forEach(e -> System.out.println(e.titulo())));;

        //Crear lista de episodios

        //List<DataEpisode> episodios = temporadas.stream().flatMap(t-> t.episodes().stream()).collect(Collectors.toList());

        //Top 5
        //System.out.println("Top 5 episodios");
        //episodios.stream().filter(e->!e.evaluacion().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(DataEpisode::evaluacion).reversed()).limit(5).forEach(System.out::println);

        //Conviertiendo datos a lista de episodios class

        List<Episode> episodiosCompletos = temporadas.stream().flatMap(e->e.episodes().stream().map( d-> new Episode(e.seasonNumber(), d))).collect(Collectors.toList());

        episodiosCompletos.forEach(System.out::println);



        //Buscar episodio por fecha
        System.out.println("indica una fecha");
        Integer fecha = sc.nextInt();
        System.out.println(fecha);
        sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaDeBusqueda = LocalDate.of(fecha, 1, 1);
        episodiosCompletos.stream().filter(e->e.getFechaLanzamiento()!= null && e.getFechaLanzamiento().isAfter(fechaDeBusqueda)).forEach(e->{

            System.out.println( "temporada "+ e.getTemporada() + ", episodio: "+e.getTitulo() + " la fecha es " + e.getFechaLanzamiento().format(dtf));
                }
        );

        //Buscar por titulo

        System.out.println("Introduce un titulo de episodio");
        String tituloInput = sc.nextLine();

        Optional<Episode> episodioBuscado = episodiosCompletos.stream().filter(e->e.getTitulo().toUpperCase().contains(tituloInput.toUpperCase())).findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("episodio encontrado" + episodioBuscado.get());
        } else {
            System.out.println("No encontrado");
        }

        //Mapeo de temporada

        Map<Integer, Double> evaluacionesPorTemporada = episodiosCompletos.stream().filter(e->e.getEvaluacion()>0.0).collect(Collectors.groupingBy(Episode::getTemporada, Collectors.averagingDouble(Episode::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);

        //Estadistica

        DoubleSummaryStatistics est= episodiosCompletos.stream().filter(e->e.getEvaluacion()>0.0).collect(Collectors.summarizingDouble(Episode::getEvaluacion));
        System.out.println(est);
    }
}
