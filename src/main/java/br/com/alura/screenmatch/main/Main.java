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
    private ComsumoApi api = new ComsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String API_KEY = "&apikey=fb1bba49";
    private final String URI = "http://www.omdbapi.com/?t=";

    public void exibeMenu(){
        System.out.println("Digite o nome da série, para a busca");
        var nomeSerie = type.nextLine();
        var json = api.obterDados(URI+nomeSerie.replace(" ", "+")+API_KEY);
        DadosSerie dados = conversor.obeterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas(); i++){
			json = api.obterDados("%s%s&season=%s%s".formatted(URI, nomeSerie.replace(" ", "+"), i, API_KEY));
			DadosTemporada dadosTemporada = conversor.obeterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporadas.size(); j++) {
                System.out.println(episodiosTemporadas.get(j).titulo());
            }
        }
        System.out.println("Títulos dos episódios");
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(toList());

//        System.out.println("\nTop 5 episódios");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A) "+e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação "+e))
//                .limit(5)
//                .peek(e -> System.out.println("Limite "+e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamneto "+e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios()
                        .stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(toList());

//        System.out.println("Digite o titulo de um episódio para a busca");
//        var trechoTitulo = type.nextLine();

        //episodios.forEach(System.out::println);
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episódio encontrado" +
//                    "Temporada: %s".formatted(episodioBuscado.get().getTemporada()));
//        } else {
//            System.out.println("Não foi encontrado o episódio");
//        }
//        System.out.println("Digite um ano para ver os episódios");
//        var ano = type.nextInt();
//        type.nextInt();

//        LocalDate localDate = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(localDate))
//                .forEach(e -> System.out.println(
//                        "Temporada: "+e.getTemporada()+
//                        ", Episódio: "+e.getTitulo()+
//                        ", Data de lançamento: "+e.getDataLancamento().format(dateTimeFormatter)
//                ));
        System.out.println("Média de avaliações por temporada");
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println("\n"+avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: %s,\nMinimo: %s,\nMáximo: %s,\nQuantidade: %s".formatted(
                est.getAverage(),
                est.getMin(),
                est.getMax(),
                est.getCount()
        ));
    }
}