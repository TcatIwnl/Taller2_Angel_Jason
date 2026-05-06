package logica;
import java.util.*;

public class AltoMando {
	
    private int numero;
    private String nombre;
    private ArrayList<Pokemon> equipo;

    public AltoMando(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
        
    }

    public void agregarPokemon(Pokemon p) {
        if (p != null) equipo.add(p);
    }

    public int getNumero() {
    	return numero;
    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public ArrayList<Pokemon> getEquipo() {
    	return equipo;
    }
    
}