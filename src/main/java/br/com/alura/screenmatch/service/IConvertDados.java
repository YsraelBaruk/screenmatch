package br.com.alura.screenmatch.service;

public interface IConvertDados {
    <T> T obeterDados(String json, Class<T> classe);
}