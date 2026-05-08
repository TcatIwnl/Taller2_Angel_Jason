package logica;

import java.util.ArrayList;

/**
 * Clase que representa al jugador dentro del sistema.
 * 
 * Almacena su nombre, las medallas obtenidas y la lista de Pokémon
 * capturados durante la partida.
 */
public class Jugador {
    private String nombre;
    private String medallas;
    private ArrayList<Pokemon> listaPokemon;

    /**
     * Constructor de la clase Jugador.
     * 
     * Inicializa el nombre, las medallas y crea la lista donde se
     * almacenarán los Pokémon del jugador.
     * 
     * @param nombre Nombre o apodo del jugador.
     * @param medallas Última medalla obtenida o estado de progreso.
     */
    public Jugador(String nombre, String medallas) {
        this.nombre = nombre;
        this.medallas = medallas;
        this.listaPokemon = new ArrayList<>();
    }

    /**
     * Agrega un Pokémon a la lista del jugador si aún no lo posee.
     * 
     * @param p Pokémon que se desea capturar.
     */
    public void capturarPokemon(Pokemon p) {
        // Validación: Un pokemon no se puede repetir
        if (!tienePokemon(p.getNombre())) {
            listaPokemon.add(p);
        }
    }

    /**
     * Verifica si el jugador ya tiene un Pokémon registrado
     * con el nombre recibido.
     * 
     * @param nombreBusqueda Nombre del Pokémon que se desea buscar.
     * @return true si el Pokémon ya existe en la lista, false en caso contrario.
     */
    private boolean tienePokemon(String nombreBusqueda) {
        for (Pokemon p : listaPokemon) {
            if (p.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cambia la posición de dos Pokémon dentro de la lista del jugador.
     * 
     * Este método se utiliza para organizar el equipo principal y los
     * Pokémon almacenados en el PC.
     * 
     * @param indice1 Posición del primer Pokémon.
     * @param indice2 Posición del segundo Pokémon.
     * @return true si el cambio se realizó correctamente, false si algún índice no es válido.
     */
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

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return Nombre del jugador.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la última medalla o progreso registrado del jugador.
     * 
     * @return Medalla o progreso actual.
     */
    public String getMedallas() { return medallas; }

    /**
     * Modifica la medalla o progreso actual del jugador.
     * 
     * @param medallas Nueva medalla o progreso.
     */
    public void setMedallas(String medallas) { this.medallas = medallas; }

    /**
     * Obtiene la lista completa de Pokémon del jugador.
     * 
     * @return Lista de Pokémon capturados.
     */
    public ArrayList<Pokemon> getListaPokemon() { return listaPokemon; }
}