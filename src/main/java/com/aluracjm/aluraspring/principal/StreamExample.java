package com.aluracjm.aluraspring.principal;
import java.util.List;
import java.util.Arrays;

public class StreamExample {

    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Brenda", "Carlos", "Maria", "Luis");

        nombres.stream().sorted().limit(2).forEach(System.out::println);
    }
}
