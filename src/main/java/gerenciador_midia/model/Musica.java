package gerenciador_midia.model;

import gerenciador_midia.enums.MusicaFormatoSuportado;

public class Musica extends Midia {
    private String artista;

    public Musica(String local, String titulo, int duracao, String categoria, String artista) {
        super(local, titulo, duracao, categoria);
        this.setArtista(artista);
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

    @Override
    protected boolean validarFormato(String extensao) {
        try {
            MusicaFormatoSuportado.valueOf(extensao);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
}





