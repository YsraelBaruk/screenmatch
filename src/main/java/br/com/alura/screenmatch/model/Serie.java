package br.com.alura.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String atores;
    private String snopse;
    private String poster;

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0.0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.snopse = dadosSerie.snopse();
        this.poster = dadosSerie.poster();
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas(){
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas){
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao(){
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao){
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero(){
        return genero;
    }

    public void setGenero(Categoria genero){
        this.genero = genero;
    }

    public String getAtores(){
        return atores;
    }

    public void setAtores(String atores){
        this.atores = atores;
    }

    public String getSnopse(){
        return snopse;
    }

    public void setSnopse(String snopse){
        this.snopse = snopse;
    }

    public String getPoster(){
        return poster;
    }

    public void setPoster(String poster){
        this.poster = poster;
    }

    @Override
    public String toString(){
        return "Gênero: "+genero+"\n"+
                "; Titúlo: "+titulo+"\n"+
                "; Total de temporadas: "+totalTemporadas+"\n"+
                "; Avaliação: "+avaliacao+"\n"+
                "; Atores: "+atores+"\n"+
                "; Pôster: "+poster+"\n"+
                "; Sinopse: "+snopse;
    }
}
