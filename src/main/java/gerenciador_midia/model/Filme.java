package gerenciador_midia.model;

import java.sql.SQLOutput;

public class Filme extends Midia{

    private String filme;

    public Filme(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String filme) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        this.filme = filme;
    }
    public void mostrarDetalhesFilme(){
        System.out.println("Detalhes do filme: " + exibirDetalhes());
    }
}
