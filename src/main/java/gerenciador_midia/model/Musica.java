package gerenciador_midia.model;

public class Musica extends Midia {
    private String Livro;

    public Musica(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String livro) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        Livro = livro;
    }
    public void mostrarDetalhesMusica(){
        System.out.println("Detalhes da musica" + exibirDetalhes());
    }
}
