package gerenciador_midia.controller;

import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Livro;
import gerenciador_midia.model.Midia;
import gerenciador_midia.model.Musica;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela persistência de mídias.
 * Cada mídia é salva em seu próprio arquivo .tpoo conforme especificação.
 */
public class PersistenciaArquivo {

    private static final Path PASTA_MIDIAS = Paths.get("midias_data");

    public PersistenciaArquivo() {
        criarPastaSeNaoExistir();
    }

    private void criarPastaSeNaoExistir() {
        File diretorio = PASTA_MIDIAS.toFile();
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    /**
     * Gera um nome de arquivo único baseado no hash do local da mídia.
     */
    private String gerarNomeArquivo(String local) {
        int hash = Math.abs(local.hashCode());
        return hash + ".tpoo";
    }

    /**
     * Salva uma mídia individual em seu arquivo .tpoo.
     */
    public void salvarMidia(Midia midia) throws IOException {
        String nomeArquivo = gerarNomeArquivo(midia.getLocal());
        Path caminhoArquivo = PASTA_MIDIAS.resolve(nomeArquivo);

        try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo, StandardCharsets.UTF_8)) {
            String tipo = "";
            String campoEspecifico = "";

            if (midia instanceof Filme) {
                tipo = "filme";
                campoEspecifico = ((Filme) midia).getIdioma();
            } else if (midia instanceof Musica) {
                tipo = "musica";
                campoEspecifico = ((Musica) midia).getArtista();
            } else if (midia instanceof Livro) {
                tipo = "livro";
                campoEspecifico = ((Livro) midia).getAutores();
            }

            writer.write("tipo=" + tipo);
            writer.newLine();
            writer.write("local=" + safe(midia.getLocal()));
            writer.newLine();
            writer.write("titulo=" + safe(midia.getTitulo()));
            writer.newLine();
            writer.write("duracao=" + midia.getDuracao());
            writer.newLine();
            writer.write("categoria=" + safe(midia.getCategoria()));
            writer.newLine();
            writer.write("formato=" + safe(midia.getFormato()));
            writer.newLine();
            writer.write("tamanho=" + midia.getTamanhoEmDisco());
            writer.newLine();
            writer.write("campoEspecifico=" + safe(campoEspecifico));
            writer.newLine();
        }
    }

    /**
     * Carrega todas as mídias dos arquivos .tpoo existentes.
     */
    public List<Midia> carregarTodasMidias() {
        List<Midia> midias = new ArrayList<>();
        File diretorio = PASTA_MIDIAS.toFile();

        if (!diretorio.exists()) {
            return midias;
        }

        File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".tpoo"));
        if (arquivos == null) {
            return midias;
        }

        for (File arquivo : arquivos) {
            try {
                Midia midia = carregarMidia(arquivo.toPath());
                if (midia != null) {
                    midias.add(midia);
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar " + arquivo.getName() + ": " + e.getMessage());
            }
        }

        return midias;
    }

    /**
     * Carrega uma mídia individual de um arquivo .tpoo.
     */
    private Midia carregarMidia(Path caminhoArquivo) throws IOException {
        String tipo = null;
        String local = null;
        String titulo = null;
        int duracao = 0;
        String categoria = null;
        String campoEspecifico = null;

        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("=", 2);
                if (partes.length == 2) {
                    String chave = partes[0].trim();
                    String valor = partes[1].trim();

                    switch (chave) {
                        case "tipo":
                            tipo = valor;
                            break;
                        case "local":
                            local = valor;
                            break;
                        case "titulo":
                            titulo = valor;
                            break;
                        case "duracao":
                            try {
                                duracao = Integer.parseInt(valor);
                            } catch (NumberFormatException e) {
                                duracao = 0;
                            }
                            break;
                        case "categoria":
                            categoria = valor;
                            break;
                        case "campoEspecifico":
                            campoEspecifico = valor;
                            break;
                    }
                }
            }
        }

        if (tipo == null || local == null || local.isEmpty()) {
            return null;
        }

        try {
            if (tipo.equalsIgnoreCase("filme")) {
                return new Filme(local, titulo, duracao, categoria, campoEspecifico);
            } else if (tipo.equalsIgnoreCase("musica")) {
                return new Musica(local, titulo, duracao, categoria, campoEspecifico);
            } else if (tipo.equalsIgnoreCase("livro")) {
                return new Livro(local, titulo, duracao, categoria, campoEspecifico);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar mídia: " + e.getMessage());
        }

        return null;
    }

    /**
     * Deleta o arquivo .tpoo de uma mídia específica.
     */
    public void deletarMidia(Midia midia) throws IOException {
        String nomeArquivo = gerarNomeArquivo(midia.getLocal());
        Path caminhoArquivo = PASTA_MIDIAS.resolve(nomeArquivo);
        Files.deleteIfExists(caminhoArquivo);
    }

    /**
     * Deleta o arquivo .tpoo de uma mídia pelo local antigo e cria novo com local atualizado.
     */
    public void renomearArquivoMidia(String localAntigo, Midia midiaNova) throws IOException {
        String nomeArquivoAntigo = gerarNomeArquivo(localAntigo);
        Path caminhoArquivoAntigo = PASTA_MIDIAS.resolve(nomeArquivoAntigo);
        Files.deleteIfExists(caminhoArquivoAntigo);
        salvarMidia(midiaNova);
    }

    private String safe(String s) {
        return (s == null) ? "" : s;
    }
}