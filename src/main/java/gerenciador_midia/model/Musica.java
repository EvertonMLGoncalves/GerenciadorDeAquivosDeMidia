package gerenciador_midia.model;

public class Musica extends Midia {
    private String artista;

    public Musica(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String artista) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        this.artista = artista;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
    @Override
    public String exibirDetalhes() {
        return "Musica: " + getTitulo() +
                "\nArtista: " + artista +
                "\nDuração: " + getDuracao() + " Segundos" +
                "\nCategoria: " + getCategoria() +
                "\nTamanho: " + getTamanhoEmDisco() + " MB" +
                "\nLocal: " + getLocal();
    }
}


