package logica;

import java.util.*;
import java.io.*;

/**
 * Clase principal que administra la lógica general del juego.
 * 
 * Se encarga de cargar datos desde archivos, manejar al jugador actual,
 * controlar capturas, batallas contra gimnasios, desafíos al Alto Mando,
 * curación de Pokémon, acceso al PC y guardado/carga de partidas.
 */
public class Sistema {
    private ArrayList<Pokemon> pokedexMaestra;
    private ArrayList<String> listaHabitats;
    private ArrayList<Gimnasio> listaGimnasios;
    private ArrayList<AltoMando> listaAltoMando;
    private Jugador jugadorActual;

    /**
     * Constructor de la clase Sistema.
     * 
     * Inicializa las listas principales del sistema y carga la información
     * necesaria desde los archivos de texto.
     */
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
    
    /**
     * Permite al jugador desafiar al Alto Mando.
     * 
     * Antes de iniciar el desafío, valida que el jugador haya derrotado
     * todos los gimnasios y que tenga al menos un Pokémon vivo en su equipo.
     * 
     * @param scanner Scanner utilizado para leer las opciones ingresadas por el usuario.
     */
    public void desafiarAltoMando(Scanner scanner) {
        if (listaGimnasios.isEmpty() || !listaGimnasios.get(listaGimnasios.size() - 1).getEstado().equalsIgnoreCase("Derrotado")) {
            System.out.println("\n¡Aun no estas listo! Debes derrotar a todos los Lideres de Gimnasio antes de entrar aqui.");
            return;
        }

        if (!tieneEquipoVivo()) {
            System.out.println("\n¡No tienes Pokémon en condiciones para luchar! Ve a curarlos.");
            return;
        }

        System.out.println("\n=== DESAFIO AL ALTO MANDO ===");

        for (AltoMando am : listaAltoMando) {
            System.out.println("\nEntrando a la sala de " + am.getNombre() + "...");
            boolean victoria = batallaMando(am, scanner);
            
            if (!victoria) {
                System.out.println("\nHas sido derrotado. El Alto Mando es demasiado para ti por ahora.");
                return;
            }
            System.out.println("\n¡Has derrotado a " + am.getNombre() + "! Avanzas a la siguiente sala...");
        }

        System.out.println("\n¡INCREIBLE! Has derrotado a todo el Alto Mando.");
        System.out.println("¡Te has convertido en el nuevo CAMPEON de la region UCN!");
        jugadorActual.setMedallas("CAMPEON");
    }

    /**
     * Ejecuta una batalla contra un integrante del Alto Mando.
     * 
     * La batalla compara los puntajes finales de los Pokémon considerando
     * sus estadísticas y la efectividad entre tipos.
     * 
     * @param am Integrante del Alto Mando que será enfrentado.
     * @param scanner Scanner utilizado para leer las acciones del usuario.
     * @return true si el jugador gana la batalla, false si pierde o se rinde.
     */
    private boolean batallaMando(AltoMando am, Scanner scanner) {
        int indexRival = 0;
        Pokemon pkmnRival = am.getEquipo().get(indexRival);
        Pokemon pkmnJugador = obtenerPrimerVivoEquipo();

        while (true) {
            System.out.println("\n" + am.getNombre() + " saca a " + pkmnRival.getNombre() + "!");
            System.out.println(jugadorActual.getNombre() + " saca a " + pkmnJugador.getNombre() + "!");

            boolean turnoActivo = true;
            while (turnoActivo) {
                System.out.println("\n¿Qué deseas hacer?");
                System.out.println("1) Atacar");
                System.out.println("2) Cambiar de pokemon");
                System.out.println("3) Rendirse");
                System.out.print("Ingrese Opcion: ");

                try {
                    int accion = Integer.parseInt(scanner.nextLine());
                    switch (accion) {
                        case 1:
                            double mJ = TablaTipos.obtenerEfectividad(pkmnJugador.getTipo(), pkmnRival.getTipo());
                            double mR = TablaTipos.obtenerEfectividad(pkmnRival.getTipo(), pkmnJugador.getTipo());
                            int pJ = (int) (pkmnJugador.getSumaStats() * mJ);
                            int pR = (int) (pkmnRival.getSumaStats() * mR);

                            if (pJ >= pR) {
                                System.out.println("\n" + pkmnJugador.getNombre() + " gana el choque!");
                                indexRival++;
                                if (indexRival >= am.getEquipo().size()) return true;
                                pkmnRival = am.getEquipo().get(indexRival);
                                turnoActivo = false;
                            } else {
                                System.out.println("\n" + pkmnJugador.getNombre() + " ha caido...");
                                pkmnJugador.setEstado("Debilitado");
                                if (!tieneEquipoVivo()) return false;
                                pkmnJugador = cambiarPokemonBatalla(scanner);
                                turnoActivo = false;
                            }
                            break;
                        case 2:
                            Pokemon n = cambiarPokemonBatalla(scanner);
                            if (n != null && n != pkmnJugador) {
                                pkmnJugador = n;
                                turnoActivo = false;
                            }
                            break;
                        case 3:
                            return false;
                        default:
                            System.out.println("Opcion no valida.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Error de entrada.");
                }
            }
        }
    }
    
    
    /**
     * Permite al jugador seleccionar y retar a un gimnasio.
     * 
     * Valida si el gimnasio ya fue derrotado, si corresponde al orden correcto
     * de progreso y si el jugador posee Pokémon vivos para combatir.
     * 
     * @param scanner Scanner utilizado para leer la opción del usuario.
     */
    public void retarGimnasio(Scanner scanner) {
        System.out.println("\n¿A cuál Líder deseas retar??");
        
        for (int i = 0; i < listaGimnasios.size(); i++) {
            Gimnasio g = listaGimnasios.get(i);
            System.out.println((i + 1) + ") " + g.getNombreLider() + " - Estado: " + g.getEstado());
        }
        System.out.println((listaGimnasios.size() + 1) + ") Volver al menu.");
        System.out.print("\nIngrese Opcion: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            
            if (opcion == listaGimnasios.size() + 1) return;
            if (opcion < 1 || opcion > listaGimnasios.size()) {
                System.out.println("Opción fuera de rango.");
                return;
            }

            Gimnasio gymElegido = listaGimnasios.get(opcion - 1);

            if (gymElegido.getEstado().equals("Derrotado")) {
                System.out.println("\n¡Ya derrotaste a este líder!");
                return;
            }

            if (opcion > 1) {
                Gimnasio gymAnterior = listaGimnasios.get(opcion - 2);
                if (!gymAnterior.getEstado().equalsIgnoreCase("Derrotado")) {
                    System.out.println("\nCalmado Entrenador!!! No puedes retar a " + gymElegido.getNombreLider() + " sin haber derrotado a los lideres anteriores!!");
                    return;
                }
            }

            if (!tieneEquipoVivo()) {
                System.out.println("\n¡No tienes Pokémon en condiciones para luchar! Ve a curarlos al menú principal.");
                return;
            }

            System.out.println("\nDesafiando a " + gymElegido.getNombreLider() + "!!");
            iniciarBatallaGimnasio(gymElegido, scanner);

        } catch (Exception e) {
            System.out.println("Error: Por favor, ingrese un número.");
        }
    }

    /**
     * Inicia y controla una batalla contra un líder de gimnasio.
     * 
     * El combate permite atacar, cambiar Pokémon o rendirse. Los resultados
     * se determinan comparando estadísticas totales modificadas por la
     * efectividad de tipos.
     * 
     * @param gym Gimnasio cuyo líder será enfrentado.
     * @param scanner Scanner utilizado para leer acciones del jugador.
     */
    private void iniciarBatallaGimnasio(Gimnasio gym, Scanner scanner) {
        int indexLider = 0;
        Pokemon pkmnLider = gym.getEquipoLider().get(indexLider);
        Pokemon pkmnJugador = obtenerPrimerVivoEquipo();
        boolean enBatalla = true;

        while (enBatalla) {
            System.out.println("\n" + gym.getNombreLider() + " saca a " + pkmnLider.getNombre() + "!");
            System.out.println(jugadorActual.getNombre() + " saca a " + pkmnJugador.getNombre() + "!");

            boolean turnoActivo = true;
            while (turnoActivo) {
                System.out.println("\n¿Qué deseas hacer?");
                System.out.println("1) Atacar");
                System.out.println("2) Cambiar de pokemon");
                System.out.println("3) Rendirse");
                System.out.print("Ingrese Opcion: ");

                try {
                    int accion = Integer.parseInt(scanner.nextLine());
                    switch (accion) {
                        case 1:
                            System.out.println("\n" + pkmnJugador.getNombre() + " -> " + pkmnJugador.getSumaStats() + " puntos");
                            System.out.println(pkmnLider.getNombre() + " -> " + pkmnLider.getSumaStats() + " puntos");

                            double multJugador = TablaTipos.obtenerEfectividad(pkmnJugador.getTipo(), pkmnLider.getTipo());
                            double multLider = TablaTipos.obtenerEfectividad(pkmnLider.getTipo(), pkmnJugador.getTipo());

                            int pFinalJugador = (int) (pkmnJugador.getSumaStats() * multJugador);
                            int pFinalLider = (int) (pkmnLider.getSumaStats() * multLider);

                            if (multJugador == 2.0) System.out.println("\n¡" + pkmnJugador.getNombre() + " es muy efectivo contra " + pkmnLider.getNombre() + "!");
                            else if (multJugador == 0.5) System.out.println("\n" + pkmnJugador.getNombre() + " no es efectivo contra " + pkmnLider.getNombre() + "!");
                            else if (multJugador == 0.0) System.out.println("\n¡" + pkmnJugador.getNombre() + " no afecta a " + pkmnLider.getNombre() + "!");

                            if (multLider == 2.0) System.out.println("¡" + pkmnLider.getNombre() + " es muy efectivo contra " + pkmnJugador.getNombre() + "!");
                            else if (multLider == 0.5) System.out.println(pkmnLider.getNombre() + " no es efectivo contra " + pkmnJugador.getNombre() + "!");
                            else if (multLider == 0.0) System.out.println("¡" + pkmnLider.getNombre() + " no afecta a " + pkmnJugador.getNombre() + "!");

                            System.out.println("Nuevo puntaje:");
                            System.out.println(pkmnJugador.getNombre() + " -> " + pFinalJugador + " puntos");
                            System.out.println(pkmnLider.getNombre() + " -> " + pFinalLider + " puntos");

                            if (pFinalJugador >= pFinalLider) {
                                System.out.println("\nHa ganado " + pkmnJugador.getNombre() + "! " + pkmnLider.getNombre() + " ha sido derrotado...");
                                indexLider++;
                                if (indexLider >= gym.getEquipoLider().size()) {
                                    System.out.println("\n¡Felicidades! Has derrotado al Líder " + gym.getNombreLider() + ".");
                                    gym.setEstado("Derrotado");
                                    jugadorActual.setMedallas(gym.getNombreLider());
                                    enBatalla = false;
                                    turnoActivo = false;
                                } else {
                                    pkmnLider = gym.getEquipoLider().get(indexLider);
                                    turnoActivo = false;
                                }
                            } else {
                                System.out.println("\nHa ganado " + pkmnLider.getNombre() + "! " + pkmnJugador.getNombre() + " ha sido derrotado...");
                                pkmnJugador.setEstado("Debilitado");
                                if (!tieneEquipoVivo()) {
                                    System.out.println("\nTe has quedado sin pokemons en tu equipo!");
                                    System.out.println("Volviendo al menu...");
                                    enBatalla = false;
                                    turnoActivo = false;
                                } else {
                                    pkmnJugador = cambiarPokemonBatalla(scanner);
                                    turnoActivo = false;
                                }
                            }
                            break;
                        case 2:
                            Pokemon nuevo = cambiarPokemonBatalla(scanner);
                            if (nuevo != null && nuevo != pkmnJugador) {
                                pkmnJugador = nuevo;
                                turnoActivo = false;
                            }
                            break;
                        case 3:
                            System.out.println("\nTe has rendido. ¡Vuelve cuando seas más fuerte!");
                            enBatalla = false;
                            turnoActivo = false;
                            break;
                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Ingrese un número válido.");
                }
            }
        }
    }

    /**
     * Busca el primer Pokémon vivo dentro de los primeros seis Pokémon
     * del jugador, considerados como su equipo principal.
     * 
     * @return Primer Pokémon vivo encontrado o null si no existe ninguno.
     */
    private Pokemon obtenerPrimerVivoEquipo() {
        int limite = Math.min(jugadorActual.getListaPokemon().size(), 6);
        for (int i = 0; i < limite; i++) {
            Pokemon p = jugadorActual.getListaPokemon().get(i);
            if (p.getEstado().equalsIgnoreCase("Vivo")) return p;
        }
        return null;
    }

    /**
     * Permite al jugador cambiar de Pokémon durante una batalla.
     * 
     * Muestra los Pokémon del equipo principal y permite seleccionar
     * uno que no esté debilitado.
     * 
     * @param scanner Scanner utilizado para leer la selección del usuario.
     * @return Pokémon seleccionado o null si el jugador cancela la acción.
     */
    private Pokemon cambiarPokemonBatalla(Scanner scanner) {
        System.out.println("\nElige a tu siguiente Pokémon:");
        int limite = Math.min(jugadorActual.getListaPokemon().size(), 6);
        System.out.println("0) Cancelar / Volver"); // AÑADIDO
        for (int i = 0; i < limite; i++) {
            Pokemon p = jugadorActual.getListaPokemon().get(i);
            System.out.println((i + 1) + ") " + p.getNombre() + " - Estado: " + p.getEstado());
        }
        while (true) {
            System.out.print("Selecciona el número: ");
            try {
                int opc = Integer.parseInt(scanner.nextLine());
                if (opc == 0) return null;
                
                if (opc >= 1 && opc <= limite) {
                    Pokemon p = jugadorActual.getListaPokemon().get(opc - 1);
                    if (p.getEstado().equalsIgnoreCase("Debilitado")) {
                        System.out.println("¡Ese Pokémon está debilitado! Elige otro.");
                    } else {
                        return p;
                    }
                } else {
                    System.out.println("Número fuera de rango.");
                }
            } catch (Exception e) {
                System.out.println("Por favor, ingresa un número.");
            }
        }
    }

    /**
     * Verifica si el jugador tiene al menos un Pokémon vivo
     * dentro de su equipo principal.
     * 
     * @return true si existe al menos un Pokémon vivo, false en caso contrario.
     */
    private boolean tieneEquipoVivo() {
        if (jugadorActual.getListaPokemon().isEmpty()) return false;
        int limite = Math.min(jugadorActual.getListaPokemon().size(), 6);
        for (int i = 0; i < limite; i++) {
            if (jugadorActual.getListaPokemon().get(i).getEstado().equalsIgnoreCase("Vivo")) return true;
        }
        return false;
    }

    /**
     * Permite al jugador acceder al PC.
     * 
     * Desde esta opción puede revisar sus Pokémon y cambiar posiciones
     * entre ellos para organizar el equipo principal y los Pokémon almacenados.
     * 
     * @param scanner Scanner utilizado para leer las opciones del usuario.
     */
    public void accederPC(Scanner scanner) {
        if (jugadorActual == null || jugadorActual.getListaPokemon().isEmpty()) {
            System.out.println("\nAún no tienes Pokémon registrados en tu PC.");
            return;
        }
        ArrayList<Pokemon> lista = jugadorActual.getListaPokemon();
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- PC DE " + jugadorActual.getNombre().toUpperCase() + " ---");
            for (int i = 0; i < lista.size(); i++) {
                if (i == 0) System.out.println("[ EQUIPO PRINCIPAL ]");
                if (i == 6) System.out.println("\n[ ALMACENADOS EN EL PC ]");
                Pokemon p = lista.get(i);
                System.out.println((i + 1) + ") " + p.getNombre() + " - Estado: " + p.getEstado());
            }
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1) Cambiar Pokémon");
            System.out.println("2) Salir");
            System.out.print("Ingrese Opción: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion == 2) {
                    salir = true;
                    System.out.println("Cerrando el sistema del PC...");
                } else if (opcion == 1) {
                    if (lista.size() < 2) {
                        System.out.println("Necesitas tener al menos 2 Pokémon para hacer un cambio.");
                        continue;
                    }
                    System.out.print("Ingrese el número del primer Pokémon: ");
                    int pos1 = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.print("Ingrese el número del segundo Pokémon: ");
                    int pos2 = Integer.parseInt(scanner.nextLine()) - 1;
                    boolean exito = jugadorActual.cambiarPosicion(pos1, pos2);
                    if (exito) System.out.println("\n¡Cambio realizado con éxito!");
                    else System.out.println("Error: Uno de los números ingresados está fuera de rango.");
                }
            } catch (Exception e) {
                System.out.println("Error: Por favor, ingrese solo números.");
            }
        }
    }

    /**
     * Permite al jugador salir a capturar Pokémon en una zona disponible.
     * 
     * El Pokémon salvaje se selecciona usando el porcentaje de aparición
     * de los Pokémon registrados en el hábitat elegido.
     * 
     * @param scanner Scanner utilizado para leer las opciones del usuario.
     */
    public void salirACapturar(Scanner scanner) {
        System.out.println("\n¿Donde deseas ir a explorar?");
        System.out.println("\nZonas disponibles:");
        for (int i = 0; i < listaHabitats.size(); i++) {
            System.out.println((i + 1) + ") " + listaHabitats.get(i));
        }
        System.out.println((listaHabitats.size() + 1) + ") Volver al menu.");
        System.out.print("\nIngrese Zona: ");
        try {
            int opcionZona = Integer.parseInt(scanner.nextLine());
            if (opcionZona == listaHabitats.size() + 1) return;
            if (opcionZona < 1 || opcionZona > listaHabitats.size()) {
                System.out.println("Esa zona no existe.");
                return;
            }
            String zonaElegida = listaHabitats.get(opcionZona - 1);
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
            double numeroAleatorio = Math.random() * sumaProbabilidades;
            double acumulado = 0.0;
            Pokemon salvaje = posibles.get(posibles.size() - 1);
            for (Pokemon p : posibles) {
                acumulado += p.getPorcentajeAparicion();
                if (numeroAleatorio <= acumulado) {
                    salvaje = p;
                    break;
                }
            }
            System.out.println("\nOh!! Ha aparecido un increible " + salvaje.getNombre() + "!!");
            System.out.println("\nQue deseas hacer?");
            System.out.println("1) Capturar");
            System.out.println("2) Huir");
            System.out.print("\nIngrese Opcion: ");
            int accion = Integer.parseInt(scanner.nextLine());
            if (accion == 1) {
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
                    Pokemon nuevoCapturado = new Pokemon(salvaje, "Vivo");
                    jugadorActual.getListaPokemon().add(nuevoCapturado);
                    System.out.println("\n" + salvaje.getNombre() + " capturado con exito!!");
                    if (jugadorActual.getListaPokemon().size() <= 6) System.out.println(salvaje.getNombre() + " ha sido agregado a tu equipo!");
                    else System.out.println(salvaje.getNombre() + " ha sido enviado al PC!");
                }
            } else if (accion == 2) System.out.println("\nHas huido sin problemas.");
            else System.out.println("\nOpción inválida. El Pokémon se ha asustado y huyó.");
        } catch (Exception e) {
            System.out.println("Error de entrada. Volviendo al menú...");
        }
    }

    /**
     * Muestra los Pokémon actuales del equipo principal del jugador.
     * 
     * Solo se muestran los primeros seis Pokémon de la lista,
     * correspondientes al equipo activo.
     */
    public void revisarEquipo() {
        if (jugadorActual == null || jugadorActual.getListaPokemon().isEmpty()) {
            System.out.println("\nTu equipo está vacío. ¡Sal a explorar y capturar Pokémon!");
            return;
        }
        System.out.println("\nEquipo Actual:");
        ArrayList<Pokemon> lista = jugadorActual.getListaPokemon();
        int limite = Math.min(lista.size(), 6);
        for (int i = 0; i < limite; i++) {
            Pokemon p = lista.get(i);
            System.out.println((i + 1) + ") " + p.getNombre() + "|" + p.getTipo() + "|Stats totales: " + p.getSumaStats() + " [" + p.getEstado() + "]");
        }
    }

    /**
     * Cura todos los Pokémon del jugador.
     * 
     * Cambia el estado de todos los Pokémon registrados a "Vivo".
     */
    public void curarPokemones() {
        if (jugadorActual.getListaPokemon().isEmpty()) System.out.println("\nNo hay Pokemones que curar!");
        else {
            if (jugadorActual != null) {
                for (Pokemon p : jugadorActual.getListaPokemon()) p.setEstado("Vivo");
                System.out.println("¡Tus Pokémon han sido curados! Todos están en estado: Vivo.");
            } else System.out.println("Error: No hay un jugador activo.");
        }
    }

    /**
     * Carga la información de los integrantes del Alto Mando desde archivo.
     * 
     * Lee el archivo "Alto Mando.txt", crea los objetos AltoMando
     * y agrega sus Pokémon correspondientes desde la Pokedex maestra.
     */
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

    /**
     * Carga una partida guardada desde el archivo "Registros.txt".
     * 
     * Reconstruye al jugador actual, sus medallas y la lista de Pokémon
     * guardados junto con sus respectivos estados.
     * 
     * @return true si la partida fue cargada correctamente, false si ocurre un error.
     */
    public boolean cargarPartida() {
        File file = new File("Registros.txt");
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
               
                if (!jugadorActual.getMedallas().equalsIgnoreCase("none")) {
                    for (Gimnasio g : listaGimnasios) {
                        g.setEstado("Derrotado");
                        if (g.getNombreLider().equalsIgnoreCase(jugadorActual.getMedallas())) {
                            break;
                        }
                    }
                }
                
                while (scanner.hasNextLine()) {
                    String lineaPkmn = scanner.nextLine();
                    if (!lineaPkmn.trim().isEmpty()) {
                        String[] datosPkmn = lineaPkmn.split(";");
                        String nombrePkmn = datosPkmn[0];
                        String estadoPkmn = datosPkmn[1];
                        Pokemon pMaestro = buscarEnPokedex(nombrePkmn);
                        if (pMaestro != null) {
                            Pokemon pGuardado = new Pokemon(pMaestro, estadoPkmn);
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

    /**
     * Carga la Pokedex maestra desde el archivo "Pokedex.txt".
     * 
     * Cada línea del archivo representa un Pokémon con sus datos,
     * estadísticas, hábitat, porcentaje de aparición y tipo.
     */
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

    /**
     * Carga la lista de hábitats disponibles desde el archivo "Habitats.txt".
     * 
     * Cada línea no vacía del archivo se agrega a la lista de hábitats.
     */
    private void cargarHabitats() {
        try {
            File file = new File("Habitats.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                if (!linea.isEmpty()) listaHabitats.add(linea);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error al leer Habitats: " + e.getMessage());
        }
    }

    /**
     * Busca un Pokémon dentro de la Pokedex maestra por su nombre.
     * 
     * @param nombre Nombre del Pokémon que se desea buscar.
     * @return Pokémon encontrado o null si no existe en la Pokedex.
     */
    public Pokemon buscarEnPokedex(String nombre) {
        for (Pokemon p : pokedexMaestra) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    /**
     * Carga los gimnasios desde el archivo "Gimnasios.txt".
     * 
     * Crea cada gimnasio con su número, líder, estado y equipo Pokémon.
     */
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

    /**
     * Obtiene la lista de hábitats disponibles.
     * 
     * @return Lista de hábitats.
     */
    public ArrayList<String> getListaHabitats() { return listaHabitats; }

    /**
     * Obtiene el jugador actual del sistema.
     * 
     * @return Jugador actual.
     */
    public Jugador getJugadorActual() { return jugadorActual; }

    /**
     * Define el jugador actual del sistema.
     * 
     * @param jugador Jugador que será asignado como jugador actual.
     */
    public void setJugadorActual(Jugador jugador) { this.jugadorActual = jugador; }

    /**
     * Obtiene la lista de gimnasios cargados en el sistema.
     * 
     * @return Lista de gimnasios.
     */
    public ArrayList<Gimnasio> getListaGimnasios() { return listaGimnasios; }

    /**
     * Guarda la partida actual en el archivo "Registros.txt".
     * 
     * Registra el nombre del jugador, sus medallas y todos los Pokémon
     * capturados junto con su estado actual.
     */
    public void guardarPartida() {
        if (jugadorActual == null) {
            System.out.println("No hay ninguna partida activa para guardar.");
            return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Registros.txt"));
            writer.write(jugadorActual.getNombre() + ";" + jugadorActual.getMedallas());
            writer.newLine();
            for (Pokemon p : jugadorActual.getListaPokemon()) {
                writer.write(p.getNombre() + ";" + p.getEstado());
                writer.newLine();
            }
            writer.close();
            System.out.println("¡Partida guardada con éxito!");
        } catch (IOException e) {
            System.out.println("Error al intentar guardar la partida: " + e.getMessage());
        }
    }
}