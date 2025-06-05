package com.audiomania.utils;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

public class StyleConfigurator {

    public static void applyStyles() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    // Definição de Cores Padrão
                    Color corFundoPrincipal = new Color(45, 45, 45);
                    Color corTextoPrincipal = new Color(220, 220, 220);
                    Color corDestaque = new Color(0, 122, 204);
                    Color corTextoDestaque = Color.WHITE;
                    Color corFundoSecundario = new Color(60, 60, 60);

                    // Definição de Fonte Padrão
                    Font fontePadrao = new FontUIResource("Segoe UI", Font.PLAIN, 14);
                    Font fonteTitulos = new FontUIResource("Segoe UI", Font.BOLD, 16);

                    // Aplicando aos Componentes Globais
                    UIManager.put("control", corFundoPrincipal);
                    UIManager.put("info", corTextoPrincipal);
                    UIManager.put("nimbusBase", corDestaque);
                    UIManager.put("nimbusFocus", corDestaque.brighter());
                    UIManager.put("nimbusLightBackground", corFundoSecundario);
                    UIManager.put("nimbusDisabledText", Color.GRAY);
                    UIManager.put("nimbusSelectedText", corTextoDestaque);
                    UIManager.put("text", corTextoPrincipal);
                    UIManager.put("nimbusSelectionBackground", corDestaque);

                    UIManager.put("Panel.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("Label.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("Label.font", fontePadrao);

                    UIManager.put("Button.background", new ColorUIResource(corDestaque));
                    UIManager.put("Button.foreground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("Button.font", fontePadrao);
                    UIManager.put("Button.focus", new ColorUIResource(corDestaque.brighter()));

                    UIManager.put("TextField.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("TextField.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("TextField.caretForeground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("TextField.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("TextField.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("TextField.font", fontePadrao);

                    UIManager.put("PasswordField.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("PasswordField.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("PasswordField.caretForeground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("PasswordField.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("PasswordField.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("PasswordField.font", fontePadrao);

                    UIManager.put("ComboBox.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("ComboBox.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("ComboBox.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("ComboBox.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("ComboBox.font", fontePadrao);

                    UIManager.put("List.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("List.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("List.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("List.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("List.font", fontePadrao);

                    UIManager.put("Table.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("Table.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("Table.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("Table.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("Table.gridColor", new ColorUIResource(corFundoPrincipal.brighter()));
                    UIManager.put("Table.font", fontePadrao);
                    UIManager.put("TableHeader.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("TableHeader.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("TableHeader.font", fonteTitulos);

                    UIManager.put("CheckBox.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("CheckBox.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("CheckBox.font", fontePadrao);
                    UIManager.put("RadioButton.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("RadioButton.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("RadioButton.font", fontePadrao);

                    UIManager.put("TabbedPane.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("TabbedPane.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("TabbedPane.selected", new ColorUIResource(corDestaque));
                    UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(corFundoSecundario));
                    UIManager.put("TabbedPane.font", fontePadrao);

                    UIManager.put("Separator.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("Separator.foreground", new ColorUIResource(corFundoSecundario));

                    UIManager.put("ScrollBar.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("ScrollBar.thumb", new ColorUIResource(corDestaque));
                    UIManager.put("ScrollBar.width", 12);

                    UIManager.put("ToolTip.background", new ColorUIResource(corFundoSecundario.darker()));
                    UIManager.put("ToolTip.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("ToolTip.font", fontePadrao);

                    UIManager.put("MenuBar.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("MenuBar.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("MenuBar.font", fontePadrao);
                    UIManager.put("Menu.background", new ColorUIResource(corFundoPrincipal));
                    UIManager.put("Menu.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("Menu.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("Menu.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("Menu.font", fontePadrao);
                    UIManager.put("MenuItem.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("MenuItem.foreground", new ColorUIResource(corTextoPrincipal));
                    UIManager.put("MenuItem.selectionBackground", new ColorUIResource(corDestaque));
                    UIManager.put("MenuItem.selectionForeground", new ColorUIResource(corTextoDestaque));
                    UIManager.put("MenuItem.font", fontePadrao);
                    UIManager.put("PopupMenu.background", new ColorUIResource(corFundoSecundario));
                    UIManager.put("PopupMenu.font", fontePadrao);

                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("Erro ao definir o Look and Feel: " + ex.getMessage());
            }
        }
    }
}