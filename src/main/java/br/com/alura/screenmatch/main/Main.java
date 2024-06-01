package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ComsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main {
    private Scanner type = new Scanner(System.in);
    private ComsumoApi comsumo = new ComsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String API_KEY = "&apikey=fb1bba49";
    private final String ENDERECO = "http://www.omdbapi.com/?t=";

    public void exibeMenu() {
        var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                                
                0 - Sair
                """;
        System.out.println(menu);
        var opcao = type.nextInt();
        type.nextLine();

        switch (opcao) {
            case 1:
            case 2:
            case 0:
            default:
                System.out.println("Opção inválida");
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para a busca");
        var nomeSerie = type.nextLine();
        var json = comsumo.obterDados("%s%s%s".formatted(ENDERECO, nomeSerie.replace(" ", "+"), API_KEY));
        DadosSerie dados = conversor.obeterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioSerie() {
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = comsumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obeterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}