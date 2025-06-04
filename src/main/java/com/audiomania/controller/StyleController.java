package com.audiomania.controller;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class StyleController {

    //Cria um Botao Personalizado
    /**
     * 
     * param botao
     * param cor
     * param textColor
     * param tamanhoFonte
     * param tamanhoBotaoX
     * param tamanhoBotaoY
     */
    public void estilizarBotao(JButton botao, Color cor, Color textColor, int tamanhoFonte, int tamanhoBotaoX, int tamanhoBotaoY) {
        botao.setFocusPainted(false);
        botao.setBackground(cor);
        botao.setForeground(textColor);
        botao.setFont(new Font("Montserrat", Font.BOLD, tamanhoFonte));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(tamanhoBotaoX, tamanhoBotaoY));
    }

    //Cria um Botao Grande 200x50
    /**
     * 
     * param botao
     * param cor
     * param textColor
     * param tamanhoFonte
     */
    public void estilizarBotaoGrande(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 200, 50);
    }

    //Cria um Botao Medio 150x40
    /**
     * 
     * param botao
     * param cor
     * param textColor
     * param tamanhoFonte
     */
    public void estilizarBotaoMedio(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 150, 40);
    }

    //Cria um Botao Pequeno 100x30
    /**
     * 
     * param botao
     * param cor
     * param textColor
     * param tamanhoFonte
     */
    public void estilizarBotaoPequeno(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 100, 30);
    }

    //Cria um Botao Mini 48x48
    /**
     * 
     * param botao
     * param cor
     * param textColor
     * param tamanhoFonte
     */
    public void estilizarBotaoMini(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 48, 48);
    }

    //Cria um Titulo
    /**
     * 
     * param titulo
     * param cor
     * param tamanhoFonte
     */
    public void estilizarTitulo(JLabel titulo, Color cor, int tamanhoFonte) {
        titulo.setFont(new Font("Montserrat", Font.BOLD, tamanhoFonte));
        titulo.setForeground(cor);
    }

    //Cria um Panel
    /**
     * 
     * param panel
     * param cor
     */
    public void estilizarPanel(JPanel panel, Color cor) {
        panel.setBackground(cor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
    }

    //Cria uma Janela
    /**
     * 
     * param janela
     * param Fundo
     */
    public void estilizarJanela(JFrame janela, Color Fundo) {
        if (Fundo == null) {
            Fundo = Color.DARK_GRAY;
        }
        janela.setLayout(new BorderLayout(10, 10));
        janela.getContentPane().setBackground(Fundo);
    }

    //Cria uma Tabela
    /**
     * 
     * param tabela
     * param cor
     * param tamanhoFonte
     * param tamanhoFonteHeader
     * param espacamentoLateral
     * param espacamentoVertical
     */
    public void estilizarTabela(JTable tabela, Color cor, int tamanhoFonte, int tamanhoFonteHeader, int espacamentoLateral, int espacamentoVertical) {
        if (cor == null) {
            cor = Color.DARK_GRAY;
        }
        if (tamanhoFonte == 0) {
            tamanhoFonte = 12;
        }
        if (tamanhoFonteHeader == 0) {
            tamanhoFonteHeader = 14;
        }
        if (espacamentoLateral == 0) {
            espacamentoLateral = 10;
        }
        if (espacamentoVertical == 0) {
            espacamentoVertical = 10;
        }

        tabela.setBackground(cor);
        tabela.setForeground(Color.WHITE);
        tabela.setFont(new Font("Montserrat", Font.PLAIN, tamanhoFonte));
        tabela.getTableHeader().setFont(new Font("Montserrat", Font.BOLD, tamanhoFonteHeader));
        tabela.getTableHeader().setBorder(BorderFactory.createEmptyBorder(espacamentoVertical, espacamentoLateral, espacamentoVertical, espacamentoLateral));
    }

    //Cria um TextField
    /**
     * 
     * param campo
     * param cor
     * param tamanhoFonte
     */
    public void estilizarTextField(JTextField campo, Color cor, int tamanhoFonte) {
        if (cor == null) {
            cor = Color.DARK_GRAY;
        }
        if (tamanhoFonte == 0) {
            tamanhoFonte = 12;
        }

        campo.setForeground(cor);
        campo.setFont(new Font("Montserrat", Font.PLAIN, tamanhoFonte));
    }

    //Cria um TextArea
    /**
     * 
     * param area
     * param background
     * param foreground
     * param fontSize
     */
    public void estilizarTextArea(JTextArea area, Color background, Color foreground, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        area.setBackground(background);
        area.setForeground(foreground);
        area.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria um PasswordField que esconde a senha
    /**
     * 
     * param field
     * param background
     * param fontSize
     */
    public void estilizarPasswordField(JPasswordField field, Color background, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        field.setBackground(background);
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria um ComboBox com varias opcoes
    /**
     * 
     * param combo
     * param background
     * param foreground
     * param fontSize
     */
    public void estilizarComboBox(JComboBox<?> combo, Color background, Color foreground, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        combo.setBackground(background);
        combo.setForeground(foreground);
        combo.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria um CheckBox
    /**
     * 
     * param checkBox
     * param background
     * param foreground
     * param fontSize
     */
    public void estilizarCheckBox(JCheckBox checkBox, Color background, Color foreground, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        checkBox.setBackground(background);
        checkBox.setForeground(foreground);
        checkBox.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria um RadioButton
    /**
     * 
     * param radioButton
     * param background
     * param foreground
     * param fontSize
     */
    public void estilizarRadioButton(JRadioButton radioButton, Color background, Color foreground, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        radioButton.setBackground(background);
        radioButton.setForeground(foreground);
        radioButton.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria um Slider
    /**
     * 
     * param slider
     * param trackColor
     * param thumbColor
     */
    public void estilizarSlider(JSlider slider, Color trackColor, Color thumbColor) {
        if (trackColor == null) {
            trackColor = Color.DARK_GRAY;
        }
        if (thumbColor == null) {
            thumbColor = Color.WHITE;
        }

        slider.setBackground(trackColor);
        slider.setForeground(thumbColor);
    }

    //Cria uma ProgressBar
    /**
     * 
     * param progressBar
     * param background
     * param foreground
     */
    public void estilizarProgressBar(JProgressBar progressBar, Color background, Color foreground) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }

        progressBar.setBackground(background);
        progressBar.setForeground(foreground);
    }

    //Cria uma MenuBar
    /**
     * 
     * param menuBar
     * param background
     */
    public void estilizarMenuBar(JMenuBar menuBar, Color background) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }

        menuBar.setBackground(background);
    }

    //Cria um Menu 
    /**
     * 
     * param menu
     * param foreground
     * param fontSize
     */
    public void estilizarMenu(JMenu menu, Color foreground, int fontSize) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        menu.setForeground(foreground);
        menu.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria um MenuItem
    /**
     * 
     * param menuItem
     * param foreground
     * param fontSize
     */
    public void estilizarMenuItem(JMenuItem menuItem, Color foreground, int fontSize) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        menuItem.setForeground(foreground);
        menuItem.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria uma TabbedPane
    /**
     * 
     * param tabbedPane
     * param background
     * param foreground
     * param fontSize
     */
    public void estilizarTabbedPane(JTabbedPane tabbedPane, Color background, Color foreground, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        tabbedPane.setBackground(background);
        tabbedPane.setForeground(foreground);
        tabbedPane.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria uma ScrollPane
    /**
     * 
     * param scrollPane
     * param border
     */
    public void estilizarScrollPane(JScrollPane scrollPane, Border border) {
        scrollPane.setBorder(border);
    }

    //Cria um ToolTip
    /**
     * 
     * param toolTip
     * param background
     * param foreground
     * param fontSize
     */
    public void estilizarToolTip(JToolTip toolTip, Color background, Color foreground, int fontSize) {
        if (background == null) {
            background = Color.DARK_GRAY;
        }
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        if (fontSize == 0) {
            fontSize = 12;
        }

        toolTip.setBackground(background);
        toolTip.setForeground(foreground);
        toolTip.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
    }

    //Cria uma Borda
    /**
     * 
     * param component
     * param border
     */
    public void estilizarBorda(JComponent component, Border border) {
        if (border == null) {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY);
        }

        component.setBorder(border);
    }
}