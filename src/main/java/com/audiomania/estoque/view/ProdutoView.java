package com.audiomania.estoque.view;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;
    private final NumberFormat currencyFormatter;

    public ProdutoView() {
        this.scanner = new Scanner(System.in);
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    // Menu para gerenciamento de produtos
    public int exibirMenuProdutos() {
        System.out.println("\n===== GERENCIAMENTO DE PRODUTOS =====");
        System.out.println("1. Listar todos os produtos");
        System.out.println("2. Buscar produto por código");
        System.out.println("3. Cadastrar novo produto");
        System.out.println("4. Editar produto existente");
        System.out.println("5. Remover produto");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

