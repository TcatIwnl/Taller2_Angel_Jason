package logica;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Sistema {
    private ArrayList<Pokemon> pokedexMaestra;
    private ArrayList<String> listaHabitats;
    private Jugador jugadorActual;

    public Sistema() {
        this.pokedexMaestra = new ArrayList<>();
        this.listaHabitats = new ArrayList<>();
        cargarPokedex();
        cargarHabitats();
    }

    private void cargarPokedex() {
        try {
            File archivo = new File("Pokedex.txt");
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";");
                Pokemon p = new Pokemon(
                    partes[0], partes[1], Double.parseDouble(partes[2]),
                    Integer.parseInt(partes[3]), Integer.parseInt(partes[4]),
                    Integer.parseInt(partes[5]), Integer.parseInt(partes[6]),
                    Integer.parseInt(partes[7]), Integer.parseInt(partes[8]),
                    partes[9]
                );
                pokedexMaestra.add(p);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error al leer Pokedex: " + e.getMessage());
        }
    }

    private void cargarHabitats() {
        try {
            File archivo = new File("Habitats.txt");
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                if (!linea.isEmpty()) {
                    listaHabitats.add(linea);
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error al leer Habitats: " + e.getMessage());
        }
    }

    public Pokemon buscarEnPokedex(String nombre) {
        for (Pokemon p : pokedexMaestra) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Getters para el futuro Main
    public ArrayList<String> getListaHabitats() { return listaHabitats; }
    public Jugador getJugadorActual() { return jugadorActual; }
    public void setJugadorActual(Jugador jugador) { this.jugadorActual = jugador; }
}
