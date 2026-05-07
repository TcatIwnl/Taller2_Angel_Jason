package logica;

import java.util.ArrayList;

public class Gimnasio {
    private int numero;
    private String nombreLider;
    private String estado; // Sin derrotar o derrotado
    private ArrayList<Pokemon> equipoLider;

    public Gimnasio(int numero, String nombreLider, String estado) {
        this.numero = numero;
        this.nombreLider = nombreLider;
        this.estado = estado;
        this.equipoLider = new ArrayList<>();
    }

    public void agregarPokemon(Pokemon p) {
        if (p != null) {
            equipoLider.add(p);
        }
    }

	public int getNumero() {
		return numero;
	}

	public String getNombreLider() {
		return nombreLider;
	}

	public String getEstado() {
		return estado;
	}

	public ArrayList<Pokemon> getEquipoLider() {
		return equipoLider;
	}

	public void setEstado(String estado) {
	    this.estado = estado;
	}
    
    
}