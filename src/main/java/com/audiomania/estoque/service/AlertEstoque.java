package com.audiomania.estoque.service;

import com.audiomania.estoque.model.ItemEstoque;

import java.util.ArrayList;
import java.util.List;

public class AlertEstoque {
    private static final List<ItemEstoque> itensAlerta = new ArrayList<>();

    public static void verificarEstoque(ItemEstoque item) {
        if (item.isEstoqueBaixo()) {
            if (!itensAlerta.contains(item)) {
                itensAlerta.add(item);
                gerarAlerta(item);
            }
        } else {
            itensAlerta.remove(item);
        }
    }

    private static void gerarAlerta(ItemEstoque item) {
        System.out.println("=== ALERTA DE ESTOQUE BAIXO ===");
        System.out.println("Produto: " + item.getProduto().getNome());
        System.out.println("Código: " + item.getProduto().getCodigo());
        System.out.println("Quantidade atual: " + item.getQuantidade());
        System.out.println("Quantidade mínima: " + item.getQuantidadeMinima());
        System.out.println("Localização: " + item.getLocalizacao());
        System.out.println("===============================");
    }

    public static List<ItemEstoque> getItensComAlerta() {
        return new ArrayList<>(itensAlerta);
    }

    public static void verificarTodosItens(List<ItemEstoque> itens) {
        for (ItemEstoque item : itens) {
            verificarEstoque(item);
        }
    }
}