package gerenciador_midia.controller;

import java.io.*;
import java.nio.file.Path;

public class PersistenciaArquivo {

    private Path pasta;
    File f = new File("arquivo.txt");
    public PersistenciaArquivo(Path pasta) {
        this.pasta = pasta;
    }
    public void salvarMidia(Path midia) throws IOException {
        FileOutputStream fos = new FileOutputStream("arquivo");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(midia);
        oos.close();
    }
    }

