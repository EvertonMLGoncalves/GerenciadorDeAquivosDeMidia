package gerenciador_midia.model;

public class Filme extends Midia {

    private String idioma;

    public Filme(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String idioma) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        this.setIdioma(idioma);
    }

    @Override
    public String exibirDetalhes() {
        return "Filme: " + getTitulo() +
                "\nIdioma: " + idioma +
                "\nDuração: " + getDuracao() + " Minutos" +
                "\nCategoria: " + getCategoria() +
                "\nTamanho: " + getTamanhoEmDisco() + " MB" +
                "\nLocal: " + getLocal();
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}