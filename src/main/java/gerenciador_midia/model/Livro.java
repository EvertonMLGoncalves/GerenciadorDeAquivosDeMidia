package gerenciador_midia.model;

import gerenciador_midia.enums.LivroFormatoSuportado;

/**
 * Representa uma mídia do tipo Livro.
 * Formatos suportados: PDF, EPUB
 */
public class Livro extends Midia {
    
    private String autores;

    public Livro(String local, String titulo, int duracao, String categoria, String autores) {
        super(local, titulo, duracao, categoria);
        this.setAutores(autores);
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

    @Override
    protected boolean validarFormato(String extensao) {
        try {
            LivroFormatoSuportado.valueOf(extensao);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        if (autores == null || autores.trim().isEmpty()) {
            throw new IllegalArgumentException("Os autores não podem ser nulos ou vazios.");
        }
        this.autores = autores.trim();
    }
}