package com.hogar.datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorCSV {
    private static final String RUTA_ARCHIVO = "recursos/datos_menus.csv";

    // Retorna [Nombre, Calorias, Descripcion, Ingredientes, Pasos]
    public static String[] obtenerPlato(String categoriaBuscada, String tipoBuscado) {
        List<String[]> opciones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            br.readLine(); 
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";"); // Usamos punto y coma
                if (datos.length >= 8 && datos[2].equalsIgnoreCase(categoriaBuscada) && datos[3].equalsIgnoreCase(tipoBuscado)) {
                    opciones.add(new String[]{datos[1], datos[4].trim(), datos[5], datos[6], datos[7]});
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo CSV: " + e.getMessage());
        }
        if (opciones.isEmpty()) return new String[]{"Plato no disponible", "0", "Sin descripción", "Sin ingredientes", "Sin pasos"};
        return opciones.get(new Random().nextInt(opciones.size()));
    }
}