package logica;

/**
 * Clase que representa a un Pokémon dentro del sistema.
 * 
 * Almacena su información principal, como nombre, hábitat,
 * porcentaje de aparición, estadísticas de combate, tipo y estado.
 */
public class Pokemon {
    private String nombre;
    private String habitat;
    private double porcentajeAparicion;
    private int vida;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;
    private String tipo;
    private String estado; // "Vivo" o "Debilitado"

    /**
     * Constructor utilizado para crear Pokémon desde la Pokedex.
     * 
     * El estado del Pokémon se asigna automáticamente como "Vivo".
     * 
     * @param nombre Nombre del Pokémon.
     * @param habitat Zona o hábitat donde puede aparecer.
     * @param porcentajeAparicion Probabilidad de aparición del Pokémon.
     * @param vida Estadística de vida.
     * @param ataque Estadística de ataque físico.
     * @param defensa Estadística de defensa física.
     * @param ataqueEspecial Estadística de ataque especial.
     * @param defensaEspecial Estadística de defensa especial.
     * @param velocidad Estadística de velocidad.
     * @param tipo Tipo elemental del Pokémon.
     */
    // Constructor para lectura de Pokedex (Estado siempre "Vivo")
    public Pokemon(String nombre, String habitat, double porcentajeAparicion, int vida, int ataque, 
                   int defensa, int ataqueEspecial, int defensaEspecial, int velocidad, String tipo) {
        this.nombre = nombre;
        this.habitat = habitat;
        this.porcentajeAparicion = porcentajeAparicion;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
        this.tipo = tipo;
        this.estado = "Vivo";
    }

    /**
     * Constructor utilizado para crear una copia de un Pokémon existente,
     * asignándole un estado específico.
     * 
     * Se usa principalmente al cargar una partida guardada o al capturar
     * un Pokémon desde la Pokedex maestra.
     * 
     * @param p Pokémon base desde el cual se copian los datos.
     * @param estado Estado que tendrá el nuevo Pokémon.
     */
    // Constructor para carga de partida (Recibe el estado desde Registros.txt)
    public Pokemon(Pokemon p, String estado) {
        this.nombre = p.getNombre();
        this.habitat = p.getHabitat();
        this.porcentajeAparicion = p.getPorcentajeAparicion();
        this.vida = p.getVida();
        this.ataque = p.getAtaque();
        this.defensa = p.getDefensa();
        this.ataqueEspecial = p.getAtaqueEspecial();
        this.defensaEspecial = p.getDefensaEspecial();
        this.velocidad = p.getVelocidad();
        this.tipo = p.getTipo();
        this.estado = estado;
    }

    /**
     * Calcula la suma total de las estadísticas principales del Pokémon.
     * 
     * @return Suma de vida, ataque, defensa, ataque especial,
     * defensa especial y velocidad.
     */
    public int getSumaStats() {
        return vida + ataque + defensa + ataqueEspecial + defensaEspecial + velocidad;
    }

    // Getters

    /**
     * Obtiene el nombre del Pokémon.
     * 
     * @return Nombre del Pokémon.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el hábitat del Pokémon.
     * 
     * @return Hábitat del Pokémon.
     */
    public String getHabitat() { return habitat; }

    /**
     * Obtiene el porcentaje de aparición del Pokémon.
     * 
     * @return Porcentaje de aparición.
     */
    public double getPorcentajeAparicion() { return porcentajeAparicion; }

    /**
     * Obtiene la estadística de vida del Pokémon.
     * 
     * @return Vida del Pokémon.
     */
    public int getVida() { return vida; }

    /**
     * Obtiene la estadística de ataque del Pokémon.
     * 
     * @return Ataque del Pokémon.
     */
    public int getAtaque() { return ataque; }

    /**
     * Obtiene la estadística de defensa del Pokémon.
     * 
     * @return Defensa del Pokémon.
     */
    public int getDefensa() { return defensa; }

    /**
     * Obtiene la estadística de ataque especial del Pokémon.
     * 
     * @return Ataque especial del Pokémon.
     */
    public int getAtaqueEspecial() { return ataqueEspecial; }

    /**
     * Obtiene la estadística de defensa especial del Pokémon.
     * 
     * @return Defensa especial del Pokémon.
     */
    public int getDefensaEspecial() { return defensaEspecial; }

    /**
     * Obtiene la estadística de velocidad del Pokémon.
     * 
     * @return Velocidad del Pokémon.
     */
    public int getVelocidad() { return velocidad; }

    /**
     * Obtiene el tipo elemental del Pokémon.
     * 
     * @return Tipo del Pokémon.
     */
    public String getTipo() { return tipo; }

    /**
     * Obtiene el estado actual del Pokémon.
     * 
     * @return Estado del Pokémon.
     */
    public String getEstado() { return estado; }

    // Setters

    /**
     * Modifica el estado actual del Pokémon.
     * 
     * @param estado Nuevo estado del Pokémon.
     */
    public void setEstado(String estado) { this.estado = estado; }
}