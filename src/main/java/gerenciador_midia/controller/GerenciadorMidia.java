package gerenciador_midia.controller;

import gerenciador_midia.model.Midia;
import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Livro;
import gerenciador_midia.model.Musica;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class GerenciadorMidia {

    private List<Midia> midias;
    private final Path ARQUIVO = Paths.get("midias.tpoo");

    public GerenciadorMidia() {
        this.midias = new ArrayList<>();
        carregarMidias();
    }

    public List<Midia> listarPorCategoria(String categoria) {
        if (categoria == null) return new ArrayList<>();

        List<Midia> resultado = new ArrayList<>();
        for (Midia m : midias) {
            if (m.getCategoria() != null && m.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public List<Midia> ordenarPorTitulo() {
        List<Midia> copia = new ArrayList<>(midias);
        copia.sort(Comparator.comparing(
                m -> (m.getTitulo() == null ? "" : m.getTitulo()),
                String.CASE_INSENSITIVE_ORDER
        ));
        return copia;
    }

    public List<Midia> ordenarPorDuracao() {
        List<Midia> copia = new ArrayList<>(midias);
        copia.sort(Comparator.comparingInt(m -> m.getDuracao()));
        return copia;
    }

    public List<Midia> filtrarMidias(String formato, String categoria) {
        List<Midia> resultado = new ArrayList<>();
        for (Midia m : midias) {
            boolean okFormato = (formato == null || formato.isEmpty()) ||
                    (m.getFormato() != null && m.getFormato().equalsIgnoreCase(formato));
            boolean okCategoria = (categoria == null || categoria.isEmpty()) ||
                    (m.getCategoria() != null && m.getCategoria().equalsIgnoreCase(categoria));

            if (okFormato && okCategoria) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public void carregarMidias() {
        midias.clear();
        if (!Files.exists(ARQUIVO)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(ARQUIVO, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";", -1);
                String titulo = partes.length > 0 ? partes[0] : "";
                String formato = partes.length > 1 ? partes[1] : "";
                String categoria = partes.length > 2 ? partes[2] : "";
                int duracao = 0;

                if (partes.length > 3) {
                    try {
                        duracao = Integer.parseInt(partes[3].trim());
                    } catch (NumberFormatException e) {
                        duracao = 0;
                    }
                }

                Midia m;
                if (formato.equalsIgnoreCase("filme")) {
                    m = new Filme("", 0, titulo, duracao, categoria, formato);
                } else if (formato.equalsIgnoreCase("livro")) m = new Livro("", 0, titulo, duracao, categoria, formato);
                else if (formato.equalsIgnoreCase("musica")) {
                    m = new Musica("", 0, titulo, duracao, categoria, formato);
                } else {
                    m = new Filme("", 0, titulo, duracao, categoria, formato);
                }

                midias.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarMidias() {
        try (BufferedWriter writer = Files.newBufferedWriter(ARQUIVO, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Midia m : midias) {
                String linha = String.join(";",
                        safe(m.getTitulo()),
                        safe(m.getFormato()),
                        safe(m.getCategoria()),
                        String.valueOf(m.getDuracao())
                );
                writer.write(linha);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String safe(String s) {
        return (s == null) ? "" : s.replace(";", "");
    }
}