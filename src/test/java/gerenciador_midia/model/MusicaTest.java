package gerenciador_midia.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MusicaTest {

    @TempDir
    Path tempDir;

    @Test
    void testConstrutor() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        Musica musica = new Musica(arquivo.getAbsolutePath(), "Bohemian Rhapsody", 354, "Rock", "Queen");
        
        assertEquals("Bohemian Rhapsody", musica.getTitulo());
        assertEquals(354, musica.getDuracao());
        assertEquals("Rock", musica.getCategoria());
        assertEquals("Queen", musica.getArtista());
        assertEquals(arquivo.getAbsolutePath(), musica.getLocal());
    }

    @Test
    void testConstrutorComArtistaInvalido() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", null));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", ""));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", "   "));
    }

    @Test
    void testConstrutorComFormatoInvalido() throws IOException {
        File arquivo = tempDir.resolve("musica.wav").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", "Artista"));
    }

    @Test
    void testConstrutorComFormatoValido() throws IOException {
        File mp3 = tempDir.resolve("musica.mp3").toFile();
        mp3.createNewFile();
        assertDoesNotThrow(() -> new Musica(mp3.getAbsolutePath(), "Música MP3", 180, "Pop", "Artista"));
    }

    @Test
    void testGetSetArtista() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        Musica musica = new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", "Artista Original");
        
        musica.setArtista("Novo Artista");
        assertEquals("Novo Artista", musica.getArtista());
        
        musica.setArtista("  The Beatles  ");
        assertEquals("The Beatles", musica.getArtista());
    }

    @Test
    void testSetArtistaInvalido() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        Musica musica = new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", "Artista");
        
        assertThrows(IllegalArgumentException.class, () -> musica.setArtista(null));
        assertThrows(IllegalArgumentException.class, () -> musica.setArtista(""));
        assertThrows(IllegalArgumentException.class, () -> musica.setArtista("   "));
    }

    @Test
    void testExibirDetalhes() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        Musica musica = new Musica(arquivo.getAbsolutePath(), "Imagine", 183, "Pop", "John Lennon");
        
        String detalhes = musica.exibirDetalhes();
        
        assertTrue(detalhes.contains("Imagine"));
        assertTrue(detalhes.contains("John Lennon"));
        assertTrue(detalhes.contains("183 Segundos"));
        assertTrue(detalhes.contains("Pop"));
    }

    @Test
    void testValidarFormato() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        Musica musica = new Musica(arquivo.getAbsolutePath(), "Música", 180, "Pop", "Artista");
        
        assertTrue(musica.validarFormato("MP3"));
        assertFalse(musica.validarFormato("WAV"));
        assertFalse(musica.validarFormato("FLAC"));
        assertFalse(musica.validarFormato("OGG"));
        assertFalse(musica.validarFormato("MP4"));
    }

    @Test
    void testConstrutorComArquivoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica("C:\\arquivo_inexistente.mp3", "Música", 180, "Pop", "Artista"));
    }

    @Test
    void testConstrutorComLocalInvalido() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(null, "Música", 180, "Pop", "Artista"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica("", "Música", 180, "Pop", "Artista"));
    }

    @Test
    void testConstrutorComTituloInvalido() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), null, 180, "Pop", "Artista"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "", 180, "Pop", "Artista"));
    }

    @Test
    void testConstrutorComDuracaoInvalida() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", -1, "Pop", "Artista"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 0, "Pop", "Artista"));
    }

    @Test
    void testConstrutorComCategoriaInvalida() throws IOException {
        File arquivo = tempDir.resolve("musica.mp3").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 180, null, "Artista"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Musica(arquivo.getAbsolutePath(), "Música", 180, "", "Artista"));
    }
}
