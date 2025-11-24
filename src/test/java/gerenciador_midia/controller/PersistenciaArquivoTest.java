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

class PersistenciaArquivoTest {

    @TempDir
    Path tempDir;

    private PersistenciaArquivo persistencia;
    private Filme filme;
    private Musica musica;
    private Livro livro;

    @BeforeEach
    void setUp() throws IOException {
        persistencia = new PersistenciaArquivo();

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
    @DisplayName("Deve salvar uma mídia Filme em arquivo .tpoo")
    void testSalvarFilme() throws IOException {
        persistencia.salvarMidia(filme);

        Path pastaMidias = Path.of("midias_data");
        assertTrue(Files.exists(pastaMidias), "Pasta midias_data deve existir");

        File[] arquivos = pastaMidias.toFile().listFiles((dir, name) -> name.endsWith(".tpoo"));
        assertNotNull(arquivos);
        assertTrue(arquivos.length > 0, "Deve haver pelo menos um arquivo .tpoo");

        String conteudo = Files.readString(arquivos[0].toPath());
        assertTrue(conteudo.contains("tipo=filme"));
        assertTrue(conteudo.contains("titulo=Matrix"));
        assertTrue(conteudo.contains("duracao=136"));
        assertTrue(conteudo.contains("categoria=Ficção Científica"));
        assertTrue(conteudo.contains("campoEspecifico=Inglês"));
    }

    @Test
    @DisplayName("Deve salvar uma mídia Música em arquivo .tpoo")
    void testSalvarMusica() throws IOException {
        persistencia.salvarMidia(musica);

        Path pastaMidias = Path.of("midias_data");
        assertTrue(Files.exists(pastaMidias));

        File[] arquivos = pastaMidias.toFile().listFiles((dir, name) -> name.endsWith(".tpoo"));
        assertNotNull(arquivos);
        assertTrue(arquivos.length > 0);

        String conteudo = Files.readString(arquivos[0].toPath());
        assertTrue(conteudo.contains("tipo=musica"));
        assertTrue(conteudo.contains("titulo=Bohemian Rhapsody"));
        assertTrue(conteudo.contains("duracao=354"));
        assertTrue(conteudo.contains("categoria=Rock"));
        assertTrue(conteudo.contains("campoEspecifico=Queen"));
    }

    @Test
    @DisplayName("Deve salvar uma mídia Livro em arquivo .tpoo")
    void testSalvarLivro() throws IOException {
        persistencia.salvarMidia(livro);

        Path pastaMidias = Path.of("midias_data");
        assertTrue(Files.exists(pastaMidias));

        File[] arquivos = pastaMidias.toFile().listFiles((dir, name) -> name.endsWith(".tpoo"));
        assertNotNull(arquivos);
        assertTrue(arquivos.length > 0);

        String conteudo = Files.readString(arquivos[0].toPath());
        assertTrue(conteudo.contains("tipo=livro"));
        assertTrue(conteudo.contains("titulo=Clean Code"));
        assertTrue(conteudo.contains("duracao=464"));
        assertTrue(conteudo.contains("categoria=Tecnologia"));
        assertTrue(conteudo.contains("campoEspecifico=Robert C. Martin"));
    }

    @Test
    @DisplayName("Deve carregar todas as mídias dos arquivos .tpoo")
    void testCarregarTodasMidias() throws IOException {
        persistencia.salvarMidia(filme);
        persistencia.salvarMidia(musica);
        persistencia.salvarMidia(livro);

        List<Midia> midias = persistencia.carregarTodasMidias();

        assertEquals(3, midias.size());

        boolean temFilme = midias.stream().anyMatch(m -> m instanceof Filme && m.getTitulo().equals("Matrix"));
        boolean temMusica = midias.stream().anyMatch(m -> m instanceof Musica && m.getTitulo().equals("Bohemian Rhapsody"));
        boolean temLivro = midias.stream().anyMatch(m -> m instanceof Livro && m.getTitulo().equals("Clean Code"));

        assertTrue(temFilme, "Deve conter o filme Matrix");
        assertTrue(temMusica, "Deve conter a música Bohemian Rhapsody");
        assertTrue(temLivro, "Deve conter o livro Clean Code");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há arquivos .tpoo")
    void testCarregarSemArquivos() {
        List<Midia> midias = persistencia.carregarTodasMidias();
        assertNotNull(midias);
    }

    @Test
    @DisplayName("Deve deletar arquivo .tpoo de uma mídia")
    void testDeletarMidia() throws IOException {
        persistencia.salvarMidia(filme);

        Path pastaMidias = Path.of("midias_data");
        File[] antesDelete = pastaMidias.toFile().listFiles((dir, name) -> name.endsWith(".tpoo"));
        assertNotNull(antesDelete);
        int quantidadeAntes = antesDelete.length;

        persistencia.deletarMidia(filme);

        File[] aposDelete = pastaMidias.toFile().listFiles((dir, name) -> name.endsWith(".tpoo"));
        int quantidadeDepois = (aposDelete != null) ? aposDelete.length : 0;

        assertTrue(quantidadeDepois < quantidadeAntes, "Quantidade de arquivos deve diminuir após deletar");
    }

    @Test
    @DisplayName("Não deve lançar exceção ao deletar mídia inexistente")
    void testDeletarMidiaInexistente() {
        assertDoesNotThrow(() -> persistencia.deletarMidia(filme));
    }

    @Test
    @DisplayName("Deve renomear arquivo .tpoo ao alterar local da mídia")
    void testRenomearArquivoMidia() throws IOException {
        persistencia.salvarMidia(filme);

        File novoArquivo = tempDir.resolve("matrix_novo.mp4").toFile();
        novoArquivo.createNewFile();
        Filme filmeNovo = new Filme(novoArquivo.getAbsolutePath(), "The Matrix", 136, "Ficção Científica", "Inglês");

        String localAntigo = filme.getLocal();
        persistencia.renomearArquivoMidia(localAntigo, filmeNovo);

        List<Midia> midias = persistencia.carregarTodasMidias();

        assertEquals(1, midias.size());
        assertEquals("The Matrix", midias.get(0).getTitulo());
        assertEquals(novoArquivo.getAbsolutePath(), midias.get(0).getLocal());
    }

    @Test
    @DisplayName("Deve criar pasta midias_data se não existir")
    void testCriarPastaSeNaoExistir() {
        PersistenciaArquivo novaPersistencia = new PersistenciaArquivo();
        
        Path pastaMidias = Path.of("midias_data");
        assertTrue(Files.exists(pastaMidias), "Pasta midias_data deve ser criada automaticamente");
    }

    @Test
    @DisplayName("Deve gerar nomes de arquivo únicos baseados no hash do local")
    void testNomesArquivosUnicos() throws IOException {
        persistencia.salvarMidia(filme);
        persistencia.salvarMidia(musica);

        Path pastaMidias = Path.of("midias_data");
        File[] arquivos = pastaMidias.toFile().listFiles((dir, name) -> name.endsWith(".tpoo"));

        assertNotNull(arquivos);
        assertEquals(2, arquivos.length);

        String nome1 = arquivos[0].getName();
        String nome2 = arquivos[1].getName();

        assertNotEquals(nome1, nome2, "Nomes de arquivo devem ser únicos");
    }

    @Test
    @DisplayName("Deve carregar Filme com todos os atributos corretos")
    void testCarregarFilmeCompleto() throws IOException {
        persistencia.salvarMidia(filme);

        List<Midia> midias = persistencia.carregarTodasMidias();
        Midia midiaCarregada = midias.stream()
                .filter(m -> m instanceof Filme)
                .findFirst()
                .orElse(null);

        assertNotNull(midiaCarregada);
        assertTrue(midiaCarregada instanceof Filme);

        Filme filmeCarregado = (Filme) midiaCarregada;
        assertEquals("Matrix", filmeCarregado.getTitulo());
        assertEquals(136, filmeCarregado.getDuracao());
        assertEquals("Ficção Científica", filmeCarregado.getCategoria());
        assertEquals("Inglês", filmeCarregado.getIdioma());
    }

    @Test
    @DisplayName("Deve carregar Música com todos os atributos corretos")
    void testCarregarMusicaCompleta() throws IOException {
        persistencia.salvarMidia(musica);

        List<Midia> midias = persistencia.carregarTodasMidias();
        Midia midiaCarregada = midias.stream()
                .filter(m -> m instanceof Musica)
                .findFirst()
                .orElse(null);

        assertNotNull(midiaCarregada);
        assertTrue(midiaCarregada instanceof Musica);

        Musica musicaCarregada = (Musica) midiaCarregada;
        assertEquals("Bohemian Rhapsody", musicaCarregada.getTitulo());
        assertEquals(354, musicaCarregada.getDuracao());
        assertEquals("Rock", musicaCarregada.getCategoria());
        assertEquals("Queen", musicaCarregada.getArtista());
    }

    @Test
    @DisplayName("Deve carregar Livro com todos os atributos corretos")
    void testCarregarLivroCompleto() throws IOException {
        persistencia.salvarMidia(livro);

        List<Midia> midias = persistencia.carregarTodasMidias();
        Midia midiaCarregada = midias.stream()
                .filter(m -> m instanceof Livro)
                .findFirst()
                .orElse(null);

        assertNotNull(midiaCarregada);
        assertTrue(midiaCarregada instanceof Livro);

        Livro livroCarregado = (Livro) midiaCarregada;
        assertEquals("Clean Code", livroCarregado.getTitulo());
        assertEquals(464, livroCarregado.getDuracao());
        assertEquals("Tecnologia", livroCarregado.getCategoria());
        assertEquals("Robert C. Martin", livroCarregado.getAutores());
    }

    @AfterEach
    void cleanup() throws IOException {
        Path pastaMidias = Path.of("midias_data");
        if (Files.exists(pastaMidias)) {
            File[] arquivos = pastaMidias.toFile().listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    arquivo.delete();
                }
            }
        }
    }
}
