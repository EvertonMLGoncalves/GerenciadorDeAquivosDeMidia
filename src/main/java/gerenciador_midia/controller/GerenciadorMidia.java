package gerenciador_midia.controller;

import gerenciador_midia.model.Midia;

import java.util.ArrayList;

public class GerenciadorMidia {

    private String local;
    private int tamanho;
    private String titulo;
    private double duracao;
    private String categoria;

    ArrayList<Midia> listaMidia = new ArrayList<Midia>();

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Midia> getListaMidia() {
        return listaMidia;
    }

    public void setListaMidia(ArrayList<Midia> listaMidia) {
        this.listaMidia = listaMidia;
    }

    public GerenciadorMidia(String local, int tamanho, String titulo, double duracao, String categoria, ArrayList<Midia> listaMidia) {
        this.local = local;
        this.tamanho = tamanho;
        this.titulo = titulo;
        this.duracao = duracao;
        this.categoria = categoria;
        this.listaMidia = listaMidia;
    }
}
