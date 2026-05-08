package logica;
import java.util.*;

/**
 * Clase que representa a un miembro del Alto Mando.
 * 
 * Cada integrante posee un número identificador,
 * un nombre y un equipo de Pokémon para combatir.
 */
public class AltoMando {
	
    private int numero;
    private String nombre;
    private ArrayList<Pokemon> equipo;

    /**
     * Constructor de la clase AltoMando.
     * 
     * Inicializa el número, nombre y crea la lista
     * que almacenará el equipo Pokémon del miembro.
     * 
     * @param numero Número identificador del miembro del Alto Mando.
     * @param nombre Nombre del integrante.
     */
    public AltoMando(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
        
    }

    /**
     * Agrega un Pokémon al equipo del Alto Mando.
     * 
     * Solo se agregará si el Pokémon recibido no es null.
     * 
     * @param p Pokémon que será agregado al equipo.
     */
    public void agregarPokemon(Pokemon p) {
        if (p != null) equipo.add(p);
    }

    /**
     * Obtiene el número identificador del miembro del Alto Mando.
     * 
     * @return Número del integrante.
     */
    public int getNumero() {
    	return numero;
    }
    
    /**
     * Obtiene el nombre del integrante del Alto Mando.
     * 
     * @return Nombre del integrante.
     */
    public String getNombre() {
    	return nombre;
    }
    
    /**
     * Obtiene el equipo Pokémon del integrante.
     * 
     * @return Lista de Pokémon del Alto Mando.
     */
    public ArrayList<Pokemon> getEquipo() {
    	return equipo;
    }
    
}