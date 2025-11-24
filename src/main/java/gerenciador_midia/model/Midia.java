package gerenciador_midia.model;

import java.io.File;
import java.io.Serializable;

public abstract class Midia implements Serializable {

    protected String local;
    protected double tamanhoEmDisco;
    protected String titulo;
    protected int duracao;
    protected String categoria;
    protected String formato;

    public Midia(String local, String titulo, int duracao, String categoria) {
        this.setLocal(local);
        this.setTitulo(titulo);
        this.setDuracao(duracao);
        this.setCategoria(categoria);
        this.setFormato(extrairFormatoDoLocal(local));
        this.setTamanhoEmDisco(calcularTamanhoEmMB(local));
    }

    private String extrairFormatoDoLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            return "";
        }

        local = local.trim().replaceAll("^\"|\"$", "");

        if (!local.contains(".")) {
            return "";
        }

        int ultimoPonto = local.lastIndexOf('.');
        String extensao = local.substring(ultimoPonto + 1);

        extensao = extensao.replaceAll("[^a-zA-Z0-9]", "");

        return extensao.toUpperCase();
    }

    private double calcularTamanhoEmMB(String local) {
        if (local == null || local.trim().isEmpty()) {
            return 0;
        }

        String caminhoLimpo = local.trim().replaceAll("^\"|\"$", "");

        File arquivo = new File(caminhoLimpo);

        if (!arquivo.exists() || !arquivo.isFile()) {
            return 0;
        }

        long tamanhoEmBytes = arquivo.length();
        return Math.round((tamanhoEmBytes / Math.pow(1024.0, 2)) * 100.0) / 100.0;
    }

    public void atualizarTamanhoEmDisco() {
        this.tamanhoEmDisco = calcularTamanhoEmMB(this.local);
    }

    public String exibirDetalhes() {
        return "Título: " + titulo +
                "\nLocal: " + local +
                "\nTamanho em disco: " + tamanhoEmDisco +
                "\nDuração: " + duracao +
                "\nCategoria: " + categoria;
    }

    protected abstract boolean validarFormato(String formato);

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("O local não pode ser nulo ou vazio.");
        }

        local = local.trim().replaceAll("^\"|\"$", "");

        this.local = local;
    }

    public double getTamanhoEmDisco() {
        return tamanhoEmDisco;
    }

    private void setTamanhoEmDisco(double tamanhoEmDisco) {
        this.tamanhoEmDisco = tamanhoEmDisco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ser nulo ou vazio.");
        }
        this.titulo = titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        if (duracao < 0) {
            throw new IllegalArgumentException("A duração não pode ser negativa.");
        }
        this.duracao = duracao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria não pode ser nula ou vazia.");
        }
        this.categoria = categoria;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException("O formato não pode ser nulo ou vazio.");
        }

        if (!validarFormato(formato.toUpperCase())) {
            throw new IllegalArgumentException("Formato não suportado: " + formato);
        }

        this.formato = formato.toUpperCase();
    }
}