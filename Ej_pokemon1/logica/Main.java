package logica;

import java.util.Scanner;

public class Main {
	
    // Angel Eduardo Olivares Flores
    // 22.338.590-7 / ICCI

    // Jason Alexander Tapia Castro
    // 22.382.028-K / ICCI
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
                 case 1:
                	 if (sistema.cargarPartida()) { // Fix ejecucón de menu secundario por if sin corchetes
                		 System.out.println("\n¡Bienvenido de vuelta, " + sistema.getJugadorActual().getNombre() + "!");
                		 menuSecundario(sistema, scanner);
                	 }
                     break;

                 case 2:
                     System.out.print("Ingrese su apodo de jugador: ");
                     String nombre = scanner.nextLine();
                     Jugador nuevoJugador = new Jugador(nombre, "none");
                     sistema.setJugadorActual(nuevoJugador);
                     System.out.println("\n¡Bienvenido " + nombre + "!");
                     
                     menuSecundario(sistema, scanner);
                     break;

                 case 3:
                	 
                     System.out.println("Nos vemos entrenador...");
                     break;

                 default:
                     System.out.println("Opcion no valida.");
                     break;
             }
         } catch (Exception e) {
             System.out.println("Error: Debe ingresar un numero.");
             opcion = 0; 
         }
     } while (opcion != 3);

     scanner.close();
 }
 
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
                 case 1:
                	 sistema.revisarEquipo();
                     break;
                 case 2:
                	 sistema.salirACapturar(scanner);
                     break;
				 case 3:
						
					 break;
				 case 4:
						
					 break;
				 case 5:
						
					 break;
				 case 6:
					 sistema.curarPokemones();
					 break;
                 case 7:
                     sistema.guardarPartida();
                     break;
                 case 8:
                     sistema.guardarPartida();
                     System.out.println("Nos vemos entrenador...");
                     System.exit(0); 
                     break;
                 default:
                     System.out.println("Opción no válida.");
                     break;
             }
         } catch (Exception e) {
             System.out.println("Error: Debe ingresar un número.");
             opcionSub = 0;
         }
     } while (opcionSub != 8);
 }
 
}
