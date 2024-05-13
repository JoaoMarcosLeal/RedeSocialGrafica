package iu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import java.io.IOException;

import java.awt.Image;

import javax.swing.*;
import java.awt.image.BufferedImage;


/**
 * Classe criada para implementar a interface gráfica da Rede Social.
 * O objetivo dessa implementação é didático! 
 * - Exercitar e apresentar conceitos de GUIs (Interfaces Gráficas de Usuário) 
 *   e Tratamento de Exceções
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
    private JButton botaoVisu;

    // Botão para comentar uma mensagem do feed
    private JButton botaoPostarFoto;
    
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
        botaoPostarMensagem = new JButton("Postar Mensagem");
        botaoCurtir = new JButton("Curtir");
        botaoVisu = new JButton("Visualizar");
        botaoComentar = new JButton("Comentar");
        botaoPostarFoto = new JButton("Postar Foto");
        
        // impede que o usuário edite a área de texto do feed
        areaTextoFeed.setEditable(false);
        
        // adiciona o método que tratará o evento de clique no botão Postar Mensagem
        botaoPostarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postarMensagem();
            }            
        });

        
        botaoPostarFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postarFoto();
            }
        });
        
        // adiciona o método que tratará o evento de clique no botão Curtir
        botaoCurtir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curtirMensagem();
            }
        });

        // adiciona o método que tratará o evento de clique no botão Curtir
        botaoVisu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizarMensagem();
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
        janela.add(painelCentral, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.add(botaoPostarMensagem);
        painelBotoes.add(botaoCurtir);
        painelBotoes.add(botaoVisu);
        painelBotoes.add(botaoComentar);
        painelBotoes.add(botaoPostarFoto);
        janela.add(painelBotoes, BorderLayout.SOUTH);
    }
    
    /*
     * Exibe a tela da Rede Social
    */
    public void exibir() {
        janela.setVisible(true);
    }
    
    /**
     * Posta uma mensagem no feed. Solicita o autor e a mensagem ao usuário,
     * posta no Feed e atualiza a área de texto de exibição do feed.
     */
    private void postarMensagem() {
        String autor = JOptionPane.showInputDialog("Autor da mensagem");
        // Se o usuário digitou algum autor e confirmou
        if(autor != null) {
            String mensagem = JOptionPane.showInputDialog("Texto da mensagem");
            // Se o usuário digitou alguma mensagem e confirmou
            if (mensagem != null) {
                feed.postarMensagemTexto(autor, mensagem);        
                atualizarAreaTextoFeed();
            }
        }
    }

    private void postarFoto() {
        String autor = JOptionPane.showInputDialog("Autor da mensagem");
        if (autor != null) {
            JFileChooser fileChooser = new JFileChooser();
            int resultado = fileChooser.showOpenDialog(janela);
    
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File arquivoFoto = fileChooser.getSelectedFile();
                String legenda = JOptionPane.showInputDialog("Legenda da foto");
                if (legenda != null) {
                    try {
                        byte[] bytesDaFoto = Files.readAllBytes(arquivoFoto.toPath());
                        feed.postarMensagemFoto(autor, bytesDaFoto, legenda);
                        atualizarAreaTextoFeed();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(janela, "Erro ao carregar a foto.", "Erro", JOptionPane.ERROR_MESSAGE);
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

    private void visualizarMensagem() {
        int idMensagem = Integer.parseInt(JOptionPane.showInputDialog("Id da mensagem"));
  
        //atualizarAreaTextoFeed();
        // Verifica se bytesDaFoto é null
        // Se bytesDaFoto não for null, continua com o código para exibir a foto
        byte[] bytesDaFoto = feed.getBytesDaFoto(idMensagem);
        JFrame frameFoto = new JFrame("Foto - ");
        if (bytesDaFoto != null) {
            try {
                
                // Converte os bytes da foto para um BufferedImage
                BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(bytesDaFoto));
                ImageIcon imagemIcon = new ImageIcon(imagem);

                // Exibe a foto em um JLabel
                JLabel labelFoto = new JLabel(imagemIcon);
                frameFoto.getContentPane().add(labelFoto);

                // Ajusta o tamanho do JFrame conforme a foto
                frameFoto.setSize(imagemIcon.getIconWidth(), imagemIcon.getIconHeight());

                frameFoto.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao exibir a foto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }    
        } else {
            JOptionPane.showMessageDialog(null, "A mensagem não tem foto.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
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
