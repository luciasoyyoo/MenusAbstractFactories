package com.hogar;

import com.hogar.gui.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }

}
