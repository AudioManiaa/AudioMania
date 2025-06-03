package com.audiomania.controller;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class StyleController {

    //Cria um Botao Personalizado
    /**
     * 
     * @param botao
     * @param cor
     * @param textColor
     * @param tamanhoFonte
     * @param tamanhoBotaoX
     * @param tamanhoBotaoY
     */
    public void estilizarBotao(JButton botao, Color cor, Color textColor, int tamanhoFonte, int tamanhoBotaoX, int tamanhoBotaoY) {
        botao.setFocusPainted(false);
        botao.setBackground(cor);
        botao.setForeground(textColor);
        botao.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(tamanhoBotaoX, tamanhoBotaoY));
    }

    //Cria um Botao Grande 200x50
    /**
     * 
     * @param botao
     * @param cor
     * @param textColor
     * @param tamanhoFonte
     */
    public void estilizarBotaoGrande(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 200, 50);
    }

    //Cria um Botao Medio 150x40
    /**
     * 
     * @param botao
     * @param cor
     * @param textColor
     * @param tamanhoFonte
     */
    public void estilizarBotaoMedio(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 150, 40);
    }

    //Cria um Botao Pequeno 100x30
    /**
     * 
     * @param botao
     * @param cor
     * @param textColor
     * @param tamanhoFonte
     */
    public void estilizarBotaoPequeno(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 100, 30);
    }

    //Cria um Botao Mini 48x48
    /**
     * 
     * @param botao
     * @param cor
     * @param textColor
     * @param tamanhoFonte
     */
    public void estilizarBotaoMini(JButton botao, Color cor, Color textColor, int tamanhoFonte) {
        estilizarBotao(botao, cor, textColor, tamanhoFonte, 48, 48);
    }

    //Cria um Titulo
    /**
     * 
     * @param titulo
     * @param cor
     * @param tamanhoFonte
     */
    public void estilizarTitulo(JLabel titulo, Color cor, int tamanhoFonte) {
        titulo.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        titulo.setForeground(cor);
    }

    //Cria um Panel
    /**
     * 
     * @param panel
     * @param cor
     */
    public void estilizarPanel(JPanel panel, Color cor) {
        panel.setBackground(cor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
    }

    //Cria uma Janela
    /**
     * 
     * @param janela
     * @param Fundo
     */
    public void estilizarJanela(JFrame janela, Color Fundo) {
        janela.setLayout(new BorderLayout(10, 10));
        janela.getContentPane().setBackground(Fundo);
    }

    //Cria uma Tabela
    /**
     * 
     * @param tabela
     * @param cor
     * @param tamanhoFonte
     * @param tamanhoFonteHeader
     * @param espacamentoLateral
     * @param espacamentoVertical
     */
    public void estilizarTabela(JTable tabela, Color cor, int tamanhoFonte, int tamanhoFonteHeader, int espacamentoLateral, int espacamentoVertical) {
        tabela.setBackground(cor);
        tabela.setForeground(Color.WHITE);
        tabela.setFont(new Font("Arial", Font.PLAIN, tamanhoFonte));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, tamanhoFonteHeader));
        tabela.getTableHeader().setBorder(BorderFactory.createEmptyBorder(espacamentoVertical, espacamentoLateral, espacamentoVertical, espacamentoLateral));
    }

    //Cria um TextField
    /**
     * 
     * @param campo
     * @param cor
     * @param tamanhoFonte
     */
    public void estilizarTextField(JTextField campo, Color cor, int tamanhoFonte) {
        campo.setForeground(cor);
        campo.setFont(new Font("Arial", Font.PLAIN, tamanhoFonte));
    }

    //Cria um TextArea
    /**
     * 
     * @param area
     * @param background
     * @param foreground
     * @param fontSize
     */
    public void estilizarTextArea(JTextArea area, Color background, Color foreground, int fontSize) {
        area.setBackground(background);
        area.setForeground(foreground);
        area.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //Cria um PasswordField que esconde a senha
    /**
     * 
     * @param field
     * @param background
     * @param fontSize
     */
    public void estilizarPasswordField(JPasswordField field, Color background, int fontSize) {
        field.setBackground(background);
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //combo box
    /**
     * 
     * @param combo
     * @param background
     * @param foreground
     * @param fontSize
     */
    public void estilizarComboBox(JComboBox<?> combo, Color background, Color foreground, int fontSize) {
        combo.setBackground(background);
        combo.setForeground(foreground);
        combo.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //check box
    /**
     * 
     * @param checkBox
     * @param background
     * @param foreground
     * @param fontSize
     */
    public void estilizarCheckBox(JCheckBox checkBox, Color background, Color foreground, int fontSize) {
        checkBox.setBackground(background);
        checkBox.setForeground(foreground);
        checkBox.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //radio button
    /**
     * 
     * @param radioButton
     * @param background
     * @param foreground
     * @param fontSize
     */
    public void estilizarRadioButton(JRadioButton radioButton, Color background, Color foreground, int fontSize) {
        radioButton.setBackground(background);
        radioButton.setForeground(foreground);
        radioButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //slider
    /**
     * 
     * @param slider
     * @param trackColor
     * @param thumbColor
     */
    public void estilizarSlider(JSlider slider, Color trackColor, Color thumbColor) {
        slider.setBackground(trackColor);
        slider.setForeground(thumbColor);
    }

    //progress bar
    /**
     * 
     * @param progressBar
     * @param background
     * @param foreground
     */
    public void estilizarProgressBar(JProgressBar progressBar, Color background, Color foreground) {
        progressBar.setBackground(background);
        progressBar.setForeground(foreground);
    }

    //menu bar
    /**
     * 
     * @param menuBar
     * @param background
     */
    public void estilizarMenuBar(JMenuBar menuBar, Color background) {
        menuBar.setBackground(background);
    }

    //menu
    /**
     * 
     * @param menu
     * @param foreground
     * @param fontSize
     */
    public void estilizarMenu(JMenu menu, Color foreground, int fontSize) {
        menu.setForeground(foreground);
        menu.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //menu item
    /**
     * 
     * @param menuItem
     * @param foreground
     * @param fontSize
     */
    public void estilizarMenuItem(JMenuItem menuItem, Color foreground, int fontSize) {
        menuItem.setForeground(foreground);
        menuItem.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //tabbed pane
    /**
     * 
     * @param tabbedPane
     * @param background
     * @param foreground
     * @param fontSize
     */
    public void estilizarTabbedPane(JTabbedPane tabbedPane, Color background, Color foreground, int fontSize) {
        tabbedPane.setBackground(background);
        tabbedPane.setForeground(foreground);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //scroll pane
    /**
     * 
     * @param scrollPane
     * @param border
     */
    public void estilizarScrollPane(JScrollPane scrollPane, Border border) {
        scrollPane.setBorder(border);
    }

    //tool tip
    /**
     * 
     * @param toolTip
     * @param background
     * @param foreground
     * @param fontSize
     */
    public void estilizarToolTip(JToolTip toolTip, Color background, Color foreground, int fontSize) {
        toolTip.setBackground(background);
        toolTip.setForeground(foreground);
        toolTip.setFont(new Font("Arial", Font.PLAIN, fontSize));
    }

    //borda
    /**
     * 
     * @param component
     * @param border
     */
    public void estilizarBorda(JComponent component, Border border) {
        component.setBorder(border);
    }
}