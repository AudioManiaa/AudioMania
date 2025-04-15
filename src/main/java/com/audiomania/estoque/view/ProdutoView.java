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

