package gerenciador_midia.view;

import gerenciador_midia.enums.FilmeFormatoSuportado;
import gerenciador_midia.enums.LivroFormatoSuportado;
import gerenciador_midia.enums.MusicaFormatoSuportado;
import gerenciador_midia.model.Filme;
import gerenciador_midia.model.Livro;
import gerenciador_midia.model.Midia;
import gerenciador_midia.model.Musica;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class DialogoMidia extends JDialog {
    
    private JLabel lblTipoDetectado;
    private JTextField txtLocal;
    private JButton btnProcurar;
    private JTextField txtTitulo;
    private JTextField txtDuracao;
    private JTextField txtCategoria;
    private JLabel lblCampoEspecifico;
    private JTextField txtCampoEspecifico;
    private JLabel lblDuracaoUnidade;
    
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    private Midia midiaResultado;
    private boolean confirmado = false;
    private String tipoMidiaDetectado = null;
    
    public DialogoMidia(Frame parent) {
        this(parent, null);
    }
    
    public DialogoMidia(Frame parent, Midia midiaExistente) {
        super(parent, midiaExistente == null ? "Adicionar Mídia" : "Editar Mídia", true);
        
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        
        if (midiaExistente != null) {
            preencherDados(midiaExistente);
        }
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        lblTipoDetectado = new JLabel("Nenhum arquivo selecionado");
        lblTipoDetectado.setFont(lblTipoDetectado.getFont().deriveFont(Font.BOLD));
        txtLocal = new JTextField(30);
        txtLocal.setEditable(false);
        btnProcurar = new JButton("Selecionar Arquivo...");
        txtTitulo = new JTextField(30);
        txtDuracao = new JTextField(10);
        txtCategoria = new JTextField(20);
        lblCampoEspecifico = new JLabel("Campo Específico:");
        txtCampoEspecifico = new JTextField(20);
        lblDuracaoUnidade = new JLabel("");
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setEnabled(false);
        btnCancelar = new JButton("Cancelar");
    }
    
    private void configurarLayout() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        painelPrincipal.add(new JLabel("Tipo de Mídia:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelPrincipal.add(lblTipoDetectado, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        painelPrincipal.add(new JLabel("Local do Arquivo:"), gbc);
        
        gbc.gridx = 1;
        painelPrincipal.add(txtLocal, gbc);
        
        gbc.gridx = 2;
        painelPrincipal.add(btnProcurar, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        painelPrincipal.add(new JLabel("Título:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelPrincipal.add(txtTitulo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        painelPrincipal.add(new JLabel("Duração:"), gbc);
        
        JPanel painelDuracao = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        painelDuracao.add(txtDuracao);
        painelDuracao.add(lblDuracaoUnidade);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelPrincipal.add(painelDuracao, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        painelPrincipal.add(new JLabel("Categoria:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelPrincipal.add(txtCategoria, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        painelPrincipal.add(lblCampoEspecifico, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelPrincipal.add(txtCampoEspecifico, gbc);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal);
    }
    
    private void configurarEventos() {
        btnProcurar.addActionListener(e -> procurarArquivo());
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> cancelar());
    }
    
    private void atualizarCamposPorTipo() {
        if (tipoMidiaDetectado == null) {
            lblCampoEspecifico.setText("Campo Específico:");
            lblDuracaoUnidade.setText("");
            btnSalvar.setEnabled(false);
            return;
        }
        
        switch (tipoMidiaDetectado) {
            case "Filme":
                lblCampoEspecifico.setText("Idioma:");
                lblDuracaoUnidade.setText("minutos");
                lblTipoDetectado.setText("Filme (MP4/MKV)");
                lblTipoDetectado.setForeground(new Color(0, 100, 0));
                break;
            case "Música":
                lblCampoEspecifico.setText("Artista:");
                lblDuracaoUnidade.setText("segundos");
                lblTipoDetectado.setText("Música (MP3)");
                lblTipoDetectado.setForeground(new Color(0, 100, 0));
                break;
            case "Livro":
                lblCampoEspecifico.setText("Autores:");
                lblDuracaoUnidade.setText("páginas");
                lblTipoDetectado.setText("Livro (PDF/EPUB)");
                lblTipoDetectado.setForeground(new Color(0, 100, 0));
                break;
        }
        btnSalvar.setEnabled(true);
    }
    
    private void procurarArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        
        FileNameExtensionFilter filtroTodos = new FileNameExtensionFilter(
            "Todos os arquivos suportados (*.mp4, *.mkv, *.mp3, *.pdf, *.epub)", 
            "mp4", "mkv", "mp3", "pdf", "epub");
        fileChooser.setFileFilter(filtroTodos);
        
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
            "Arquivos de Vídeo (*.mp4, *.mkv)", "mp4", "mkv"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
            "Arquivos de Áudio (*.mp3)", "mp3"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
            "Arquivos de Livro (*.pdf, *.epub)", "pdf", "epub"));
        
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            txtLocal.setText(arquivoSelecionado.getAbsolutePath());
            
            String nomeArquivo = arquivoSelecionado.getName();
            String extensao = "";
            int pontoIndex = nomeArquivo.lastIndexOf('.');
            if (pontoIndex > 0 && pontoIndex < nomeArquivo.length() - 1) {
                extensao = nomeArquivo.substring(pontoIndex + 1).toLowerCase();
            }
            
            tipoMidiaDetectado = null;
            switch (extensao) {
                case "mp4":
                case "mkv":
                    tipoMidiaDetectado = "Filme";
                    break;
                case "mp3":
                    tipoMidiaDetectado = "Música";
                    break;
                case "pdf":
                case "epub":
                    tipoMidiaDetectado = "Livro";
                    break;
                default:
                    lblTipoDetectado.setText("Formato não suportado: " + extensao.toUpperCase());
                    lblTipoDetectado.setForeground(Color.RED);
                    btnSalvar.setEnabled(false);
                    JOptionPane.showMessageDialog(this,
                        "Formato de arquivo não suportado!\n\n" +
                        "Formatos aceitos:\n" +
                        "• Filmes: MP4, MKV\n" +
                        "• Músicas: MP3\n" +
                        "• Livros: PDF, EPUB",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            atualizarCamposPorTipo();
            
            if (txtTitulo.getText().trim().isEmpty()) {
                if (pontoIndex > 0) {
                    txtTitulo.setText(nomeArquivo.substring(0, pontoIndex));
                } else {
                    txtTitulo.setText(nomeArquivo);
                }
            }
            
            detectarDuracaoAutomatica(arquivoSelecionado, extensao);
        }
    }
    
    private void detectarDuracaoAutomatica(File arquivo, String extensao) {
        int duracaoDetectada = 0;
        
        try {
            switch (extensao.toLowerCase()) {
                case "mp3":
                    duracaoDetectada = gerenciador_midia.util.MetadadosExtractor.extrairDuracaoMP3(arquivo);
                    break;
                case "mp4":
                    duracaoDetectada = gerenciador_midia.util.MetadadosExtractor.extrairDuracaoMP4(arquivo);
                    break;
                case "mkv":
                    duracaoDetectada = gerenciador_midia.util.MetadadosExtractor.extrairDuracaoMKV(arquivo);
                    break;
                case "pdf":
                    duracaoDetectada = gerenciador_midia.util.MetadadosExtractor.extrairPaginasPDF(arquivo);
                    break;
                case "epub":
                    duracaoDetectada = gerenciador_midia.util.MetadadosExtractor.extrairPaginasEPUB(arquivo);
                    break;
            }
            
            if (duracaoDetectada > 0) {
                txtDuracao.setText(String.valueOf(duracaoDetectada));
            }
        } catch (Exception e) {
            System.err.println("Erro ao detectar duração: " + e.getMessage());
        }
    }
    
    private void salvar() {
        try {
            String local = txtLocal.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String campoEspecifico = txtCampoEspecifico.getText().trim();
            
            if (local.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O campo 'Local do Arquivo' é obrigatório!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                txtLocal.requestFocus();
                return;
            }
            
            File arquivo = new File(local);
            if (!arquivo.exists()) {
                int resposta = JOptionPane.showConfirmDialog(this,
                    "O arquivo não existe no caminho especificado.\nDeseja continuar mesmo assim?",
                    "Arquivo não encontrado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if (resposta != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O campo 'Título' é obrigatório!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                txtTitulo.requestFocus();
                return;
            }
            
            if (categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O campo 'Categoria' é obrigatório!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                txtCategoria.requestFocus();
                return;
            }
            
            if (campoEspecifico.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O campo '" + lblCampoEspecifico.getText().replace(":", "") + "' é obrigatório!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                txtCampoEspecifico.requestFocus();
                return;
            }
            
            int duracao;
            try {
                duracao = Integer.parseInt(txtDuracao.getText().trim());
                if (duracao < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "A duração deve ser um número inteiro não negativo!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                txtDuracao.requestFocus();
                return;
            }
            
            if (tipoMidiaDetectado == null) {
                JOptionPane.showMessageDialog(this,
                    "Nenhum arquivo válido foi selecionado!\nPor favor, clique em 'Selecionar Arquivo...'",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validarFormatoArquivo(local)) {
                return;
            }
            
            switch (tipoMidiaDetectado) {
                case "Filme":
                    midiaResultado = new Filme(local, titulo, duracao, categoria, campoEspecifico);
                    break;
                case "Música":
                    midiaResultado = new Musica(local, titulo, duracao, categoria, campoEspecifico);
                    break;
                case "Livro":
                    midiaResultado = new Livro(local, titulo, duracao, categoria, campoEspecifico);
                    break;
            }
            
            confirmado = true;
            dispose();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao criar mídia: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro inesperado: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean validarFormatoArquivo(String local) {
        if (tipoMidiaDetectado == null) {
            return false;
        }
        
        int pontoIndex = local.lastIndexOf('.');
        if (pontoIndex == -1 || pontoIndex == local.length() - 1) {
            JOptionPane.showMessageDialog(this, 
                "O arquivo deve ter uma extensão válida!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String extensao = local.substring(pontoIndex + 1).toUpperCase();
        
        boolean formatoValido = false;
        String formatosAceitos = "";
        
        switch (tipoMidiaDetectado) {
            case "Filme":
                try {
                    FilmeFormatoSuportado.valueOf(extensao);
                    formatoValido = true;
                } catch (IllegalArgumentException e) {
                    formatosAceitos = "MP4, MKV";
                }
                break;
            case "Música":
                try {
                    MusicaFormatoSuportado.valueOf(extensao);
                    formatoValido = true;
                } catch (IllegalArgumentException e) {
                    formatosAceitos = "MP3";
                }
                break;
            case "Livro":
                try {
                    LivroFormatoSuportado.valueOf(extensao);
                    formatoValido = true;
                } catch (IllegalArgumentException e) {
                    formatosAceitos = "PDF, EPUB";
                }
                break;
        }
        
        if (!formatoValido) {
            JOptionPane.showMessageDialog(this, 
                "Formato de arquivo inválido!\nFormatos aceitos para " + 
                tipoMidiaDetectado + ": " + formatosAceitos, 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void cancelar() {
        confirmado = false;
        dispose();
    }
    
    private void preencherDados(Midia midia) {
        txtLocal.setText(midia.getLocal());
        txtTitulo.setText(midia.getTitulo());
        txtDuracao.setText(String.valueOf(midia.getDuracao()));
        txtCategoria.setText(midia.getCategoria());
        
        if (midia instanceof Filme) {
            tipoMidiaDetectado = "Filme";
            txtCampoEspecifico.setText(((Filme) midia).getIdioma());
        } else if (midia instanceof Musica) {
            tipoMidiaDetectado = "Música";
            txtCampoEspecifico.setText(((Musica) midia).getArtista());
        } else if (midia instanceof Livro) {
            tipoMidiaDetectado = "Livro";
            txtCampoEspecifico.setText(((Livro) midia).getAutores());
        }
        
        atualizarCamposPorTipo();
        
        btnProcurar.setEnabled(false);
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public Midia getMidia() {
        return midiaResultado;
    }
}
