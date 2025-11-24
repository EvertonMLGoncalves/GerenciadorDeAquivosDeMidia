package gerenciador_midia.controller;

import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Musica;
import gerenciador_midia.model.Livro;

public class TelaPrincipal {
    static void main(String[] args) {
        GerenciadorMidia gerenciador = new GerenciadorMidia();

//        // Criar e incluir um Filme
//        Filme filme = new Filme(
//            "C:\\videos\\matrix.mp4",
//            700,
//            "Matrix",
//            136,
//            "Ficção Científica",
//            "Inglês"
//        );
//        gerenciador.incluirMidia(filme);
//        System.out.println("✓ Filme criado e adicionado!");
//        System.out.println(filme.exibirDetalhes());
//        System.out.println("\n" + "=".repeat(50) + "\n");
//
//        // Criar e incluir uma Música
//        Musica musica = new Musica(
//            "C:\\musicas\\bohemian.mp3",
//            10,
//            "Bohemian Rhapsody",
//            355,
//            "Rock",
//            "Queen"
//        );
//        gerenciador.incluirMidia(musica);
//        System.out.println("✓ Música criada e adicionada!");
//        System.out.println(musica.exibirDetalhes());
//        System.out.println("\n" + "=".repeat(50) + "\n");

        // Criar e incluir um Livro
        Livro livro = new Livro(
            "\"C:\\Users\\cacao\\Downloads\\30 - Serialização.pdf\"",
            "30 - Serialização",
            999,
            "Ficção Distópica",
            "Professor"
        );
        gerenciador.incluirMidia(livro);
        System.out.println("✓ Livro criado e adicionado!");
        System.out.println(livro.exibirDetalhes());
    }
}

