package com.aluracjm.aluraspring.principal;

import com.aluracjm.aluraspring.model.*;
import com.aluracjm.aluraspring.repository.SerieRepository;
import com.aluracjm.aluraspring.service.DataConversion;
import com.aluracjm.aluraspring.service.RequestAPI;

import java.util.*;


public class Principal {

    private final DataConversion conversor = new DataConversion();
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ba27516a";
    private List<DataSeries> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }
//    public void showMenu(){
//
//        System.out.println("Por favor escribe el nombre de la serie que desea buscar");
//        //Busqueda datos generales
//        String seriesName = sc.nextLine();
//        String json = RequestAPI.obtenerDatos(URL_BASE+seriesName.replace(" ", "+")+API_KEY);
//        DataSeries data = conversor.getData(json, DataSeries.class);
//        System.out.println(data);
//
//        //Busca datos temporadas y crea lista
//        List<DataSeason> temporadas = new ArrayList<>();
//        for (int i = 1; i < data.totalTemporadas()+1; i++) {
//            String jsonSeason = RequestAPI.obtenerDatos((URL_BASE+seriesName.replace(" ", "+")+"&season="+ i + API_KEY));
//            DataSeason datosTemporada = conversor.getData(jsonSeason, DataSeason.class);
//            temporadas.add(datosTemporada);
//        }
//       // temporadas.forEach(System.out::println);
//
//        //Mostrar titulo episodios
//
////        for (int i = 0; i < data.totalTemporadas(); i++) {
////            List<DataEpisode> episodios = temporadas.get(i).episodes();
////            System.out.println("season:"+ (i+1));
////            for (int j = 0; j < episodios.size(); j++) {
////                System.out.println(episodios.get(j).titulo());
////            }
////        }
//        //temporadas.forEach(t-> t.episodes().forEach(e -> System.out.println(e.titulo())));;
//
//        //Crear lista de episodios
//
//        //List<DataEpisode> episodios = temporadas.stream().flatMap(t-> t.episodes().stream()).collect(Collectors.toList());
//
//        //Top 5
//        //System.out.println("Top 5 episodios");
//        //episodios.stream().filter(e->!e.evaluacion().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(DataEpisode::evaluacion).reversed()).limit(5).forEach(System.out::println);
//
//        //Conviertiendo datos a lista de episodios class
//
//        List<Episode> episodiosCompletos = temporadas.stream().flatMap(e->e.episodes().stream().map( d-> new Episode(e.seasonNumber(), d))).collect(Collectors.toList());
//
//        episodiosCompletos.forEach(System.out::println);
//
//
//
//        //Buscar episodio por fecha
//        System.out.println("indica una fecha");
//        Integer fecha = sc.nextInt();
//        System.out.println(fecha);
//        sc.nextLine();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate fechaDeBusqueda = LocalDate.of(fecha, 1, 1);
//        episodiosCompletos.stream().filter(e->e.getFechaLanzamiento()!= null && e.getFechaLanzamiento().isAfter(fechaDeBusqueda)).forEach(e->{
//
//            System.out.println( "temporada "+ e.getTemporada() + ", episodio: "+e.getTitulo() + " la fecha es " + e.getFechaLanzamiento().format(dtf));
//                }
//        );
//
//        //Buscar por titulo
//
//        System.out.println("Introduce un titulo de episodio");
//        String tituloInput = sc.nextLine();
//
//        Optional<Episode> episodioBuscado = episodiosCompletos.stream().filter(e->e.getTitulo().toUpperCase().contains(tituloInput.toUpperCase())).findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("episodio encontrado" + episodioBuscado.get());
//        } else {
//            System.out.println("No encontrado");
//        }
//
//        //Mapeo de temporada
//
//        Map<Integer, Double> evaluacionesPorTemporada = episodiosCompletos.stream().filter(e->e.getEvaluacion()>0.0).collect(Collectors.groupingBy(Episode::getTemporada, Collectors.averagingDouble(Episode::getEvaluacion)));
//        System.out.println(evaluacionesPorTemporada);
//
//        //Estadistica
//
//        DoubleSummaryStatistics est= episodiosCompletos.stream().filter(e->e.getEvaluacion()>0.0).collect(Collectors.summarizingDouble(Episode::getEvaluacion));
//        System.out.println(est);
//    }

    public void showMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar por titulo 
                    5 - Top 5 series  
                    6 - Buscar series por categoria 
                    7 - Filtrar series
                    8 - Buscar episodios por titulo
                    9 - Top 5 episodios por serie           
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DataSeries getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = sc.nextLine();
        var json = RequestAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        return conversor.getData(json, DataSeries.class);
    }
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie que quieres ver: ");
        String nombreSerie = sc.nextLine();

        Optional<Serie> serie = series.stream().filter(e->e.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase())).findFirst();
        if(serie.isPresent()){
            Serie serieEncontrada = serie.get();
            List<DataSeason> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = RequestAPI.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeason datosTemporada = conversor.getData(json, DataSeason.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episode> episodios = temporadas.stream().flatMap(d->d.episodes().stream().map(e-> new Episode(d.seasonNumber(), e))).toList();
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }



    }
    private void buscarSerieWeb() {
        DataSeries datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo(){
        System.out.println("Escribe el nombre de la serie que quieres buscar: ");
        String nombreSerie = sc.nextLine();
        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if(serieBuscada.isPresent()){
            System.out.println("La serie buscada es " + serieBuscada.get());
        } else {
            System.out.println("Serie no encontrada");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println("Titulo: " + s.getTitulo() + " Evaluacion: " +s.getEvaluacion()));
    }
    private void buscarSeriesPorCategoria(){

        System.out.println("Escriba el genero que quiere buscar: ");
        String generoBuscado = sc.nextLine();
        Categoria categoria = Categoria.fromEspanol(generoBuscado);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("La series de la categoria "+ categoria + " son las siguientes: ");
        seriesPorCategoria.forEach(s-> System.out.println(s.getTitulo()));


    }
    public void filtrarSeriesPorTemporadaYEvaluacion(){
        System.out.println("¿Filtrar séries con cuántas temporadas? ");
        int totalTemporadas = sc.nextInt();
        sc.nextLine();
        System.out.println("¿Com evaluación apartir de cuál valor? ");
        Double evaluacion = sc.nextDouble();
        sc.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
        System.out.println("*** Series filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - evaluacion: " + s.getEvaluacion()));
    }

    private void buscarEpisodiosPorTitulo(){
        System.out.println("Escribe el nombre del episodio que deseas buscar:");
        String nombreEpisodio = sc.nextLine();
        List<Episode> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e-> System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s\n", e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void buscarTop5Episodios(){
        buscarSeriesPorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episode> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s\n", e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }
}
