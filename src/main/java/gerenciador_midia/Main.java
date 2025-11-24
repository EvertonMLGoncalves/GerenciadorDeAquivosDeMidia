package gerenciador_midia;

import gerenciador_midia.view.TelaPrincipal;

import javax.swing.*;

/**
 * Classe principal da aplicação Gerenciador de Mídias.
 * Responsável por inicializar a interface gráfica.
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new TelaPrincipal());
    }
}
