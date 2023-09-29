package br.com.alura.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IConvertsData {
    <T> T getData(String json, Class<T> typeClass);

    <T> List<T> getList(String json, Class<T> typeClass);
}
