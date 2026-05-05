package logica;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Sistema {
    private ArrayList<Pokemon> pokedexMaestra;
    private ArrayList<String> listaHabitats;
    private ArrayList<Gimnasio> listaGimnasios;
    private Jugador jugadorActual;

    public Sistema() {
        this.pokedexMaestra = new ArrayList<>();
        this.listaHabitats = new ArrayList<>();
        this.listaGimnasios = new ArrayList<>();
        cargarPokedex();
        cargarHabitats();
        cargarGimnasios();
    }

    private void cargarPokedex() {
        try {
            File file = new File("Pokedex.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
            	
                String linea = scanner.nextLine();
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
            File file = new File("Habitats.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
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
    
    
    public void cargarGimnasios() {
    
    	try {
    		File file = new File("Gimnasios.txt");
    		Scanner scanner = new Scanner(file);
    		while (scanner.hasNextLine()) {
    			String linea = scanner.nextLine();
    			String[] partes = linea.split(";");
    			
    			int num = Integer.parseInt(partes[0]);
    			String lider = partes[1];
    			String estado = partes[2];
    			int cant = Integer.parseInt(partes[3]);
    			
    			Gimnasio g = new Gimnasio(num, lider, estado);
    			
    			for (int i = 0; i < cant; i++) {
    				String nombrePkmn = partes[4 + i];
    				Pokemon p = buscarEnPokedex(nombrePkmn);
    				g.agregarPokemon(p);
    			}
    			
				listaGimnasios.add(g);
    			
    		}
    		
    		
    		scanner.close();
    		
    		
    	} catch (Exception e) {
    		System.out.println("Error en la carga de gimnasios: " + e.getMessage());
    	}
    	
    	
    	
    	
    	
    }
    
    
    // FUTURO
    public ArrayList<String> getListaHabitats() {
    	return listaHabitats;
    }
    
    public Jugador getJugadorActual() {
    	return jugadorActual;
    }
    
    public void setJugadorActual(Jugador jugador) {
    	this.jugadorActual = jugador;
    }
    
    public ArrayList<Gimnasio> getListaGimnasios() {
    	return listaGimnasios;
    }
}
