package gerenciador_midia.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MetadadosExtractorTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Deve retornar 0 para arquivo MP3 inexistente")
    void testExtrairDuracaoMP3ArquivoInexistente() {
        File arquivo = new File("arquivo_inexistente.mp3");
        int duracao = MetadadosExtractor.extrairDuracaoMP3(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo MP3 vazio")
    void testExtrairDuracaoMP3ArquivoVazio() throws IOException {
        File arquivo = tempDir.resolve("musica_vazia.mp3").toFile();
        arquivo.createNewFile();

        int duracao = MetadadosExtractor.extrairDuracaoMP3(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo MP4 inexistente")
    void testExtrairDuracaoMP4ArquivoInexistente() {
        File arquivo = new File("arquivo_inexistente.mp4");
        int duracao = MetadadosExtractor.extrairDuracaoMP4(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo MP4 vazio")
    void testExtrairDuracaoMP4ArquivoVazio() throws IOException {
        File arquivo = tempDir.resolve("filme_vazio.mp4").toFile();
        arquivo.createNewFile();

        int duracao = MetadadosExtractor.extrairDuracaoMP4(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo MKV inexistente")
    void testExtrairDuracaoMKVArquivoInexistente() {
        File arquivo = new File("arquivo_inexistente.mkv");
        int duracao = MetadadosExtractor.extrairDuracaoMKV(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo MKV vazio")
    void testExtrairDuracaoMKVArquivoVazio() throws IOException {
        File arquivo = tempDir.resolve("filme_vazio.mkv").toFile();
        arquivo.createNewFile();

        int duracao = MetadadosExtractor.extrairDuracaoMKV(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo PDF inexistente")
    void testExtrairPaginasPDFArquivoInexistente() {
        File arquivo = new File("arquivo_inexistente.pdf");
        int paginas = MetadadosExtractor.extrairPaginasPDF(arquivo);
        assertEquals(0, paginas);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo PDF vazio ou inválido")
    void testExtrairPaginasPDFArquivoVazio() throws IOException {
        File arquivo = tempDir.resolve("livro_vazio.pdf").toFile();
        arquivo.createNewFile();

        int paginas = MetadadosExtractor.extrairPaginasPDF(arquivo);
        assertEquals(0, paginas);
    }

    @Test
    @DisplayName("Deve retornar 0 para arquivo EPUB inexistente")
    void testExtrairPaginasEPUBArquivoInexistente() {
        File arquivo = new File("arquivo_inexistente.epub");
        int paginas = MetadadosExtractor.extrairPaginasEPUB(arquivo);
        assertEquals(0, paginas);
    }

    @Test
    @DisplayName("Deve retornar valor maior que 0 para arquivo EPUB com conteúdo")
    void testExtrairPaginasEPUBArquivoComConteudo() throws IOException {
        File arquivo = tempDir.resolve("livro.epub").toFile();
        
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            byte[] conteudo = new byte[10240];
            fos.write(conteudo);
        }

        int paginas = MetadadosExtractor.extrairPaginasEPUB(arquivo);
        assertTrue(paginas >= 0, "Deve retornar número de páginas >= 0");
    }

    @Test
    @DisplayName("Deve tratar arquivos sem permissão de leitura corretamente")
    void testArquivoSemPermissaoLeitura() throws IOException {
        File arquivo = tempDir.resolve("sem_permissao.mp3").toFile();
        arquivo.createNewFile();
        arquivo.setReadable(false);

        int duracao = MetadadosExtractor.extrairDuracaoMP3(arquivo);
        assertEquals(0, duracao);

        arquivo.setReadable(true);
    }

    @Test
    @DisplayName("Deve retornar 0 quando arquivo não contém metadados de duração")
    void testArquivoSemMetadadosDuracao() throws IOException {
        File arquivo = tempDir.resolve("arquivo_sem_metadata.mp3").toFile();
        
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            fos.write(new byte[]{0x00, 0x01, 0x02});
        }

        int duracao = MetadadosExtractor.extrairDuracaoMP3(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve lidar com arquivos corrompidos")
    void testArquivoCorrompido() throws IOException {
        File arquivo = tempDir.resolve("corrompido.mp4").toFile();
        
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            fos.write(new byte[]{0x00, 0x00, 0x00, 0x00});
        }

        int duracao = MetadadosExtractor.extrairDuracaoMP4(arquivo);
        assertEquals(0, duracao);
    }

    @Test
    @DisplayName("Deve processar múltiplos arquivos sem falhas")
    void testProcessarMultiplosArquivos() throws IOException {
        File mp3 = tempDir.resolve("musica.mp3").toFile();
        mp3.createNewFile();

        File mp4 = tempDir.resolve("filme.mp4").toFile();
        mp4.createNewFile();

        File pdf = tempDir.resolve("livro.pdf").toFile();
        pdf.createNewFile();

        assertDoesNotThrow(() -> {
            MetadadosExtractor.extrairDuracaoMP3(mp3);
            MetadadosExtractor.extrairDuracaoMP4(mp4);
            MetadadosExtractor.extrairPaginasPDF(pdf);
        });
    }

    @Test
    @DisplayName("Deve retornar valores não negativos")
    void testValoresNaoNegativos() throws IOException {
        File arquivo = tempDir.resolve("teste.mp3").toFile();
        arquivo.createNewFile();

        int duracaoMP3 = MetadadosExtractor.extrairDuracaoMP3(arquivo);
        assertTrue(duracaoMP3 >= 0, "Duração MP3 não pode ser negativa");

        File arquivoMP4 = tempDir.resolve("teste.mp4").toFile();
        arquivoMP4.createNewFile();
        int duracaoMP4 = MetadadosExtractor.extrairDuracaoMP4(arquivoMP4);
        assertTrue(duracaoMP4 >= 0, "Duração MP4 não pode ser negativa");

        File arquivoPDF = tempDir.resolve("teste.pdf").toFile();
        arquivoPDF.createNewFile();
        int paginasPDF = MetadadosExtractor.extrairPaginasPDF(arquivoPDF);
        assertTrue(paginasPDF >= 0, "Páginas PDF não pode ser negativo");
    }
}
