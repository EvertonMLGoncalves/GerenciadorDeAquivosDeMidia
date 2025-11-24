package gerenciador_midia.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicaFormatoSuportadoTest {

    @Test
    @DisplayName("Deve conter formato MP3")
    void testContemMP3() {
        MusicaFormatoSuportado formato = MusicaFormatoSuportado.MP3;
        assertNotNull(formato);
        assertEquals("MP3", formato.name());
    }

    @Test
    @DisplayName("Deve ter exatamente 1 formato")
    void testQuantidadeFormatos() {
        MusicaFormatoSuportado[] formatos = MusicaFormatoSuportado.values();
        assertEquals(1, formatos.length);
    }

    @Test
    @DisplayName("Deve aceitar valueOf com MP3")
    void testValueOfMP3() {
        MusicaFormatoSuportado formato = MusicaFormatoSuportado.valueOf("MP3");
        assertEquals(MusicaFormatoSuportado.MP3, formato);
    }

    @Test
    @DisplayName("Deve lançar exceção para formato inválido")
    void testValueOfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            MusicaFormatoSuportado.valueOf("WAV")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção para formato em lowercase")
    void testValueOfLowercase() {
        assertThrows(IllegalArgumentException.class, () -> 
            MusicaFormatoSuportado.valueOf("mp3")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção para valor nulo")
    void testValueOfNulo() {
        assertThrows(NullPointerException.class, () -> 
            MusicaFormatoSuportado.valueOf(null)
        );
    }

    @Test
    @DisplayName("Deve comparar enums corretamente")
    void testComparacaoEnums() {
        MusicaFormatoSuportado formato1 = MusicaFormatoSuportado.MP3;
        MusicaFormatoSuportado formato2 = MusicaFormatoSuportado.valueOf("MP3");
        
        assertEquals(formato1, formato2);
        assertSame(formato1, formato2);
    }
}
