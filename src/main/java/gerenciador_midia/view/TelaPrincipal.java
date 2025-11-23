package gerenciador_midia.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JLabel jsTest;
    private JPanel TelaPrincipalPanel;
    private JTextField Inputteste;
    private JButton button1;

    public TelaPrincipal() {
        setContentPane(TelaPrincipalPanel);
        setTitle("Gerenciador de Mídia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 120);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TelaPrincipal.this, "Hello world!");
            }
        });
    }

    public static void main() {
        new TelaPrincipal();
    }
}
