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
                     System.out.println("Cargando partida...");
                     break;

                 case 2:
                     System.out.print("Ingrese su apodo de jugador: ");
                     String nombre = scanner.nextLine();
                     Jugador nuevoJugador = new Jugador(nombre, "none");
                     sistema.setJugadorActual(nuevoJugador);
                     System.out.println("¡Bienvenido " + nombre + "!");
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
 
 
}
