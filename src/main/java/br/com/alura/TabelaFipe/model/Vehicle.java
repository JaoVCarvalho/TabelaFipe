package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicle(@JsonAlias("Valor") String valor,
                      @JsonAlias("Marca") String marca,
                      @JsonAlias("Modelo") String modelo,
                      @JsonAlias("AnoModelo") Integer anoModelo,
                      @JsonAlias("Combustivel") String combustivel,
                      @JsonAlias("CodigoFipe") String codigoFipe,
                      @JsonAlias("MesReferencia") String mesReferencia) {

    public String toString() {
        return "[Valor: " + valor +
                " | Marca: " + marca +
                " | Modelo: " + modelo +
                " | Ano do Modelo: " + anoModelo +
                " | Combustível: " + combustivel +
                " | Mês de consulta: " + mesReferencia +
                " | Código FIPE: " + codigoFipe + "]";
    }
}
