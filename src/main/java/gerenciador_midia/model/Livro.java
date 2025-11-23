package gerenciador_midia.model;

public class Livro extends Midia{
    private String Livro;

    public Livro(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String livro) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        Livro = livro;
    }
        public void mostrarDetalhesLivro(){
            System.out.println("Detalhes do Livro: " + exibirDetalhes());
    }
}
