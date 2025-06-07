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

                    // Definição de Cores Otimizadas para uma experiência visual mais rica
                    Color corFundoPrincipal = new Color(25, 25, 25); // Fundo muito escuro para profundidade
                    Color corFundoSecundario = new Color(40, 40, 40); // Fundo para elementos secundários, ligeiramente mais claro
                    Color corDestaque = new Color(0, 150, 120); // Verde-azulado vibrante para destaque principal
                    Color corDestaqueClaro = new Color(0, 180, 150); // Versão mais clara da cor de destaque para foco/hover
                    Color corTextoPrincipal = new Color(240, 240, 240); // Texto muito claro para alto contraste
                    Color corTextoSecundario = new Color(150, 150, 150); // Cinza médio para texto secundário ou dicas
                    Color corTextoDestaque = Color.WHITE; // Texto branco para elementos em destaque
                    Color corBordaFina = new Color(70, 70, 70); // Cor para bordas sutis e separadores
                    Color corErro = new Color(200, 50, 50); // Vermelho para mensagens de erro ou avisos

                    // Definição de Fontes Otimizadas
                    // Usando "Inter" como alternativa a "Segoe UI" se disponível, para um visual moderno e limpo.
                    // Se não, "Segoe UI" ou uma fonte sans-serif padrão será usada.
                    Font fontePadrao = new FontUIResource("Inter", Font.PLAIN, 14); // Fonte padrão legível
                    Font fonteTitulos = new FontUIResource("Inter", Font.BOLD, 16); // Fonte para títulos, mais proeminente
                    Font fonteSubtitulos = new FontUIResource("Inter", Font.PLAIN, 12); // Fonte menor para detalhes

                    // Verificação e fallback para fontes
                    if (!"Inter".equals(fontePadrao.getFamily())) {
                        fontePadrao = new FontUIResource("Segoe UI", Font.PLAIN, 14);
                        fonteTitulos = new FontUIResource("Segoe UI", Font.BOLD, 16);
                        fonteSubtitulos = new FontUIResource("Segoe UI", Font.PLAIN, 12);
                    }


                    // Aplicando aos Componentes Globais
                    UIManager.put("control", new ColorUIResource(corFundoPrincipal)); // Cor de fundo para controles gerais
                    UIManager.put("info", new ColorUIResource(corTextoPrincipal)); // Cor de informação
                    UIManager.put("nimbusBase", new ColorUIResource(corFundoSecundario)); // Cor base do tema Nimbus
                    UIManager.put("nimbusFocus", new ColorUIResource(corDestaqueClaro)); // Cor de foco para elementos interativos
                    UIManager.put("nimbusLightBackground", new ColorUIResource(corFundoSecundario)); // Fundo claro do tema Nimbus (para certas áreas)
                    UIManager.put("nimbusDisabledText", new ColorUIResource(corTextoSecundario.darker())); // Cor para texto desabilitado
                    UIManager.put("nimbusSelectedText", new ColorUIResource(corTextoDestaque)); // Cor do texto selecionado
                    UIManager.put("text", new ColorUIResource(corTextoPrincipal)); // Cor do texto geral
                    UIManager.put("nimbusSelectionBackground", new ColorUIResource(corDestaque)); // Cor de fundo para seleção

                    UIManager.put("Panel.background", new ColorUIResource(corFundoPrincipal)); // Fundo do painel
                    UIManager.put("Label.foreground", new ColorUIResource(corTextoPrincipal)); // Cor do texto do rótulo
                    UIManager.put("Label.font", fontePadrao); // Fonte do rótulo

                    // Botões com visual mais distinto
                    UIManager.put("Button.background", new ColorUIResource(corDestaque)); // Fundo do botão com cor de destaque
                    UIManager.put("Button.foreground", new ColorUIResource(corTextoDestaque)); // Texto do botão (branco)
                    UIManager.put("Button.font", fontePadrao); // Fonte do botão
                    UIManager.put("Button.focus", new ColorUIResource(corDestaqueClaro)); // Foco do botão
                    UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 15, 8, 15)); // Mais padding para botões
                    UIManager.put("Button.select", new ColorUIResource(corDestaqueClaro)); // Cor quando o botão é clicado

                    // Campos de Texto/Senha com bordas e cores refinadas
                    UIManager.put("TextField.background", new ColorUIResource(corFundoSecundario)); // Fundo do campo de texto
                    UIManager.put("TextField.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do campo de texto
                    UIManager.put("TextField.caretForeground", new ColorUIResource(corTextoPrincipal)); // Cor do cursor (caret)
                    UIManager.put("TextField.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção no campo de texto
                    UIManager.put("TextField.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção no campo de texto
                    UIManager.put("TextField.font", fontePadrao); // Fonte do campo de texto
                    UIManager.put("TextField.border", BorderFactory.createLineBorder(corBordaFina, 1, true)); // Borda sutil com cantos arredondados

                    UIManager.put("PasswordField.background", new ColorUIResource(corFundoSecundario)); // Fundo do campo de senha
                    UIManager.put("PasswordField.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do campo de senha
                    UIManager.put("PasswordField.caretForeground", new ColorUIResource(corTextoPrincipal)); // Cor do cursor (caret)
                    UIManager.put("PasswordField.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção no campo de senha
                    UIManager.put("PasswordField.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção no campo de senha
                    UIManager.put("PasswordField.font", fontePadrao); // Fonte do campo de senha
                    UIManager.put("PasswordField.border", BorderFactory.createLineBorder(corBordaFina, 1, true)); // Borda sutil com cantos arredondados

                    UIManager.put("ComboBox.background", new ColorUIResource(corFundoSecundario)); // Fundo do combobox
                    UIManager.put("ComboBox.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do combobox
                    UIManager.put("ComboBox.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção do combobox
                    UIManager.put("ComboBox.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção do combobox
                    UIManager.put("ComboBox.font", fontePadrao); // Fonte do combobox
                    UIManager.put("ComboBox.border", BorderFactory.createLineBorder(corBordaFina, 1, true)); // Borda sutil com cantos arredondados
                    UIManager.put("ComboBox.buttonBackground", new ColorUIResource(corDestaque.darker())); // Cor do botão do combobox
                    UIManager.put("ComboBox.buttonForeground", new ColorUIResource(corTextoDestaque)); // Cor da seta do combobox

                    UIManager.put("List.background", new ColorUIResource(corFundoSecundario)); // Fundo da lista
                    UIManager.put("List.foreground", new ColorUIResource(corTextoPrincipal)); // Texto da lista
                    UIManager.put("List.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção da lista
                    UIManager.put("List.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção da lista
                    UIManager.put("List.font", fontePadrao); // Fonte da lista

                    // Tabela com linhas alternadas para melhor legibilidade
                    UIManager.put("Table.background", new ColorUIResource(corFundoSecundario)); // Fundo da tabela
                    UIManager.put("Table.foreground", new ColorUIResource(corTextoPrincipal)); // Texto da tabela
                    UIManager.put("Table.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção da tabela
                    UIManager.put("Table.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção da tabela
                    UIManager.put("Table.gridColor", new ColorUIResource(corBordaFina.darker())); // Cor da grade da tabela
                    UIManager.put("Table.font", fontePadrao); // Fonte da tabela
                    UIManager.put("TableHeader.background", new ColorUIResource(corFundoPrincipal.brighter())); // Fundo do cabeçalho da tabela
                    UIManager.put("TableHeader.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do cabeçalho da tabela
                    UIManager.put("TableHeader.font", fonteTitulos); // Fonte do cabeçalho da tabela
                    UIManager.put("Table.alternateRowColor", new ColorUIResource(new Color(45, 45, 45))); // Cor para linhas alternadas

                    UIManager.put("CheckBox.background", new ColorUIResource(corFundoPrincipal)); // Fundo do checkbox
                    UIManager.put("CheckBox.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do checkbox
                    UIManager.put("CheckBox.font", fontePadrao); // Fonte do checkbox
                    UIManager.put("RadioButton.background", new ColorUIResource(corFundoPrincipal)); // Fundo do radio button
                    UIManager.put("RadioButton.foreground", new ColorUIResource(corTextoSecundario)); // Texto do radio button
                    UIManager.put("RadioButton.font", fontePadrao); // Fonte do radio button

                    // Painel de abas com melhor destaque para a aba selecionada
                    UIManager.put("TabbedPane.background", new ColorUIResource(corFundoPrincipal)); // Fundo do painel de abas
                    UIManager.put("TabbedPane.foreground", new ColorUIResource(corTextoSecundario)); // Texto das abas não selecionadas
                    UIManager.put("TabbedPane.selected", new ColorUIResource(corDestaque)); // Fundo da aba selecionada
                    UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(corFundoSecundario)); // Área de conteúdo do painel de abas
                    UIManager.put("TabbedPane.font", fontePadrao); // Fonte do painel de abas
                    UIManager.put("TabbedPane.focus", new ColorUIResource(corDestaqueClaro)); // Foco na aba

                    UIManager.put("Separator.background", new ColorUIResource(corBordaFina)); // Fundo do separador
                    UIManager.put("Separator.foreground", new ColorUIResource(corBordaFina)); // Cor do separador

                    // Barra de rolagem com mais contraste
                    UIManager.put("ScrollBar.background", new ColorUIResource(corFundoPrincipal.darker())); // Fundo da barra de rolagem
                    UIManager.put("ScrollBar.thumb", new ColorUIResource(corDestaque)); // Cor do "polegar" da barra de rolagem
                    UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(corDestaqueClaro)); // Destaque do polegar
                    UIManager.put("ScrollBar.track", new ColorUIResource(corFundoSecundario.darker())); // Trilho da barra de rolagem
                    UIManager.put("ScrollBar.width", 12); // Largura da barra de rolagem

                    UIManager.put("ToolTip.background", new ColorUIResource(corFundoSecundario.darker())); // Fundo da dica de ferramenta
                    UIManager.put("ToolTip.foreground", new ColorUIResource(corTextoPrincipal)); // Texto da dica de ferramenta
                    UIManager.put("ToolTip.font", fontePadrao); // Fonte da dica de ferramenta
                    UIManager.put("ToolTip.border", BorderFactory.createLineBorder(corDestaque, 1, true)); // Borda na dica

                    // Menus com um visual mais limpo e organizado
                    UIManager.put("MenuBar.background", new ColorUIResource(corFundoPrincipal)); // Fundo da barra de menu
                    UIManager.put("MenuBar.foreground", new ColorUIResource(corTextoPrincipal)); // Texto da barra de menu
                    UIManager.put("MenuBar.font", fontePadrao); // Fonte da barra de menu
                    UIManager.put("Menu.background", new ColorUIResource(corFundoPrincipal)); // Fundo do menu
                    UIManager.put("Menu.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do menu
                    UIManager.put("Menu.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção do menu
                    UIManager.put("Menu.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção do menu
                    UIManager.put("Menu.font", fontePadrao); // Fonte do menu
                    UIManager.put("MenuItem.background", new ColorUIResource(corFundoSecundario)); // Fundo do item de menu
                    UIManager.put("MenuItem.foreground", new ColorUIResource(corTextoPrincipal)); // Texto do item de menu
                    UIManager.put("MenuItem.selectionBackground", new ColorUIResource(corDestaque)); // Fundo da seleção do item de menu
                    UIManager.put("MenuItem.selectionForeground", new ColorUIResource(corTextoDestaque)); // Texto da seleção do item de menu
                    UIManager.put("MenuItem.font", fontePadrao); // Fonte do item de menu
                    UIManager.put("PopupMenu.background", new ColorUIResource(corFundoSecundario)); // Fundo do menu pop-up
                    UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(corBordaFina, 1, true)); // Borda no menu pop-up
                    UIManager.put("PopupMenu.font", fontePadrao); // Fonte do menu pop-up

                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            try {
                // Em caso de erro com Nimbus, tenta aplicar o Look and Feel do sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("Erro ao definir o Look and Feel: " + ex.getMessage());
            }
        }
    }
}
