package com.audiomania.utils;

import javax.swing.ImageIcon;
import java.awt.Image; // Importação necessária para redimensionar a imagem
import java.net.URL;

/**
 * Classe de utilidade para criar e carregar ícones para a aplicação.
 * Centraliza a lógica de carregamento de recursos para fácil manutenção.
 */
public class FabricaDeIcones {

    /**
     * Carrega um ícone do caminho especificado e o redimensiona.
     * O método é estático para que possa ser chamado sem instanciar a classe.
     *
     * @param caminho O caminho para o arquivo de ícone (ex: "/icons/customer.png").
     * @param descricao Uma descrição para o ícone (importante para acessibilidade).
     * @param tamanho O tamanho (largura e altura) desejado para o ícone.
     * @return um objeto ImageIcon redimensionado, ou null se o recurso não for encontrado.
     */
    public static ImageIcon criarIcone(String caminho, String descricao, int tamanho) {
        // Usa a classe para obter o recurso do classpath.
        URL imgURL = FabricaDeIcones.class.getResource(caminho);
        if (imgURL != null) {
            ImageIcon iconeOriginal = new ImageIcon(imgURL, descricao);
            // Pega a imagem do ícone e a redimensiona
            Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH);
            // Retorna um novo ícone com a imagem no tamanho certo
            return new ImageIcon(imagemRedimensionada, descricao);
        } else {
            // Imprime um erro claro se o ícone não for encontrado.
            System.err.println("Não foi possível encontrar o arquivo de ícone: " + caminho);
            return null;
        }
    }
}