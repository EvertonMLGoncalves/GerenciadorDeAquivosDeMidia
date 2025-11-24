package gerenciador_midia.controller;

import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Livro;
import gerenciador_midia.model.Midia;
import gerenciador_midia.model.Musica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador principal do sistema de gerenciamento de mídias.
 * Responsável por todas as operações de CRUD, filtragem, ordenação e persistência.
 */
public class GerenciadorMidia {

    private List<Midia> midias;
    private PersistenciaArquivo persistencia;

    public GerenciadorMidia() {
        this.midias = new ArrayList<>();
        this.persistencia = new PersistenciaArquivo();
        carregarMidias();
    }

    /**
     * Inclui uma nova mídia no sistema.
     * 
     * @param midia Mídia a ser incluída
     * @throws IllegalArgumentException se a mídia for nula ou já existir no local especificado
     */
    public void incluirMidia(Midia midia) {
        if (midia == null) {
            throw new IllegalArgumentException("A mídia não pode ser nula.");
        }
        
        // Verificar se já existe uma mídia com o mesmo local
        for (Midia m : midias) {
            if (m.getLocal().equals(midia.getLocal())) {
                throw new IllegalArgumentException("Já existe uma mídia cadastrada neste local: " + midia.getLocal());
            }
        }
        
        this.midias.add(midia);
    }

    /**
     * Remove uma mídia do sistema e deleta seu arquivo .tpoo.
     * 
     * @param midia Mídia a ser removida
     * @throws IllegalArgumentException se a mídia for nula ou não for encontrada
     */
    public void removerMidia(Midia midia) {
        if (midia == null) {
            throw new IllegalArgumentException("A mídia não pode ser nula.");
        }
        
        boolean removido = this.midias.removeIf(m -> m.getLocal().equals(midia.getLocal()));
        
        if (!removido) {
            throw new IllegalArgumentException("Mídia não encontrada: " + midia.getLocal());
        }
        
        try {
            persistencia.deletarMidia(midia);
        } catch (IOException e) {
            System.err.println("Erro ao deletar arquivo .tpoo: " + e.getMessage());
        }
    }

    /**
     * Retorna uma cópia da lista de mídias.
     * 
     * @return Lista com todas as mídias cadastradas
     */
    public List<Midia> getMidias() {
        return new ArrayList<>(midias);
    }

    /**
     * Edita os dados de uma mídia existente e atualiza seu arquivo .tpoo.
     * 
     * @param midiaAntiga Mídia a ser editada
     * @param midiaNova Novos dados da mídia
     * @throws IllegalArgumentException se alguma mídia for nula ou a mídia antiga não for encontrada
     */
    public void editarMidia(Midia midiaAntiga, Midia midiaNova) {
        if (midiaAntiga == null || midiaNova == null) {
            throw new IllegalArgumentException("As mídias não podem ser nulas.");
        }
        
        boolean encontrado = false;
        for (int i = 0; i < midias.size(); i++) {
            if (midias.get(i).getLocal().equals(midiaAntiga.getLocal())) {
                midias.set(i, midiaNova);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            throw new IllegalArgumentException("Mídia não encontrada: " + midiaAntiga.getLocal());
        }
        
        try {
            if (!midiaAntiga.getLocal().equals(midiaNova.getLocal())) {
                persistencia.renomearArquivoMidia(midiaAntiga.getLocal(), midiaNova);
            } else {
                persistencia.salvarMidia(midiaNova);
            }
        } catch (IOException e) {
            System.err.println("Erro ao atualizar arquivo .tpoo: " + e.getMessage());
        }
    }

    /**
     * Renomeia o título e opcionalmente o local de uma mídia, atualizando seu arquivo .tpoo.
     * 
     * @param local Local atual da mídia
     * @param novoTitulo Novo título da mídia
     * @param novoLocal Novo local do arquivo (ou null para manter o mesmo)
     * @throws IllegalArgumentException se o local ou título forem inválidos ou a mídia não for encontrada
     */
    public void renomearMidia(String local, String novoTitulo, String novoLocal) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("O local não pode ser nulo ou vazio.");
        }
        if (novoTitulo == null || novoTitulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O novo título não pode ser nulo ou vazio.");
        }
        
        boolean encontrado = false;
        Midia midiaModificada = null;
        for (Midia m : midias) {
            if (m.getLocal().equals(local)) {
                m.setTitulo(novoTitulo);
                if (novoLocal != null && !novoLocal.trim().isEmpty()) {
                    m.setLocal(novoLocal);
                    m.atualizarTamanhoEmDisco();
                }
                midiaModificada = m;
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            throw new IllegalArgumentException("Mídia não encontrada no local: " + local);
        }
        
        try {
            if (novoLocal != null && !novoLocal.trim().isEmpty() && !local.equals(novoLocal)) {
                persistencia.renomearArquivoMidia(local, midiaModificada);
            } else {
                persistencia.salvarMidia(midiaModificada);
            }
        } catch (IOException e) {
            System.err.println("Erro ao atualizar arquivo .tpoo: " + e.getMessage());
        }
    }

    /**
     * Move uma mídia para um novo local e atualiza seu arquivo .tpoo.
     * 
     * @param localAntigo Local atual da mídia
     * @param localNovo Novo local da mídia
     * @throws IllegalArgumentException se os locais forem inválidos, o novo local já estiver em uso ou a mídia não for encontrada
     */
    public void moverMidia(String localAntigo, String localNovo) {
        if (localAntigo == null || localAntigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O local antigo não pode ser nulo ou vazio.");
        }
        if (localNovo == null || localNovo.trim().isEmpty()) {
            throw new IllegalArgumentException("O local novo não pode ser nulo ou vazio.");
        }
        
        for (Midia m : midias) {
            if (m.getLocal().equals(localNovo)) {
                throw new IllegalArgumentException("Já existe uma mídia no local de destino: " + localNovo);
            }
        }
        
        boolean encontrado = false;
        Midia midiaMovida = null;
        for (Midia m : midias) {
            if (m.getLocal().equals(localAntigo)) {
                m.setLocal(localNovo);
                m.atualizarTamanhoEmDisco();
                midiaMovida = m;
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            throw new IllegalArgumentException("Mídia não encontrada no local: " + localAntigo);
        }
        
        try {
            persistencia.renomearArquivoMidia(localAntigo, midiaMovida);
        } catch (IOException e) {
            System.err.println("Erro ao atualizar arquivo .tpoo: " + e.getMessage());
        }
    }

    /**
     * Lista mídias de uma categoria específica.
     * 
     * @param categoria Categoria a ser filtrada
     * @return Lista de mídias da categoria especificada
     */
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

    /**
     * Retorna lista de mídias ordenadas por título.
     * 
     * @return Lista ordenada alfabeticamente por título
     */
    public List<Midia> ordenarPorTitulo() {
        List<Midia> copia = new ArrayList<>(midias);
        copia.sort(Comparator.comparing(
                m -> (m.getTitulo() == null ? "" : m.getTitulo()),
                String.CASE_INSENSITIVE_ORDER
        ));
        return copia;
    }

    /**
     * Retorna lista de mídias ordenadas por duração.
     * 
     * @return Lista ordenada por duração crescente
     */
    public List<Midia> ordenarPorDuracao() {
        List<Midia> copia = new ArrayList<>(midias);
        copia.sort(Comparator.comparingInt(m -> m.getDuracao()));
        return copia;
    }

    /**
     * Filtra mídias por tipo e/ou categoria.
     * 
     * @param tipo Tipo de mídia (Filme, Música, Livro) ou null para todos
     * @param categoria Categoria da mídia ou null para todas
     * @return Lista de mídias que atendem aos critérios
     */
    public List<Midia> filtrarMidias(String tipo, String categoria) {
        List<Midia> resultado = new ArrayList<>();
        for (Midia m : midias) {
            boolean okTipo = (tipo == null || tipo.isEmpty());
            if (!okTipo) {
                if (tipo.equalsIgnoreCase("filme") && m instanceof Filme) {
                    okTipo = true;
                } else if (tipo.equalsIgnoreCase("música") && m instanceof Musica) {
                    okTipo = true;
                } else if (tipo.equalsIgnoreCase("livro") && m instanceof Livro) {
                    okTipo = true;
                }
            }
            
            boolean okCategoria = (categoria == null || categoria.isEmpty()) ||
                    (m.getCategoria() != null && m.getCategoria().equalsIgnoreCase(categoria));

            if (okTipo && okCategoria) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    /**
     * Carrega todas as mídias dos arquivos .tpoo individuais.
     */
    public void carregarMidias() {
        midias.clear();
        midias.addAll(persistencia.carregarTodasMidias());
    }

    /**
     * Salva todas as mídias em arquivos .tpoo individuais.
     */
    public void salvarMidias() {
        try {
            for (Midia m : midias) {
                persistencia.salvarMidia(m);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar mídias: " + e.getMessage());
            e.printStackTrace();
        }
    }
}