package gerenciador_midia.controller;

import gerenciador_midia.model.Midia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArquivo {

    private Path pasta;

    public PersistenciaArquivo(Path pasta) {
        this.pasta = pasta;
        criarPastaSeNaoExistir();
    }

    private void criarPastaSeNaoExistir() {
        File diretorio = pasta.toFile();
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    public void salvarMidia(Midia midia) throws IOException {
        List<Midia> midias = carregarTodasMidias();

        boolean encontrado = false;
        for (int i = 0; i < midias.size(); i++) {
            if (midias.get(i).getLocal().equals(midia.getLocal())) {
                midias.set(i, midia);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            midias.add(midia);
        }

        salvarTodasMidias(midias);
    }

    private void salvarTodasMidias(List<Midia> midias) throws IOException {
        File arquivo = pasta.resolve("midias.dat").toFile();

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            oos.writeObject(midias);
            System.out.println("Mídias salvas com sucesso!");
        }
    }

    public List<Midia> carregarTodasMidias() {
        List<Midia> midias = new ArrayList<>();
        File arquivo = pasta.resolve("midias.dat").toFile();

        if (!arquivo.exists()) {
            return midias;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(arquivo))) {
            midias = (List<Midia>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar mídias: " + e.getMessage());
        }

        return midias;
    }

    public void deletarMidia(Midia midia) throws IOException {
        List<Midia> midias = carregarTodasMidias();
        midias.removeIf(m -> m.getLocal().equals(midia.getLocal()));
        salvarTodasMidias(midias);
    }
}