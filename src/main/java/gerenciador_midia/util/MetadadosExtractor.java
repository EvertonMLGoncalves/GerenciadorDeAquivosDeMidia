package gerenciador_midia.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * Extrator de metadados de arquivos de mídia.
 * Utiliza Apache Tika para áudio/vídeo e Apache PDFBox para documentos PDF.
 */
public class MetadadosExtractor {

    /**
     * Extrai a duração de um arquivo MP3 em segundos.
     * 
     * @param arquivo Arquivo MP3
     * @return Duração em segundos ou 0 se não for possível extrair
     */
    public static int extrairDuracaoMP3(File arquivo) {
        if (!arquivo.exists() || !arquivo.canRead()) {
            return 0;
        }

        try (FileInputStream inputStream = new FileInputStream(arquivo)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();
            
            parser.parse(inputStream, handler, metadata, context);
            
            String duracao = metadata.get("xmpDM:duration");
            if (duracao != null) {
                try {
                    return (int) Double.parseDouble(duracao);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao extrair duração do MP3: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Extrai a duração de um arquivo MP4 em minutos.
     * 
     * @param arquivo Arquivo MP4
     * @return Duração em minutos ou 0 se não for possível extrair
     */
    public static int extrairDuracaoMP4(File arquivo) {
        if (!arquivo.exists() || !arquivo.canRead()) {
            return 0;
        }

        try (FileInputStream inputStream = new FileInputStream(arquivo)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();
            
            parser.parse(inputStream, handler, metadata, context);
            
            String duracao = metadata.get("xmpDM:duration");
            if (duracao != null) {
                try {
                    double segundos = Double.parseDouble(duracao);
                    return (int) Math.round(segundos / 60.0);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao extrair duração do MP4: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Extrai a duração de um arquivo MKV em minutos.
     * 
     * @param arquivo Arquivo MKV
     * @return Duração em minutos ou 0 se não for possível extrair
     */
    public static int extrairDuracaoMKV(File arquivo) {
        if (!arquivo.exists() || !arquivo.canRead()) {
            return 0;
        }

        try (FileInputStream inputStream = new FileInputStream(arquivo)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();
            
            parser.parse(inputStream, handler, metadata, context);
            
            String duracao = metadata.get("xmpDM:duration");
            if (duracao != null) {
                try {
                    double segundos = Double.parseDouble(duracao);
                    return (int) Math.round(segundos / 60.0);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao extrair duração do MKV: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Extrai o número de páginas de um arquivo PDF.
     * 
     * @param arquivo Arquivo PDF
     * @return Número de páginas ou 0 se não for possível extrair
     */
    public static int extrairPaginasPDF(File arquivo) {
        if (!arquivo.exists() || !arquivo.canRead()) {
            return 0;
        }

        try (PDDocument document = org.apache.pdfbox.Loader.loadPDF(arquivo)) {
            return document.getNumberOfPages();
        } catch (Exception e) {
            System.err.println("Erro ao extrair páginas do PDF: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Extrai estimativa de páginas de um arquivo EPUB.
     * Utiliza contagem de palavras como base de cálculo.
     * 
     * @param arquivo Arquivo EPUB
     * @return Estimativa de páginas ou 0 se não for possível extrair
     */
    public static int extrairPaginasEPUB(File arquivo) {
        if (!arquivo.exists() || !arquivo.canRead()) {
            return 0;
        }

        try (FileInputStream inputStream = new FileInputStream(arquivo)) {
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();
            
            parser.parse(inputStream, handler, metadata, context);
            
            String texto = handler.toString();
            int palavras = texto.split("\\s+").length;
            return Math.max(1, palavras / 500);
            
        } catch (Exception e) {
            System.err.println("Erro ao extrair páginas do EPUB: " + e.getMessage());
            return (int) (arquivo.length() / (2 * 1024));
        }
    }
}
