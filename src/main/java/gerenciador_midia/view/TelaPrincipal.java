package gerenciador_midia.view;

import gerenciador_midia.controller.GerenciadorMidia;
import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Livro;
import gerenciador_midia.model.Midia;
import gerenciador_midia.model.Musica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Comparator;
import java.util.List;

public class TelaPrincipal extends JFrame {
    
    private GerenciadorMidia gerenciador;
    
    private JTable tabelaMidias;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboFiltroTipo;
    private JTextField txtFiltroCategoria;
    private JButton btnFiltrar;
    private JButton btnLimparFiltros;
    
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnRemover;
    private JButton btnRenomear;
    private JButton btnMover;
    private JButton btnDetalhes;
    private JLabel lblStatus;
    
    private int colunaOrdenada = -1;
    private boolean ordemCrescente = true;
    private String[] nomesColunas = {"Tipo", "Título", "Duração", "Categoria", "Formato", "Tamanho (MB)"};
    
    public TelaPrincipal() {
        gerenciador = new GerenciadorMidia();
        
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        atualizarTabela(gerenciador.getMidias());
        
        setTitle("Gerenciador de Mídias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        modeloTabela = new DefaultTableModel(nomesColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMidias = new JTable(modeloTabela);
        tabelaMidias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMidias.getTableHeader().setReorderingAllowed(false);
        
        JTableHeader header = tabelaMidias.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int coluna = header.columnAtPoint(e.getPoint());
                ordenarPorColuna(coluna);
            }
        });
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        comboFiltroTipo = new JComboBox<>(new String[]{"Todos", "Filme", "Música", "Livro"});
        txtFiltroCategoria = new JTextField(15);
        btnFiltrar = new JButton("Filtrar");
        btnLimparFiltros = new JButton("Limpar Filtros");
        
        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnRemover = new JButton("Remover");
        btnRenomear = new JButton("Renomear");
        btnMover = new JButton("Mover");
        btnDetalhes = new JButton("Detalhes");
        
        lblStatus = new JLabel("Pronto");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        atualizarEstadoBotoes();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        painelFiltros.add(new JLabel("Tipo:"));
        painelFiltros.add(comboFiltroTipo);
        painelFiltros.add(new JLabel("Categoria:"));
        painelFiltros.add(txtFiltroCategoria);
        painelFiltros.add(btnFiltrar);
        painelFiltros.add(btnLimparFiltros);
        
        add(painelFiltros, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(tabelaMidias);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new GridLayout(0, 1, 5, 5));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnRenomear);
        painelBotoes.add(btnMover);
        painelBotoes.add(btnDetalhes);
        
        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.add(painelBotoes, BorderLayout.NORTH);
        painelDireito.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        add(painelDireito, BorderLayout.EAST);
        
        add(lblStatus, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        tabelaMidias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    exibirDetalhes();
                }
            }
        });
        
        tabelaMidias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarEstadoBotoes();
            }
        });
        
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        btnLimparFiltros.addActionListener(e -> limparFiltros());
        txtFiltroCategoria.addActionListener(e -> aplicarFiltros());
        
        btnAdicionar.addActionListener(e -> adicionarMidia());
        btnEditar.addActionListener(e -> editarMidia());
        btnRemover.addActionListener(e -> removerMidia());
        btnRenomear.addActionListener(e -> renomearMidia());
        btnMover.addActionListener(e -> moverMidia());
        btnDetalhes.addActionListener(e -> exibirDetalhes());
    }
    
    private void atualizarEstadoBotoes() {
        boolean temSelecao = tabelaMidias.getSelectedRow() != -1;
        btnEditar.setEnabled(temSelecao);
        btnRemover.setEnabled(temSelecao);
        btnRenomear.setEnabled(temSelecao);
        btnMover.setEnabled(temSelecao);
        btnDetalhes.setEnabled(temSelecao);
    }
    
    private void atualizarTabela(List<Midia> midias) {
        modeloTabela.setRowCount(0);
        
        for (Midia midia : midias) {
            String tipo = "";
            if (midia instanceof Filme) tipo = "Filme";
            else if (midia instanceof Musica) tipo = "Música";
            else if (midia instanceof Livro) tipo = "Livro";
            
            Object[] linha = {
                tipo,
                midia.getTitulo(),
                midia.getDuracao(),
                midia.getCategoria(),
                midia.getFormato(),
                String.format("%.2f", midia.getTamanhoEmDisco())
            };
            modeloTabela.addRow(linha);
        }
        
        atualizarCabecalhosOrdenacao();
        
        lblStatus.setText("Total de mídias: " + midias.size());
    }
    
    private void aplicarFiltros() {
        String tipoSelecionado = (String) comboFiltroTipo.getSelectedItem();
        String categoria = txtFiltroCategoria.getText().trim();
        
        String tipo = null;
        if (!"Todos".equals(tipoSelecionado)) {
            tipo = tipoSelecionado;
        }
        
        List<Midia> midiasFiltradas = gerenciador.filtrarMidias(
            tipo,
            categoria.isEmpty() ? null : categoria
        );
        
        atualizarTabela(midiasFiltradas);
        lblStatus.setText("Filtros aplicados - " + midiasFiltradas.size() + " mídia(s) encontrada(s)");
    }
    
    private void limparFiltros() {
        comboFiltroTipo.setSelectedIndex(0);
        txtFiltroCategoria.setText("");
        colunaOrdenada = -1;
        atualizarTabela(gerenciador.getMidias());
        lblStatus.setText("Filtros removidos - Total de mídias: " + gerenciador.getMidias().size());
    }
    
    private void adicionarMidia() {
        DialogoMidia dialogo = new DialogoMidia(this);
        dialogo.setVisible(true);
        
        if (dialogo.isConfirmado()) {
            try {
                Midia novaMidia = dialogo.getMidia();
                gerenciador.incluirMidia(novaMidia);
                gerenciador.salvarMidias();
                atualizarTabela(gerenciador.getMidias());
                lblStatus.setText("Mídia adicionada com sucesso!");
                JOptionPane.showMessageDialog(this, 
                    "Mídia adicionada com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao adicionar mídia: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                lblStatus.setText("Erro ao adicionar mídia");
            }
        }
    }
    
    private void editarMidia() {
        int linhaSelecionada = tabelaMidias.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        List<Midia> midiasAtuais = obterMidiasExibidas();
        Midia midiaAntiga = midiasAtuais.get(linhaSelecionada);
        
        DialogoMidia dialogo = new DialogoMidia(this, midiaAntiga);
        dialogo.setVisible(true);
        
        if (dialogo.isConfirmado()) {
            try {
                Midia midiaNova = dialogo.getMidia();
                gerenciador.editarMidia(midiaAntiga, midiaNova);
                gerenciador.salvarMidias();
                atualizarTabela(gerenciador.getMidias());
                lblStatus.setText("Mídia editada com sucesso!");
                JOptionPane.showMessageDialog(this, 
                    "Mídia editada com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao editar mídia: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                lblStatus.setText("Erro ao editar mídia");
            }
        }
    }
    
    private void removerMidia() {
        int linhaSelecionada = tabelaMidias.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        List<Midia> midiasAtuais = obterMidiasExibidas();
        Midia midia = midiasAtuais.get(linhaSelecionada);
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente remover a mídia '" + midia.getTitulo() + "'?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                gerenciador.removerMidia(midia);
                gerenciador.salvarMidias();
                atualizarTabela(gerenciador.getMidias());
                lblStatus.setText("Mídia removida com sucesso!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao remover mídia: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                lblStatus.setText("Erro ao remover mídia");
            }
        }
    }
    
    private void renomearMidia() {
        int linhaSelecionada = tabelaMidias.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        List<Midia> midiasAtuais = obterMidiasExibidas();
        Midia midia = midiasAtuais.get(linhaSelecionada);
        
        String novoTitulo = JOptionPane.showInputDialog(this,
            "Digite o novo título:",
            midia.getTitulo());
        
        if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
            try {
                File arquivoAtual = new File(midia.getLocal());
                
                if (arquivoAtual.exists()) {
                    String pastaAtual = arquivoAtual.getParent();
                    String extensao = "";
                    String nomeAtual = arquivoAtual.getName();
                    int ultimoPonto = nomeAtual.lastIndexOf('.');
                    if (ultimoPonto > 0) {
                        extensao = nomeAtual.substring(ultimoPonto);
                    }
                    
                    File arquivoNovo = new File(pastaAtual, novoTitulo.trim() + extensao);
                    
                    if (arquivoNovo.exists()) {
                        JOptionPane.showMessageDialog(this,
                            "Já existe um arquivo com este nome no mesmo diretório.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (arquivoAtual.renameTo(arquivoNovo)) {
                        gerenciador.renomearMidia(midia.getLocal(), novoTitulo.trim(), arquivoNovo.getAbsolutePath());
                        gerenciador.salvarMidias();
                        atualizarTabela(gerenciador.getMidias());
                        lblStatus.setText("Arquivo renomeado com sucesso!");
                        JOptionPane.showMessageDialog(this,
                            "Arquivo renomeado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Não foi possível renomear o arquivo fisicamente.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    gerenciador.renomearMidia(midia.getLocal(), novoTitulo.trim(), null);
                    gerenciador.salvarMidias();
                    atualizarTabela(gerenciador.getMidias());
                    lblStatus.setText("Título atualizado (arquivo não encontrado)");
                    JOptionPane.showMessageDialog(this,
                        "Título atualizado no registro.\n(Arquivo físico não encontrado)",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao renomear: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void moverMidia() {
        int linhaSelecionada = tabelaMidias.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        List<Midia> midiasAtuais = obterMidiasExibidas();
        Midia midia = midiasAtuais.get(linhaSelecionada);
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Selecione a nova pasta");
        
        File arquivoAtual = new File(midia.getLocal());
        if (arquivoAtual.getParentFile() != null) {
            fileChooser.setCurrentDirectory(arquivoAtual.getParentFile());
        }
        
        int resultado = fileChooser.showDialog(this, "Mover para esta pasta");
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File novaPasta = fileChooser.getSelectedFile();
            File arquivoOrigem = new File(midia.getLocal());
            File arquivoDestino = new File(novaPasta, arquivoOrigem.getName());
            
            try {
                if (arquivoOrigem.exists()) {
                    if (arquivoOrigem.renameTo(arquivoDestino)) {
                        gerenciador.moverMidia(midia.getLocal(), arquivoDestino.getAbsolutePath());
                        gerenciador.salvarMidias();
                        atualizarTabela(gerenciador.getMidias());
                        lblStatus.setText("Mídia movida com sucesso!");
                        JOptionPane.showMessageDialog(this, 
                            "Mídia movida com sucesso!", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Não foi possível mover o arquivo fisicamente.", 
                            "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    gerenciador.moverMidia(midia.getLocal(), arquivoDestino.getAbsolutePath());
                    gerenciador.salvarMidias();
                    atualizarTabela(gerenciador.getMidias());
                    lblStatus.setText("Localização da mídia atualizada!");
                    JOptionPane.showMessageDialog(this, 
                        "Localização da mídia atualizada (arquivo não encontrado fisicamente).", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao mover mídia: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void exibirDetalhes() {
        int linhaSelecionada = tabelaMidias.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        List<Midia> midiasAtuais = obterMidiasExibidas();
        Midia midia = midiasAtuais.get(linhaSelecionada);
        
        String detalhes = midia.exibirDetalhes();
        
        JTextArea textArea = new JTextArea(detalhes);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "Detalhes da Mídia", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void ordenarPorColuna(int coluna) {
        if (colunaOrdenada == coluna) {
            ordemCrescente = !ordemCrescente;
        } else {
            colunaOrdenada = coluna;
            ordemCrescente = true;
        }
        
        List<Midia> midias = obterMidiasExibidas();
        
        Comparator<Midia> comparador = null;
        String nomeCampo = "";
        
        switch (coluna) {
            case 0:
                nomeCampo = "Tipo";
                comparador = (m1, m2) -> {
                    String tipo1 = m1 instanceof Filme ? "Filme" : m1 instanceof Musica ? "Música" : "Livro";
                    String tipo2 = m2 instanceof Filme ? "Filme" : m2 instanceof Musica ? "Música" : "Livro";
                    return tipo1.compareTo(tipo2);
                };
                break;
            case 1:
                nomeCampo = "Título";
                comparador = Comparator.comparing(m -> m.getTitulo().toLowerCase());
                break;
            case 2:
                nomeCampo = "Duração";
                comparador = Comparator.comparingInt(Midia::getDuracao);
                break;
            case 3:
                nomeCampo = "Categoria";
                comparador = Comparator.comparing(m -> m.getCategoria().toLowerCase());
                break;
            case 4:
                nomeCampo = "Formato";
                comparador = Comparator.comparing(m -> m.getFormato().toLowerCase());
                break;
            case 5:
                nomeCampo = "Tamanho";
                comparador = Comparator.comparingDouble(Midia::getTamanhoEmDisco);
                break;
        }
        
        if (comparador != null) {
            if (!ordemCrescente) {
                comparador = comparador.reversed();
            }
            midias.sort(comparador);
            atualizarTabela(midias);
            atualizarCabecalhosOrdenacao();
            
            String ordem = ordemCrescente ? "crescente" : "decrescente";
            lblStatus.setText("Ordenado por " + nomeCampo + " (" + ordem + ")");
        }
    }
    
    private void atualizarCabecalhosOrdenacao() {
        for (int i = 0; i < nomesColunas.length; i++) {
            modeloTabela.setColumnIdentifiers(nomesColunas);
        }
        
        if (colunaOrdenada >= 0 && colunaOrdenada < nomesColunas.length) {
            String[] colunasComSeta = nomesColunas.clone();
            String seta = ordemCrescente ? " ▲" : " ▼";
            colunasComSeta[colunaOrdenada] = nomesColunas[colunaOrdenada] + seta;
            modeloTabela.setColumnIdentifiers(colunasComSeta);
        }
    }
    
    private List<Midia> obterMidiasExibidas() {
        String tipoSelecionado = (String) comboFiltroTipo.getSelectedItem();
        String categoria = txtFiltroCategoria.getText().trim();
        
        if (!"Todos".equals(tipoSelecionado) || !categoria.isEmpty()) {
            String tipo = "Todos".equals(tipoSelecionado) ? null : tipoSelecionado;
            return gerenciador.filtrarMidias(tipo, categoria.isEmpty() ? null : categoria);
        }
        
        return gerenciador.getMidias();
    }
}
