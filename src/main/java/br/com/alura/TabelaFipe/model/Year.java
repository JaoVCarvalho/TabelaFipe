package br.com.alura.TabelaFipe.model;

public record Year(Integer codigo, String nome) {

    @Override
    public String toString() { return "[Código: " + codigo + " Ano: " + nome + "]";}
}
