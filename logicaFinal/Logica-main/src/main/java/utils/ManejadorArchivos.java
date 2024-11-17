package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManejadorArchivos {

    private Map<String, Double> tablaTipos;

    // Constructor para cargar la tabla desde un archivo
    public ManejadorArchivos() {
        tablaTipos = new HashMap<>();
        cargarTablaTipos("tablaDeTipos.txt");
    }

    // Método para cargar la tabla de tipos desde un archivo
    private void cargarTablaTipos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                // Verifica que la línea tenga exactamente tres partes: tipo1, tipo2 y valor
                if (partes.length == 3) {
                    try {
                        String key = partes[0].trim() + ":" + partes[1].trim();
                        double valor = Double.parseDouble(partes[2].trim());
                        tablaTipos.put(key, valor);
                    } catch (NumberFormatException e) {
                        System.err.println("Formato de número inválido en línea: " + linea);
                    }
                } else {
                    System.err.println("Formato incorrecto en línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Método que devuelve el valor de efectividad entre dos tipos
    public double obtenerEfectividad(String tipo1, String tipo2) {
        String key = tipo1.trim() + ":" + tipo2.trim();
        return tablaTipos.getOrDefault(key, 1.0); // Devuelve 1.0 si no encuentra la combinación
    }
}
