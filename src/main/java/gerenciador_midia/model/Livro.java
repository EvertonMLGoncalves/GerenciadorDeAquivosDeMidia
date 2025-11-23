package gerenciador_midia.model;

public class Livro extends Midia {
    private String autores;

    public String getAutores() {

        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public Livro(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String autores) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        this.autores = autores;
    }

    @Override
    public String exibirDetalhes() {
        return "Livro: " + getTitulo() +
                "\nAutores: " + autores +
                "\nDuração: " + getDuracao() + " Páginas" +
                "\nCategoria: " + getCategoria() +
                "\nTamanho: " + getTamanhoEmDisco() + " MB" +
                "\nLocal: " + getLocal();
    }
}