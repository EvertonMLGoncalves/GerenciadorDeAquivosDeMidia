package gerenciador_midia.model;

import java.io.Serializable;

public abstract class Midia  implements Serializable {

    protected String local;
    protected long tamanhoEmDisco;
    protected String titulo;
    protected int duracao;
    protected String categoria;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public long getTamanhoEmDisco() {
        return tamanhoEmDisco;
    }

    public void setTamanhoEmDisco(long tamanhoEmDisco) {
        this.tamanhoEmDisco = tamanhoEmDisco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Midia(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria) {
        this.local = local;
        this.tamanhoEmDisco = tamanhoEmDisco;
        this.titulo = titulo;
        this.duracao = duracao;
        this.categoria = categoria;
    }

    public String exibirDetalhes() {
        return "Título: " + titulo +
                "\nLocal: " + local +
                "\nTamanho em disco: " + tamanhoEmDisco +
                "\nDuração: " + duracao +
                "\nCategoria: " + categoria;
    }

    public String getFormato() {
        return "";
    }
}
