package gerenciador_midia.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilmeFormatoSuportadoTest {

    @Test
    @DisplayName("Deve conter formato MP4")
    void testContemMP4() {
        FilmeFormatoSuportado formato = FilmeFormatoSuportado.MP4;
        assertNotNull(formato);
        assertEquals("MP4", formato.name());
    }

    @Test
    @DisplayName("Deve conter formato MKV")
    void testContemMKV() {
        FilmeFormatoSuportado formato = FilmeFormatoSuportado.MKV;
        assertNotNull(formato);
        assertEquals("MKV", formato.name());
    }

    @Test
    @DisplayName("Deve ter exatamente 2 formatos")
    void testQuantidadeFormatos() {
        FilmeFormatoSuportado[] formatos = FilmeFormatoSuportado.values();
        assertEquals(2, formatos.length);
    }

    @Test
    @DisplayName("Deve aceitar valueOf com MP4")
    void testValueOfMP4() {
        FilmeFormatoSuportado formato = FilmeFormatoSuportado.valueOf("MP4");
        assertEquals(FilmeFormatoSuportado.MP4, formato);
    }

    @Test
    @DisplayName("Deve aceitar valueOf com MKV")
    void testValueOfMKV() {
        FilmeFormatoSuportado formato = FilmeFormatoSuportado.valueOf("MKV");
        assertEquals(FilmeFormatoSuportado.MKV, formato);
    }

    @Test
    @DisplayName("Deve lançar exceção para formato inválido")
    void testValueOfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            FilmeFormatoSuportado.valueOf("AVI")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção para formato em lowercase")
    void testValueOfLowercase() {
        assertThrows(IllegalArgumentException.class, () -> 
            FilmeFormatoSuportado.valueOf("mp4")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção para valor nulo")
    void testValueOfNulo() {
        assertThrows(NullPointerException.class, () -> 
            FilmeFormatoSuportado.valueOf(null)
        );
    }
}
