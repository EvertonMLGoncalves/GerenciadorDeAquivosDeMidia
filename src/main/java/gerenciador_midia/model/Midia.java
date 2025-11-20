package gerenciador_midia.model;

public abstract class Midia {

    protected String local;
    protected long tamanhoEmDisco;
    protected String titulo;
    protected int duracao;
    protected String categoria;

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
}