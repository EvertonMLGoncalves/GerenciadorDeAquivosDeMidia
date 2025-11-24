package gerenciador_midia.controller;

import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Livro;
import gerenciador_midia.model.Midia;
import gerenciador_midia.model.Musica;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorMidiaTest {

    @TempDir
    Path tempDir;

    private GerenciadorMidia gerenciador;
    private Filme filme;
    private Musica musica;
    private Livro livro;

    @BeforeEach
    void setUp() throws IOException {
        Path pastaMidias = Path.of("midias_data");
        if (Files.exists(pastaMidias)) {
            File[] arquivos = pastaMidias.toFile().listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    arquivo.delete();
                }
            }
        }
        
        gerenciador = new GerenciadorMidia();
        gerenciador.getMidias().clear();

        File arquivoFilme = tempDir.resolve("filme.mp4").toFile();
        arquivoFilme.createNewFile();
        filme = new Filme(arquivoFilme.getAbsolutePath(), "Matrix", 136, "Ficção Científica", "Inglês");

        File arquivoMusica = tempDir.resolve("musica.mp3").toFile();
        arquivoMusica.createNewFile();
        musica = new Musica(arquivoMusica.getAbsolutePath(), "Bohemian Rhapsody", 354, "Rock", "Queen");

        File arquivoLivro = tempDir.resolve("livro.pdf").toFile();
        arquivoLivro.createNewFile();
        livro = new Livro(arquivoLivro.getAbsolutePath(), "Clean Code", 464, "Tecnologia", "Robert C. Martin");
    }

    @Test
    @DisplayName("Deve incluir uma mídia com sucesso")
    void testIncluirMidia() {
        gerenciador.incluirMidia(filme);

        assertEquals(1, gerenciador.getMidias().size());
        assertTrue(gerenciador.getMidias().contains(filme));
    }

    @Test
    @DisplayName("Deve lançar exceção ao incluir mídia nula")
    void testIncluirMidiaNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.incluirMidia(null)
        );
        assertEquals("A mídia não pode ser nula.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao incluir mídia duplicada")
    void testIncluirMidiaDuplicada() {
        gerenciador.incluirMidia(filme);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.incluirMidia(filme)
        );
        assertTrue(exception.getMessage().contains("Já existe uma mídia cadastrada neste local"));
    }

    @Test
    @DisplayName("Deve remover uma mídia com sucesso")
    void testRemoverMidia() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);

        assertEquals(2, gerenciador.getMidias().size());

        gerenciador.removerMidia(filme);

        assertEquals(1, gerenciador.getMidias().size());
        assertFalse(gerenciador.getMidias().contains(filme));
        assertTrue(gerenciador.getMidias().contains(musica));
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover mídia nula")
    void testRemoverMidiaNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.removerMidia(null)
        );
        assertEquals("A mídia não pode ser nula.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover mídia não encontrada")
    void testRemoverMidiaNaoEncontrada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.removerMidia(filme)
        );
        assertTrue(exception.getMessage().contains("Mídia não encontrada"));
    }

    @Test
    @DisplayName("Deve editar uma mídia com sucesso")
    void testEditarMidia() throws IOException {
        gerenciador.incluirMidia(filme);

        File novoArquivoFilme = tempDir.resolve("filme_editado.mp4").toFile();
        novoArquivoFilme.createNewFile();
        Filme filmeEditado = new Filme(novoArquivoFilme.getAbsolutePath(), "Matrix Reloaded", 138, "Ficção Científica", "Inglês");

        gerenciador.editarMidia(filme, filmeEditado);

        assertEquals(1, gerenciador.getMidias().size());
        assertEquals("Matrix Reloaded", gerenciador.getMidias().get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao editar mídia nula")
    void testEditarMidiaNula() throws IOException {
        File novoArquivo = tempDir.resolve("novo.mp4").toFile();
        novoArquivo.createNewFile();
        Filme novoFilme = new Filme(novoArquivo.getAbsolutePath(), "Filme", 120, "Ação", "Inglês");

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.editarMidia(null, novoFilme)
        );

        gerenciador.incluirMidia(filme);
        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.editarMidia(filme, null)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao editar mídia não encontrada")
    void testEditarMidiaNaoEncontrada() throws IOException {
        File novoArquivo = tempDir.resolve("novo.mp4").toFile();
        novoArquivo.createNewFile();
        Filme novoFilme = new Filme(novoArquivo.getAbsolutePath(), "Filme", 120, "Ação", "Inglês");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.editarMidia(filme, novoFilme)
        );
        assertTrue(exception.getMessage().contains("Mídia não encontrada"));
    }

    @Test
    @DisplayName("Deve renomear título da mídia com sucesso")
    void testRenomearMidia() {
        gerenciador.incluirMidia(filme);

        gerenciador.renomearMidia(filme.getLocal(), "The Matrix", null);

        assertEquals("The Matrix", gerenciador.getMidias().get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve renomear título e local da mídia com sucesso")
    void testRenomearMidiaComNovoLocal() throws IOException {
        gerenciador.incluirMidia(filme);

        File novoArquivo = tempDir.resolve("matrix_renamed.mp4").toFile();
        novoArquivo.createNewFile();

        gerenciador.renomearMidia(filme.getLocal(), "The Matrix", novoArquivo.getAbsolutePath());

        assertEquals("The Matrix", gerenciador.getMidias().get(0).getTitulo());
        assertEquals(novoArquivo.getAbsolutePath(), gerenciador.getMidias().get(0).getLocal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao renomear mídia com local inválido")
    void testRenomearMidiaLocalInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia(null, "Novo Título", null)
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia("", "Novo Título", null)
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia("   ", "Novo Título", null)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao renomear mídia com título inválido")
    void testRenomearMidiaTituloInvalido() {
        gerenciador.incluirMidia(filme);

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia(filme.getLocal(), null, null)
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia(filme.getLocal(), "", null)
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia(filme.getLocal(), "   ", null)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao renomear mídia não encontrada")
    void testRenomearMidiaNaoEncontrada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.renomearMidia("local_inexistente", "Novo Título", null)
        );
        assertTrue(exception.getMessage().contains("Mídia não encontrada no local"));
    }

    @Test
    @DisplayName("Deve mover mídia para novo local com sucesso")
    void testMoverMidia() throws IOException {
        gerenciador.incluirMidia(filme);

        File novoArquivo = tempDir.resolve("filmes/matrix.mp4").toFile();
        novoArquivo.getParentFile().mkdirs();
        novoArquivo.createNewFile();

        gerenciador.moverMidia(filme.getLocal(), novoArquivo.getAbsolutePath());

        assertEquals(novoArquivo.getAbsolutePath(), gerenciador.getMidias().get(0).getLocal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao mover mídia com locais inválidos")
    void testMoverMidiaLocaisInvalidos() {
        gerenciador.incluirMidia(filme);

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.moverMidia(null, "novo_local")
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.moverMidia(filme.getLocal(), null)
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.moverMidia("", "novo_local")
        );

        assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.moverMidia(filme.getLocal(), "")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao mover mídia para local já ocupado")
    void testMoverMidiaLocalOcupado() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.moverMidia(filme.getLocal(), musica.getLocal())
        );
        assertTrue(exception.getMessage().contains("Já existe uma mídia no local de destino"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao mover mídia não encontrada")
    void testMoverMidiaNaoEncontrada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            gerenciador.moverMidia("local_inexistente", "novo_local")
        );
        assertTrue(exception.getMessage().contains("Mídia não encontrada no local"));
    }

    @Test
    @DisplayName("Deve listar mídias por categoria")
    void testListarPorCategoria() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> tecnologia = gerenciador.listarPorCategoria("Tecnologia");
        assertEquals(1, tecnologia.size());
        assertTrue(tecnologia.contains(livro));

        List<Midia> rock = gerenciador.listarPorCategoria("Rock");
        assertEquals(1, rock.size());
        assertTrue(rock.contains(musica));

        List<Midia> inexistente = gerenciador.listarPorCategoria("Inexistente");
        assertEquals(0, inexistente.size());
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao buscar categoria nula")
    void testListarPorCategoriaNula() {
        gerenciador.incluirMidia(filme);
        
        List<Midia> resultado = gerenciador.listarPorCategoria(null);
        assertEquals(0, resultado.size());
    }

    @Test
    @DisplayName("Deve ordenar mídias por título")
    void testOrdenarPorTitulo() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> ordenadas = gerenciador.ordenarPorTitulo();

        assertEquals("Bohemian Rhapsody", ordenadas.get(0).getTitulo());
        assertEquals("Clean Code", ordenadas.get(1).getTitulo());
        assertEquals("Matrix", ordenadas.get(2).getTitulo());
    }

    @Test
    @DisplayName("Deve ordenar mídias por duração")
    void testOrdenarPorDuracao() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> ordenadas = gerenciador.ordenarPorDuracao();

        assertEquals(136, ordenadas.get(0).getDuracao());
        assertEquals(354, ordenadas.get(1).getDuracao());
        assertEquals(464, ordenadas.get(2).getDuracao());
    }

    @Test
    @DisplayName("Deve filtrar mídias por tipo Filme")
    void testFiltrarPorTipoFilme() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> filmes = gerenciador.filtrarMidias("filme", null);

        assertEquals(1, filmes.size());
        assertTrue(filmes.get(0) instanceof Filme);
    }

    @Test
    @DisplayName("Deve filtrar mídias por tipo Música")
    void testFiltrarPorTipoMusica() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> musicas = gerenciador.filtrarMidias("música", null);

        assertEquals(1, musicas.size());
        assertTrue(musicas.get(0) instanceof Musica);
    }

    @Test
    @DisplayName("Deve filtrar mídias por tipo Livro")
    void testFiltrarPorTipoLivro() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> livros = gerenciador.filtrarMidias("livro", null);

        assertEquals(1, livros.size());
        assertTrue(livros.get(0) instanceof Livro);
    }

    @Test
    @DisplayName("Deve filtrar mídias por categoria")
    void testFiltrarPorCategoria() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> rock = gerenciador.filtrarMidias(null, "Rock");

        assertEquals(1, rock.size());
        assertEquals("Rock", rock.get(0).getCategoria());
    }

    @Test
    @DisplayName("Deve filtrar mídias por tipo e categoria")
    void testFiltrarPorTipoECategoria() throws IOException {
        File arquivo2 = tempDir.resolve("filme2.mp4").toFile();
        arquivo2.createNewFile();
        Filme filmeAcao = new Filme(arquivo2.getAbsolutePath(), "Die Hard", 131, "Ação", "Inglês");

        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(filmeAcao);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> filmesAcao = gerenciador.filtrarMidias("filme", "Ação");

        assertEquals(1, filmesAcao.size());
        assertEquals("Die Hard", filmesAcao.get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve retornar todas as mídias ao filtrar sem critérios")
    void testFiltrarSemCriterios() {
        gerenciador.incluirMidia(filme);
        gerenciador.incluirMidia(musica);
        gerenciador.incluirMidia(livro);

        List<Midia> todas = gerenciador.filtrarMidias(null, null);

        assertEquals(3, todas.size());
    }

    @Test
    @DisplayName("Deve retornar cópia da lista de mídias")
    void testGetMidiasRetornaCopia() {
        gerenciador.incluirMidia(filme);

        List<Midia> lista1 = gerenciador.getMidias();
        List<Midia> lista2 = gerenciador.getMidias();

        assertNotSame(lista1, lista2);
        assertEquals(lista1.size(), lista2.size());
    }
}