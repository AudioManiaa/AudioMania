package com.audiomania;

import com.audiomania.view.*;
import com.audiomania.utils.StyleConfigurator; // Nova classe

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        StyleConfigurator.applyStyles();

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.iniciar();
        });
    }
}