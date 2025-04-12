/**
 * MenuInterativo.java
 * Módulo para auxiliar na criação de menus interativos no terminal.
 * 
 * @author Joao
 */


package com.audiomania;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class MenuInterativo {
    
    /**
     * Classe que representa uma opção do menu
     */
    public static class OpcaoMenu {
        private String texto;
        private Consumer<Scanner> acao;
        
        /**
         * Construtor para criar uma opção de menu
         * 
         * @param texto O texto a ser exibido para esta opção
         * @param acao A ação a ser executada quando esta opção for selecionada
         */
        public OpcaoMenu(String texto, Consumer<Scanner> acao) {
            this.texto = texto;
            this.acao = acao;
        }
        
        /**
         * Obtém o texto da opção
         * 
         * @return O texto da opção
         */
        public String getTexto() {
            return texto;
        }
        
        /**
         * Executa a ação associada a esta opção
         * 
         * @param scanner O scanner para entrada do usuário
         */
        public void executar(Scanner scanner) {
            acao.accept(scanner);
        }
    }
    
    /**
     * Cria e exibe um menu interativo no terminal
     * 
     * @param titulo O título do menu
     * @param opcoes Lista de opções do menu
     */
    public static void criarMenu(String titulo, List<OpcaoMenu> opcoes) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        
        while (executando) {
            // Limpa a tela (pode não funcionar em todos os terminais)
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            // Exibe o título
            System.out.println("===== " + titulo + " =====");
            System.out.println();
            
            // Exibe as opções
            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((i + 1) + ". " + opcoes.get(i).getTexto());
            }
            
            // Adiciona opção de sair
            System.out.println("0. Sair");
            System.out.println();
            
            // Solicita a escolha do usuário
            System.out.print("Escolha uma opção: ");
            
            try {
                int escolha = Integer.parseInt(scanner.nextLine());
                
                if (escolha == 0) {
                    executando = false;
                } else if (escolha > 0 && escolha <= opcoes.size()) {
                    opcoes.get(escolha - 1).executar(scanner);
                    
                    // Pausa para o usuário ver o resultado
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                } else {
                    System.out.println("Opção inválida. Pressione ENTER para continuar...");
                    scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Método auxiliar para criar um menu com opções simples
     * 
     * @param titulo O título do menu
     * @param opcoesTexto Array de textos das opções
     * @param acoes Array de ações correspondentes às opções
     */
    public static void criarMenuSimples(String titulo, String[] opcoesTexto, Consumer<Scanner>[] acoes) {
        if (opcoesTexto.length != acoes.length) {
            throw new IllegalArgumentException("O número de textos de opções deve ser igual ao número de ações");
        }
        
        List<OpcaoMenu> opcoes = new ArrayList<>();
        for (int i = 0; i < opcoesTexto.length; i++) {
            opcoes.add(new OpcaoMenu(opcoesTexto[i], acoes[i]));
        }
        
        criarMenu(titulo, opcoes);
    }
    
    /**
     * Exemplo de uso do MenuInterativo
     */
    public static void main(String[] args) {
        // Exemplo de criação de menu
        List<OpcaoMenu> opcoes = new ArrayList<>();
        
        opcoes.add(new OpcaoMenu("Exibir mensagem", scanner -> {
            System.out.println("Olá, mundo!");
        }));
        
        opcoes.add(new OpcaoMenu("Calcular soma", scanner -> {
            System.out.print("Digite o primeiro número: ");
            int num1 = Integer.parseInt(scanner.nextLine());
            System.out.print("Digite o segundo número: ");
            int num2 = Integer.parseInt(scanner.nextLine());
            System.out.println("A soma é: " + (num1 + num2));
        }));
        
        criarMenu("Menu de Exemplo", opcoes);
    }
}
