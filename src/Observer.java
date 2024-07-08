/**
 * Interfaz que define el contrato para objetos que actúan como observadores.
 * Esta interfaz es parte del patrón de diseño Observer.
 */
public interface Observer {

    /**
     * Método llamado para notificar al observador de un cambio.
     * Este método es invocado por el sujeto observado cuando ocurre un evento de interés.
     *
     * @param message El mensaje que contiene la información sobre el cambio o evento ocurrido.
     */
    void update(String message);
}