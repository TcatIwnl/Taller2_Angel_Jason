// Angel Eduardo Olivares Flores
// 22.338.590-7 / ICCI

// Jason Alexander Tapia Castro
// 22.382.028-K / ICCI

package logica;

import java.util.Scanner;

/**
 * Clase principal encargada de iniciar la ejecución del programa.
 * 
 * Contiene el menú principal y el menú secundario del juego,
 * además de controlar la interacción inicial del usuario con el sistema.
 */
public class Main {
	

    /**
     * Método principal del programa.
     * 
     * Inicializa el sistema, el scanner y muestra el menú inicial
     * permitiendo al jugador cargar una partida, iniciar una nueva
     * o salir del programa.
     * 
     * @param args Argumentos enviados desde la línea de comandos.
     */
 public static void main(String[] args) {
    
     Scanner scanner = new Scanner(System.in);
     Sistema sistema = new Sistema(); 
     int opcion = 0;

     System.out.println("¡Bienvenido al simulador Pokémon UCN!");

     do {
         System.out.println("\n--- MENÚ INICIAL ---");
         System.out.println("1) Continuar");
         System.out.println("2) Nueva Partida");
         System.out.println("3) Salir");
         System.out.print("Ingrese Opcion: ");

         try {
             opcion = Integer.parseInt(scanner.nextLine());

             switch (opcion) {

                 /**
                  * Carga una partida previamente guardada.
                  */
                 case 1:
                 	 if (sistema.cargarPartida()) {
                 		 System.out.println("\n¡Bienvenido de vuelta, " + sistema.getJugadorActual().getNombre() + "!");
                 		 menuSecundario(sistema, scanner);
                 	 }
                     break;

                 /**
                  * Crea una nueva partida solicitando el nombre del jugador.
                  */
                 case 2:
                 	    String nombre;
                 	    do {
                 	        System.out.print("Ingrese su apodo de jugador: ");
                 	        nombre = scanner.nextLine().trim();

                 	        if (nombre.isEmpty()) {
                 	            System.out.println("El nombre no puede estar vacío. Inténtalo de nuevo.");
                 	        }

                 	    } while (nombre.isEmpty());

                 	    Jugador nuevoJugador = new Jugador(nombre, "none");
                 	    sistema.setJugadorActual(nuevoJugador);

                 	    System.out.println("\n¡Bienvenido " + nombre + "!");

                 	    menuSecundario(sistema, scanner);
                 	    break;

                 /**
                  * Finaliza la ejecución del programa.
                  */
                 case 3:
                 	
                     System.out.println("Nos vemos entrenador...");
                     break;

                 /**
                  * Maneja opciones inválidas del menú.
                  */
                 default:
                     System.out.println("Opcion no valida.");
                     break;
             }

         } catch (Exception e) {

             /**
              * Controla errores de ingreso de datos.
              */
             System.out.println("Error: Debe ingresar un numero.");
             opcion = 0; 
         }

     } while (opcion != 3);

     scanner.close();
 }
 
    /**
     * Método que muestra el menú principal del juego una vez iniciada
     * o cargada una partida.
     * 
     * Permite acceder a todas las funcionalidades disponibles del sistema.
     * 
     * @param sistema Sistema principal del juego.
     * @param scanner Scanner utilizado para leer entradas del usuario.
     */
 public static void menuSecundario(Sistema sistema, Scanner scanner) {

     int opcionSub = 0;

     do {

         System.out.println("\n" + sistema.getJugadorActual().getNombre() + ", ¿qué deseas hacer?");
         System.out.println("1) Revisar equipo");
         System.out.println("2) Salir a capturar");
         System.out.println("3) Acceso al PC");
         System.out.println("4) Retar un gimnasio");
         System.out.println("5) Desafío al Alto Mando");
         System.out.println("6) Curar Pokémon");
         System.out.println("7) Guardar");
         System.out.println("8) Guardar y Salir");
         
         System.out.print("Ingrese Opción: ");
         
         try {

             opcionSub = Integer.parseInt(scanner.nextLine());

             switch (opcionSub) {

                 /**
                  * Muestra el equipo actual del jugador.
                  */
                 case 1:
                 	 sistema.revisarEquipo();
                     break;

                 /**
                  * Permite explorar zonas y capturar Pokémon.
                  */
                 case 2:
                 	 sistema.salirACapturar(scanner);
                     break;

                 /**
                  * Permite acceder al PC del jugador.
                  */
				 case 3:
					 sistema.accederPC(scanner);
					 break;

                 /**
                  * Inicia el desafío a un gimnasio.
                  */
				 case 4:
					 sistema.retarGimnasio(scanner);
					 break;

                 /**
                  * Permite desafiar al Alto Mando.
                  */
				 case 5:
					 sistema.desafiarAltoMando(scanner);
					 break;

                 /**
                  * Cura todos los Pokémon del jugador.
                  */
				 case 6:
					 sistema.curarPokemones();
					 break;

                 /**
                  * Guarda la partida actual.
                  */
                 case 7:
                     sistema.guardarPartida();
                     break;

                 /**
                  * Guarda la partida y cierra el programa.
                  */
                 case 8:
                     sistema.guardarPartida();
                     System.out.println("Nos vemos entrenador...");
                     System.exit(0); 
                     break;

                 /**
                  * Maneja opciones inválidas.
                  */
                 default:
                     System.out.println("Opción no válida.");
                     break;
             }

         } catch (Exception e) {

             /**
              * Controla errores de ingreso de datos.
              */
             System.out.println("Error: Debe ingresar un número.");
             opcionSub = 0;
         }

     } while (opcionSub != 8);
 }
 
}