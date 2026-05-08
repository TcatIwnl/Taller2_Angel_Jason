package logica;

import java.util.ArrayList;

/**
 * Clase que representa un Gimnasio Pokémon dentro del juego.
 * 
 * Cada gimnasio posee un número identificador, un líder,
 * un estado y un equipo de Pokémon perteneciente al líder.
 */
public class Gimnasio {
    private int numero;
    private String nombreLider;
    private String estado; // Sin derrotar o derrotado
    private ArrayList<Pokemon> equipoLider;

    /**
     * Constructor de la clase Gimnasio.
     * 
     * Inicializa los datos principales del gimnasio y crea
     * la lista que almacenará el equipo del líder.
     * 
     * @param numero Número del gimnasio.
     * @param nombreLider Nombre del líder del gimnasio.
     * @param estado Estado actual del gimnasio.
     */
    public Gimnasio(int numero, String nombreLider, String estado) {
        this.numero = numero;
        this.nombreLider = nombreLider;
        this.estado = estado;
        this.equipoLider = new ArrayList<>();
    }

    /**
     * Agrega un Pokémon al equipo del líder del gimnasio.
     * 
     * Solo se agregará si el Pokémon recibido no es null.
     * 
     * @param p Pokémon que será agregado al equipo.
     */
    public void agregarPokemon(Pokemon p) {
        if (p != null) {
            equipoLider.add(p);
        }
    }

    /**
     * Obtiene el número del gimnasio.
     * 
     * @return Número identificador del gimnasio.
     */
	public int getNumero() {
		return numero;
	}

    /**
     * Obtiene el nombre del líder del gimnasio.
     * 
     * @return Nombre del líder.
     */
	public String getNombreLider() {
		return nombreLider;
	}

    /**
     * Obtiene el estado actual del gimnasio.
     * 
     * @return Estado del gimnasio.
     */
	public String getEstado() {
		return estado;
	}

    /**
     * Obtiene el equipo Pokémon del líder del gimnasio.
     * 
     * @return Lista de Pokémon del líder.
     */
	public ArrayList<Pokemon> getEquipoLider() {
		return equipoLider;
	}

    /**
     * Modifica el estado actual del gimnasio.
     * 
     * @param estado Nuevo estado del gimnasio.
     */
	public void setEstado(String estado) {
	    this.estado = estado;
	}
    
    
}