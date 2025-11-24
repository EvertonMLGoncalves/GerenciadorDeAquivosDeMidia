package gerenciador_midia.model;

import gerenciador_midia.enums.LivroFormatoSuportado;

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
        this.autores = autores;
    }
}