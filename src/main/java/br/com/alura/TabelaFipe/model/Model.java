package br.com.alura.TabelaFipe.model;

public record Model(Integer codigo, String nome) {

    @Override
    public String toString() {
        return "[Código: " + codigo + " Modelo: " + nome + "]";
    }
}
