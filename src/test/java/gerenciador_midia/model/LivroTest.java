package gerenciador_midia.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LivroTest {

    @TempDir
    Path tempDir;

    @Test
    void testConstrutor() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        Livro livro = new Livro(arquivo.getAbsolutePath(), "Clean Code", 464, "Tecnologia", "Robert C. Martin");
        
        assertEquals("Clean Code", livro.getTitulo());
        assertEquals(464, livro.getDuracao());
        assertEquals("Tecnologia", livro.getCategoria());
        assertEquals("Robert C. Martin", livro.getAutores());
        assertEquals(arquivo.getAbsolutePath(), livro.getLocal());
    }

    @Test
    void testConstrutorComAutoresInvalidos() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", null));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", ""));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", "   "));
    }

    @Test
    void testConstrutorComFormatoInvalido() throws IOException {
        File arquivo = tempDir.resolve("livro.txt").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", "Autor"));
    }

    @Test
    void testConstrutorComFormatosValidos() throws IOException {
        File pdf = tempDir.resolve("livro.pdf").toFile();
        pdf.createNewFile();
        assertDoesNotThrow(() -> new Livro(pdf.getAbsolutePath(), "Livro PDF", 300, "Ficção", "Autor"));

        File epub = tempDir.resolve("livro.epub").toFile();
        epub.createNewFile();
        assertDoesNotThrow(() -> new Livro(epub.getAbsolutePath(), "Livro EPUB", 300, "Ficção", "Autor"));
    }

    @Test
    void testGetSetAutores() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        Livro livro = new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", "Autor Original");
        
        livro.setAutores("Novo Autor");
        assertEquals("Novo Autor", livro.getAutores());
        
        livro.setAutores("  Martin Fowler  ");
        assertEquals("Martin Fowler", livro.getAutores());
    }

    @Test
    void testSetAutoresInvalidos() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        Livro livro = new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", "Autor");
        
        assertThrows(IllegalArgumentException.class, () -> livro.setAutores(null));
        assertThrows(IllegalArgumentException.class, () -> livro.setAutores(""));
        assertThrows(IllegalArgumentException.class, () -> livro.setAutores("   "));
    }

    @Test
    void testExibirDetalhes() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        Livro livro = new Livro(arquivo.getAbsolutePath(), "Design Patterns", 395, "Engenharia de Software", "Gang of Four");
        
        String detalhes = livro.exibirDetalhes();
        
        assertTrue(detalhes.contains("Design Patterns"));
        assertTrue(detalhes.contains("Gang of Four"));
        assertTrue(detalhes.contains("395 Páginas"));
        assertTrue(detalhes.contains("Engenharia de Software"));
    }

    @Test
    void testValidarFormato() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        Livro livro = new Livro(arquivo.getAbsolutePath(), "Livro", 300, "Ficção", "Autor");
        
        assertTrue(livro.validarFormato("PDF"));
        assertTrue(livro.validarFormato("EPUB"));
        assertFalse(livro.validarFormato("TXT"));
        assertFalse(livro.validarFormato("DOCX"));
        assertFalse(livro.validarFormato("MP3"));
    }

    @Test
    void testConstrutorComArquivoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro("C:\\arquivo_inexistente.pdf", "Livro", 300, "Ficção", "Autor"));
    }

    @Test
    void testConstrutorComLocalInvalido() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(null, "Livro", 300, "Ficção", "Autor"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro("", "Livro", 300, "Ficção", "Autor"));
    }

    @Test
    void testConstrutorComTituloInvalido() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), null, 300, "Ficção", "Autor"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "", 300, "Ficção", "Autor"));
    }

    @Test
    void testConstrutorComDuracaoInvalida() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", -1, "Ficção", "Autor"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 0, "Ficção", "Autor"));
    }

    @Test
    void testConstrutorComCategoriaInvalida() throws IOException {
        File arquivo = tempDir.resolve("livro.pdf").toFile();
        arquivo.createNewFile();
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 300, null, "Autor"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Livro(arquivo.getAbsolutePath(), "Livro", 300, "", "Autor"));
    }
}
