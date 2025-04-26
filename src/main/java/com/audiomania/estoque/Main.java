package com.audiomania.estoque;

public class Main {
    public static void main(String[] args) {
        ProdutoRepository gerenciador = new ProdutoRepository();
        gerenciador.exibirMenu();
    }
}