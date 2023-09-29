package br.com.alura.TabelaFipe.model;

public record Brand(Integer codigo, String nome) {

    public String toString(){
        return "[Código: " + codigo + " Marca: " + nome + "]";
    }
}
