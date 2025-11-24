package gerenciador_midia.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FilmeTest {

    @TempDir
    Path tempDir;

    @Test
    void testConstrutor() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        Filme filme = new Filme(arquivo.getAbsolutePath(), "O Poderoso Chefão", 175, "Drama", "Inglês");
        
        assertEquals("O Poderoso Chefão", filme.getTitulo());
        assertEquals(175, filme.getDuracao());
        assertEquals("Drama", filme.getCategoria());
        assertEquals("Inglês", filme.getIdioma());
        assertEquals(arquivo.getAbsolutePath(), filme.getLocal());
    }

    @Test
    void testConstrutorComIdiomaInvalido() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", null));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", ""));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", "   "));
    }

    @Test
    void testConstrutorComFormatoInvalido() throws IOException {
        File arquivo = tempDir.resolve("filme.avi").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", "Inglês"));
    }

    @Test
    void testConstrutorComFormatosValidos() throws IOException {
        File mp4 = tempDir.resolve("filme.mp4").toFile();
        mp4.createNewFile();
        assertDoesNotThrow(() -> new Filme(mp4.getAbsolutePath(), "Filme MP4", 120, "Ação", "Inglês"));

        File mkv = tempDir.resolve("filme.mkv").toFile();
        mkv.createNewFile();
        assertDoesNotThrow(() -> new Filme(mkv.getAbsolutePath(), "Filme MKV", 120, "Ação", "Inglês"));
    }

    @Test
    void testGetSetIdioma() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        Filme filme = new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", "Inglês");
        
        filme.setIdioma("Português");
        assertEquals("Português", filme.getIdioma());
        
        filme.setIdioma("  Espanhol  ");
        assertEquals("Espanhol", filme.getIdioma());
    }

    @Test
    void testSetIdiomaInvalido() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        Filme filme = new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", "Inglês");
        
        assertThrows(IllegalArgumentException.class, () -> filme.setIdioma(null));
        assertThrows(IllegalArgumentException.class, () -> filme.setIdioma(""));
        assertThrows(IllegalArgumentException.class, () -> filme.setIdioma("   "));
    }

    @Test
    void testExibirDetalhes() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        Filme filme = new Filme(arquivo.getAbsolutePath(), "Matrix", 136, "Ficção Científica", "Inglês");
        
        String detalhes = filme.exibirDetalhes();
        
        assertTrue(detalhes.contains("Matrix"));
        assertTrue(detalhes.contains("Inglês"));
        assertTrue(detalhes.contains("136 Minutos"));
        assertTrue(detalhes.contains("Ficção Científica"));
    }

    @Test
    void testValidarFormato() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        Filme filme = new Filme(arquivo.getAbsolutePath(), "Filme", 120, "Ação", "Inglês");
        
        assertTrue(filme.validarFormato("MP4"));
        assertTrue(filme.validarFormato("MKV"));
        assertFalse(filme.validarFormato("AVI"));
        assertFalse(filme.validarFormato("MOV"));
        assertFalse(filme.validarFormato("MP3"));
    }

    @Test
    void testConstrutorComArquivoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme("C:\\arquivo_inexistente.mp4", "Filme", 120, "Ação", "Inglês"));
    }

    @Test
    void testConstrutorComLocalInvalido() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(null, "Filme", 120, "Ação", "Inglês"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme("", "Filme", 120, "Ação", "Inglês"));
    }

    @Test
    void testConstrutorComTituloInvalido() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), null, 120, "Ação", "Inglês"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "", 120, "Ação", "Inglês"));
    }

    @Test
    void testConstrutorComDuracaoInvalida() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", -1, "Ação", "Inglês"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 0, "Ação", "Inglês"));
    }

    @Test
    void testConstrutorComCategoriaInvalida() throws IOException {
        File arquivo = tempDir.resolve("filme.mp4").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 120, null, "Inglês"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Filme(arquivo.getAbsolutePath(), "Filme", 120, "", "Inglês"));
    }
}
