package gerenciador_midia.model;

import gerenciador_midia.enums.FilmeFormatoSuportado;

/**
 * Representa uma mídia do tipo Filme.
 * Formatos suportados: MP4, MKV
 */
public class Filme extends Midia {

    private String idioma;

    public Filme(String local, String titulo, int duracao, String categoria, String idioma) {
        super(local, titulo, duracao, categoria);
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

    @Override
    protected boolean validarFormato(String extensao) {
        try {
            FilmeFormatoSuportado.valueOf(extensao);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        if (idioma == null || idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("O idioma não pode ser nulo ou vazio.");
        }
        this.idioma = idioma.trim();
    }
}