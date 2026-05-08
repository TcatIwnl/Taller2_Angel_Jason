# Simulador de Batallas Pokémon - Taller 2 POO

## Integrantes
* **Integrante 1:** Angel Eduardo Olivares Flores - 22.338.590-7 - TcatIwnl
* **Integrante 2:** Jason Alexander Tapia Castro - 22.382.028-K - jtapia-code

**Carrera:** Ingeniería Civil en Computación e Informática  
**Sede:** Coquimbo, Universidad Católica del Norte (UCN)

---

## Descripción del Proyecto
Este proyecto es un sistema de simulación interactivo por consola basado en la franquicia Pokémon. Permite gestionar un equipo, capturar criaturas en distintos hábitats, intercambiar Pokémon mediante un PC y desafiar a los 8 Líderes de Gimnasio hasta llegar al Alto Mando.

## Estructura del Proyecto
El código fuente se encuentra organizado bajo el paquete `logica`, contenido en el directorio del proyecto:

* **Directorio de fuentes**: `Ej_pokemon1/`
* **`Main.java`**: Punto de entrada con los datos de identificación obligatorios.
* **`Sistema.java`**: Controlador principal del simulador (Lógica, Menús y Persistencia).
* **`Jugador.java`**: Entidad que gestiona el equipo, medallas y PC del usuario.
* **`Pokemon.java`**: Modelo de datos base de las criaturas (Stats, Tipo, Estado).
* **`Gimnasio.java`**: Clase que representa a los líderes y sus equipos.
* **`AltoMando.java`**: Clase que representa a los miembros del desafío final.
* **`TablaTipos.java`**: Matriz de efectividad de tipos.
* **Archivos .txt**: Localizados en la raíz para la persistencia y carga de datos.

## Instrucciones de Ejecución
1. **Requisitos:** JDK 17 o superior.
2. **Importación:** Importar la carpeta raíz en Eclipse como un proyecto existente. El IDE reconocerá automáticamente la carpeta `Ej_pokemon1` como el contenedor de los paquetes de código.
3. **Ejecución:** Ejecutar la clase `Main.java` para iniciar la consola interactiva.

## Diagramas
Los entregables en PDF se encuentran en la raíz del repositorio:
* `Modelo_Dominio_Taller2.pdf`
* `Diagrama_Clases_Taller2.pdf`

---
*Desarrollado para la asignatura Programación Orientada a Objetos 2026-I.*
