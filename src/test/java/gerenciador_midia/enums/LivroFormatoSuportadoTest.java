package gerenciador_midia.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivroFormatoSuportadoTest {

    @Test
    @DisplayName("Deve conter formato PDF")
    void testContemPDF() {
        LivroFormatoSuportado formato = LivroFormatoSuportado.PDF;
        assertNotNull(formato);
        assertEquals("PDF", formato.name());
    }

    @Test
    @DisplayName("Deve conter formato EPUB")
    void testContemEPUB() {
        LivroFormatoSuportado formato = LivroFormatoSuportado.EPUB;
        assertNotNull(formato);
        assertEquals("EPUB", formato.name());
    }

    @Test
    @DisplayName("Deve ter exatamente 2 formatos")
    void testQuantidadeFormatos() {
        LivroFormatoSuportado[] formatos = LivroFormatoSuportado.values();
        assertEquals(2, formatos.length);
    }

    @Test
    @DisplayName("Deve aceitar valueOf com PDF")
    void testValueOfPDF() {
        LivroFormatoSuportado formato = LivroFormatoSuportado.valueOf("PDF");
        assertEquals(LivroFormatoSuportado.PDF, formato);
    }

    @Test
    @DisplayName("Deve aceitar valueOf com EPUB")
    void testValueOfEPUB() {
        LivroFormatoSuportado formato = LivroFormatoSuportado.valueOf("EPUB");
        assertEquals(LivroFormatoSuportado.EPUB, formato);
    }

    @Test
    @DisplayName("Deve lançar exceção para formato inválido")
    void testValueOfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            LivroFormatoSuportado.valueOf("DOCX")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção para formato em lowercase")
    void testValueOfLowercase() {
        assertThrows(IllegalArgumentException.class, () -> 
            LivroFormatoSuportado.valueOf("pdf")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção para valor nulo")
    void testValueOfNulo() {
        assertThrows(NullPointerException.class, () -> 
            LivroFormatoSuportado.valueOf(null)
        );
    }

    @Test
    @DisplayName("Deve comparar enums corretamente")
    void testComparacaoEnums() {
        LivroFormatoSuportado formato1 = LivroFormatoSuportado.PDF;
        LivroFormatoSuportado formato2 = LivroFormatoSuportado.valueOf("PDF");
        
        assertEquals(formato1, formato2);
        assertSame(formato1, formato2);
    }
}
