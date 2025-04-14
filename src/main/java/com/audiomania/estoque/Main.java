package com.audiomania.estoque;

import com.audiomania.estoque.controller.EstoqueController;

public class Main {
    public static void main(String[] args) {
        EstoqueController controller = new EstoqueController();
        controller.iniciarSistema();
    }
}