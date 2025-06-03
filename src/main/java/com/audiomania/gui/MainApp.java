package com.audiomania.gui;

import javax.swing.*;
import java.awt.EventQueue;
import java.awt.BorderLayout; // Import BorderLayout

public class MainApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("AudioMania");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JLabel label = new JLabel("Hello Swing!", SwingConstants.CENTER);
            frame.getContentPane().add(label, BorderLayout.CENTER); // Add label to the content pane

            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
            System.out.println("Swing JFrame with a JLabel should be visible now.");
        });
    }
}
