package logica;

import java.util.*;
import java.io.*;

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

    public boolean cargarPartida() {
        File file = new File("Registros.txt");
        
        //Verificamos si el archivo NO existe o si está totalmente vacío
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n[!] No se encontró ninguna partida guardada. Por favor, inicie una Nueva Partida.");
            return false;
        }
        
        try {
            Scanner scanner = new Scanner(file);
            
            if (scanner.hasNextLine()) {
                String lineaJugador = scanner.nextLine();
                String[] datosJugador = lineaJugador.split(";");
                
                this.jugadorActual = new Jugador(datosJugador[0], datosJugador[1]);
                
                while (scanner.hasNextLine()) {
                    String lineaPkmn = scanner.nextLine();
                    if (!lineaPkmn.trim().isEmpty()) { // Evitamos líneas en blanco
                        String[] datosPkmn = lineaPkmn.split(";");
                        String nombrePkmn = datosPkmn[0];
                        String estadoPkmn = datosPkmn[1];
                        
                        // Buscamos las estadísticas base del Pokémon en la Pokedex maestra
                        Pokemon pMaestro = buscarEnPokedex(nombrePkmn);
                        
                        if (pMaestro != null) {
                            // Usamos tu constructor especial que recibe el Pokémon y el estado
                            Pokemon pGuardado = new Pokemon(pMaestro, estadoPkmn);
                            
                            // Lo añadimos directamente a la lista del jugador
                            this.jugadorActual.getListaPokemon().add(pGuardado);
                        }
                    }
                }
            }
            scanner.close();
            return true;
            
        } catch (Exception e) {
            System.out.println("Error al intentar leer el archivo: " + e.getMessage());
            return false;
        }
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
    
    public void guardarPartida() {
    	if (jugadorActual == null) {
            System.out.println("No hay ninguna partida activa para guardar.");
            return;
        }

        try {
            // FileWriter con false (por defecto) sobrescribe el archivo completo
            BufferedWriter writer = new BufferedWriter(new FileWriter("Registros.txt"));
            
            writer.write(jugadorActual.getNombre() + ";" + jugadorActual.getMedallas());
            writer.newLine(); //salto de linea
            
            //Recorremos la lista de Pokémon del jugador para guardar su estado
            for (Pokemon p : jugadorActual.getListaPokemon()) {
                writer.write(p.getNombre() + ";" + p.getEstado());
                writer.newLine();
            }
            
            //debemos cerrar el writer para que los cambios se guarden.
            writer.close();
            
            System.out.println("¡Partida guardada con éxito!");
            
        } catch (IOException e) {
            System.out.println("Error al intentar guardar la partida: " + e.getMessage());
        }
    }
    
}
