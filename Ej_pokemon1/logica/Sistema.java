package logica;

import java.util.*;
import java.io.*;

public class Sistema {
    private ArrayList<Pokemon> pokedexMaestra;
    private ArrayList<String> listaHabitats;
    private ArrayList<Gimnasio> listaGimnasios;
    private ArrayList<AltoMando> listaAltoMando;
    private Jugador jugadorActual;

    public Sistema() {
        this.pokedexMaestra = new ArrayList<>();
        this.listaHabitats = new ArrayList<>();
        this.listaGimnasios = new ArrayList<>();
        this.listaAltoMando = new ArrayList<>();
        
        cargarPokedex();
        cargarHabitats();
        cargarGimnasios();
        cargarAltoMando();
    }
    
    public void salirACapturar(Scanner scanner) {
        System.out.println("\n¿Donde deseas ir a explorar?");
        System.out.println("\nZonas disponibles:");
        
        // Mostrar zonas
        for (int i = 0; i < listaHabitats.size(); i++) {
            System.out.println((i + 1) + ") " + listaHabitats.get(i));
        }
        System.out.println((listaHabitats.size() + 1) + ") Volver al menu.");
        System.out.print("\nIngrese Zona: ");

        try {
            int opcionZona = Integer.parseInt(scanner.nextLine());
            
            if (opcionZona == listaHabitats.size() + 1) return; // Vuelve al menú
            
            if (opcionZona < 1 || opcionZona > listaHabitats.size()) {
                System.out.println("Esa zona no existe.");
                return;
            }

            String zonaElegida = listaHabitats.get(opcionZona - 1);

            // Filtrar los Pokémon que viven en esta zona
            ArrayList<Pokemon> posibles = new ArrayList<>();
            double sumaProbabilidades = 0.0;
            
            for (Pokemon p : pokedexMaestra) {
                if (p.getHabitat().equalsIgnoreCase(zonaElegida)) {
                    posibles.add(p);
                    sumaProbabilidades += p.getPorcentajeAparicion();
                }
            }

            if (posibles.isEmpty()) {
                System.out.println("Aún no hay registros de Pokémon en esta zona.");
                return;
            }

            // Lógica de aparición aleatoria (Probabilidad Acumulada)
            double numeroAleatorio = Math.random() * sumaProbabilidades;
            double acumulado = 0.0;
            Pokemon salvaje = posibles.get(posibles.size() - 1); // Por defecto el último

            for (Pokemon p : posibles) {
                acumulado += p.getPorcentajeAparicion();
                if (numeroAleatorio <= acumulado) {
                    salvaje = p;
                    break; // Encontramos al Pokémon
                }
            }

            // Interacción con el usuario
            System.out.println("\nOh!! Ha aparecido un increible " + salvaje.getNombre() + "!!");
            System.out.println("\nQue deseas hacer?");
            System.out.println("1) Capturar");
            System.out.println("2) Huir");
            System.out.print("\nIngrese Opcion: ");

            int accion = Integer.parseInt(scanner.nextLine());
            
            if (accion == 1) {
                // Verificar si ya lo tiene
                boolean repetido = false;
                for (Pokemon p : jugadorActual.getListaPokemon()) {
                    if (p.getNombre().equalsIgnoreCase(salvaje.getNombre())) {
                        repetido = true;
                        break;
                    }
                }

                if (repetido) {
                    System.out.println("\n¡Ya tienes a " + salvaje.getNombre() + "! No puedes capturarlo de nuevo.");
                } else {
                    // Creamos una copia
                    Pokemon nuevoCapturado = new Pokemon(salvaje, "Vivo");
                    jugadorActual.getListaPokemon().add(nuevoCapturado);
                    
                    System.out.println("\n" + salvaje.getNombre() + " capturado con exito!!");
                    
                    // Si el equipo tiene espacio, va al equipo. Si no, al PC.
                    if (jugadorActual.getListaPokemon().size() <= 6) {
                        System.out.println(salvaje.getNombre() + " ha sido agregado a tu equipo!");
                    } else {
                        System.out.println(salvaje.getNombre() + " ha sido enviado al PC!");
                    }
                }
            } else if (accion == 2) {
                System.out.println("\nHas huido sin problemas.");
            } else {
                System.out.println("\nOpción inválida. El Pokémon se ha asustado y huyó.");
            }

        } catch (Exception e) {
            System.out.println("Error de entrada. Volviendo al menú...");
        }
    }
    
    public void revisarEquipo() {
        if (jugadorActual == null || jugadorActual.getListaPokemon().isEmpty()) {
            System.out.println("\nTu equipo está vacío. ¡Sal a explorar y capturar Pokémon!");
            return;
        }

        System.out.println("\nEquipo Actual:");
        ArrayList<Pokemon> lista = jugadorActual.getListaPokemon();
        
        // Determinamos el límite: el tamaño de la lista o 6
        int limite = Math.min(lista.size(), 6);
        
        for (int i = 0; i < limite; i++) {
            Pokemon p = lista.get(i);
            // PRINT
            System.out.println((i + 1) + ") " + p.getNombre() + "|" + p.getTipo() + "|Stats totales: " + p.getSumaStats() + " [" + p.getEstado() + "]");
        }
    }
    
    public void curarPokemones() {
        if (jugadorActual != null) {

            for (Pokemon p : jugadorActual.getListaPokemon()) {
                p.setEstado("Vivo");
            }
            System.out.println("¡Tus Pokémon han sido curados! Todos están en estado: Vivo.");
        } else {
            System.out.println("Error: No hay un jugador activo.");
        }
    }

    private void cargarAltoMando() {
        try {
        	
            File file = new File("Alto Mando.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                
                int num = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                AltoMando am = new AltoMando(num, nombre);
                
                // Siempre son 6 Pokémon después del nombre
                for (int i = 2; i < partes.length; i++) {
                    Pokemon p = buscarEnPokedex(partes[i]);
                    am.agregarPokemon(p);
                }
                listaAltoMando.add(am);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error al leer Alto Mando: " + e.getMessage());
        }
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
