package iu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import feed.FeedNoticias;
import feed.Publicacao;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.image.BufferedImage;

/**
 * Classe criada para implementar a interface gráfica da Rede Social.
 * O objetivo dessa implementação é didático!
 * - Exercitar e apresentar conceitos de GUIs (Interfaces Gráficas de Usuário)
 * e Tratamento de Exceções
 * 
 * @author Julio Cesar Alves
 */
public class TelaRedeSocial {
    // Janela da nossa tela
    private JFrame janela;
    // Caixa de texto para exibir o feed de noticiai
    private JTextArea areaTextoFeed;
    // Botão para postar uma mensagem no feed
    private JButton botaoPostarMensagem;
    // Botão para curtir uma mensagem do feed
    private JButton botaoCurtir;
    // Botão para comentar uma mensagem do feed
    private JButton botaoComentar;
    // Botão para visualizar uma mensagem do feed
    private JButton botaoVisualizar;

    // Botão para comentar uma mensagem do feed
    private JButton botaoPostarFoto;

    // Botão para atualizar o feed
    private JButton botaoAtualizarFeed;

    // Objeto que representa a Regra de Negócios (a lógica da Rede Social em si)
    private FeedNoticias feed;

    /**
     * Construtor da classe: cria o feed, os componentes e monta a tela.
     */
    public TelaRedeSocial() {
        feed = new FeedNoticias();

        construirJanela();
    }

    /**
     * Constroi os componentes e monta a janela
     */
    private void construirJanela() throws HeadlessException {
        janela = new JFrame("GUI - Rede Social");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        criarComponentes();

        montarJanela();
    }

    /**
     * Cria os componentes da tela e faz a inscrição nos eventos necessários
     */
    private void criarComponentes() {
        // criando os componentes

        areaTextoFeed = new JTextArea();
        botaoPostarMensagem = new JButton("Postar Texto");
        botaoPostarFoto = new JButton("Postar Foto");
        botaoVisualizar = new JButton("Visualizar");
        botaoCurtir = new JButton("Curtir");
        botaoComentar = new JButton("Comentar");
        botaoAtualizarFeed = new JButton("Atualizar feed");

        // impede que o usuário edite a área de texto do feed
        areaTextoFeed.setEditable(false);

        // adiciona o método que tratará o evento de clique no botão Postar Mensagem de
        // Texto
        botaoPostarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postarMensagemTexto();
            }
        });

        // adiciona o método que tratará o evento de clique no botão Postar Mensagem com
        // Foto
        botaoPostarFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postarMensagemFoto();
            }
        });

        // adiciona o método que tratará o evento de clique no botão Visualizar
        botaoVisualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizarMensagem();
            }
        });

        // adiciona o método que tratará o evento de clique no botão Curtir
        botaoCurtir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curtirMensagem();
            }
        });

        // adiciona o método que tratará o evento de clique no botão Comentar
        botaoComentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comentar();
            }
        });

        // adiciona o método que tratará o evento de clique no botão Atualizar feed
        botaoAtualizarFeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarAreaTextoFeed();
            }
        });
    }

    /**
     * Monta a janela
     */
    private void montarJanela() {
        janela.setSize(600, 600);

        janela.setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new FlowLayout());
        painelSuperior.add(new JLabel("Feed de Notícias"));
        janela.add(painelSuperior, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.add(areaTextoFeed);
        JScrollPane scrollPane = new JScrollPane(painelCentral);
        janela.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridBagLayout());
        painelBotoes.add(botaoPostarMensagem);
        painelBotoes.add(botaoPostarFoto);
        painelBotoes.add(botaoVisualizar);
        painelBotoes.add(botaoCurtir);
        painelBotoes.add(botaoComentar);
        painelBotoes.add(botaoAtualizarFeed);
        janela.add(painelBotoes, BorderLayout.SOUTH);
    }

    /*
     * Exibe a tela da Rede Social
     */
    public void exibir() {
        janela.setVisible(true);
    }

    /**
     * Posta uma mensagem de texto no feed. Solicita o autor e a mensagem ao
     * usuário,
     * posta no Feed e atualiza a área de texto de exibição do feed.
     */
    private void postarMensagemTexto() {
        String autor = JOptionPane.showInputDialog("Autor da mensagem");
        // Se o usuário digitou algum autor e confirmou
        if (autor != null) {
            String mensagem = JOptionPane.showInputDialog("Texto da mensagem");
            // Se o usuário digitou alguma mensagem e confirmou
            if (mensagem != null) {
                feed.postarMensagemTexto(autor, mensagem);
                atualizarAreaTextoFeed();
            }
        }
    }

    /**
     * Posta uma mensagem com foto no feed. Solicita o autor, o arquivo da imagem e
     * uma legenda,
     * posta no Feed e atualiza a área de texto de exibição do feed.
     */
    private void postarMensagemFoto() {
        String autor = JOptionPane.showInputDialog("Autor da mensagem");
        if (autor != null) {
            // Cria um objeto da tela de escolha de arquivos
            JFileChooser selecionadorArquivo = new JFileChooser();
            // Cria um fitlro para que o usuário selecione apenas imagens com extensões jpg,
            // jpeg e png
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Imagens", "jpg", "jpeg", "png");
            // Utiliza o filtro para a tela de seleção de arquivos
            selecionadorArquivo.setFileFilter(filter);

            // Exibe a tela e verifica se o usuário selecionou um arquivo
            int resultado = selecionadorArquivo.showOpenDialog(janela);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                // Obtém o arquivo selecionado
                File arquivoFoto = selecionadorArquivo.getSelectedFile();
                // Pede uma legenda para o usuário
                String legenda = JOptionPane.showInputDialog("Legenda da foto");
                // Se foi digitada uma legenda, obtém a imagem como vetor de bytes e posta a
                // mensagem com foto
                if (legenda != null) {
                    try {
                        byte[] bytesDaFoto = Files.readAllBytes(arquivoFoto.toPath());
                        feed.postarMensagemFoto(autor, bytesDaFoto, legenda);
                        atualizarAreaTextoFeed();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(janela, "Erro ao carregar a foto.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * Curte uma mensagem. Solicita o identificador da mensagem ao usuário,
     * curte a mensagem e atualiza a área de texto de exibição do feed.
     */
    private void curtirMensagem() {
        int idMensagem = Integer.parseInt(JOptionPane.showInputDialog("Id da mensagem"));
        feed.curtir(idMensagem);
        atualizarAreaTextoFeed();
    }

    /**
     * Visualiza o conteúdo de uma mensagem (mostra a imagem de uma mensagem com
     * foto, por exemplo)
     */
    private void visualizarMensagem() {
        int idMensagem = Integer.parseInt(JOptionPane.showInputDialog("Id da mensagem"));

        // obtém os dados binários associados à mensagem
        byte[] bytesDaFoto = feed.getDadosBinarios(idMensagem);

        if (bytesDaFoto != null) {
            try {
                // Crie uma janela para exibir a imagem
                JFrame frameFoto = new JFrame("Foto - ");
                // Converte os bytes da foto para um BufferedImage
                BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(bytesDaFoto));
                ImageIcon imagemIcon = new ImageIcon(imagem);

                // Exibe a foto em um rítulo
                JLabel labelFoto = new JLabel(imagemIcon);
                frameFoto.getContentPane().add(labelFoto);

                // Ajusta o tamanho da janela conforme a foto
                frameFoto.setSize(imagemIcon.getIconWidth(), imagemIcon.getIconHeight());

                // Exibe a janela
                frameFoto.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao exibir a foto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "A mensagem não tem foto.", "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Comenta em uma mensagem. Solicita o identificador da mensagem ao usuário,
     * faz um comentário na mensagem e atualiza a área de texto de exibição do feed.
     */
    private void comentar() {
        int idMensagem = Integer.parseInt(JOptionPane.showInputDialog("Id da mensagem"));
        String comentario = JOptionPane.showInputDialog("Comentário");
        feed.comentar(idMensagem, comentario);
        atualizarAreaTextoFeed();
    }

    /**
     * Atualiza a área de texto de exibição do Feed.
     */
    private void atualizarAreaTextoFeed() {
        areaTextoFeed.setText("");

        List<Publicacao> publicacoes = feed.getPublicacoes();

        for (Publicacao publicacao : publicacoes) {
            areaTextoFeed.append(publicacao.getTextoExibicao());
        }
    }

}
