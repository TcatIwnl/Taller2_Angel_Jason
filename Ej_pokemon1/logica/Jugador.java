package logica;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private String medallas;
    private ArrayList<Pokemon> listaPokemon;

    public Jugador(String nombre, String medallas) {
        this.nombre = nombre;
        this.medallas = medallas;
        this.listaPokemon = new ArrayList<>();
    }

    public void capturarPokemon(Pokemon p) {
        // Validación: Un pokemon no se puede repetir
        if (!tienePokemon(p.getNombre())) {
            listaPokemon.add(p);
        }
    }

    private boolean tienePokemon(String nombreBusqueda) {
        for (Pokemon p : listaPokemon) {
            if (p.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                return true;
            }
        }
        return false;
    }

    // Lógica para Acceso al PC (Opción 3 del menú)
    public boolean cambiarPosicion(int indice1, int indice2) {
        if (indice1 >= 0 && indice1 < listaPokemon.size() && 
            indice2 >= 0 && indice2 < listaPokemon.size()) {
            
            Pokemon temporal = listaPokemon.get(indice1);
            listaPokemon.set(indice1, listaPokemon.get(indice2));
            listaPokemon.set(indice2, temporal);
            return true;
        }
        return false;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getMedallas() { return medallas; }
    public void setMedallas(String medallas) { this.medallas = medallas; }
    public ArrayList<Pokemon> getListaPokemon() { return listaPokemon; }
}

