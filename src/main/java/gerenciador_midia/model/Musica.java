package gerenciador_midia.model;

public class Musica extends Midia {
    private String Musica;

    public Musica(String local, long tamanhoEmDisco, String titulo, int duracao, String categoria, String musica) {
        super(local, tamanhoEmDisco, titulo, duracao, categoria);
        Musica =  musica;
    }
    public void mostrarDetalhesMusica(){
        System.out.println("Detalhes da musica" + exibirDetalhes());
    }
}
