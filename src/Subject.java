/**
 * Interfaz que define el contrato para objetos que actúan como sujetos observables.
 * Esta interfaz es parte del patrón de diseño Observer y permite a los objetos
 * registrar, eliminar y notificar a sus observadores.
 */
public interface Subject {

    /**
     * Añade un observador a la lista de observadores del sujeto.
     *
     * @param o El observador a añadir.
     */
    void addObserver(Observer o);

    /**
     * Elimina un observador de la lista de observadores del sujeto.
     *
     * @param o El observador a eliminar.
     */
    void removeObserver(Observer o);

    /**
     * Notifica a todos los observadores registrados sobre un cambio.
     * Este método típicamente llama al método update() de cada observador.
     *
     * @param message El mensaje que se enviará a todos los observadores,
     *                conteniendo información sobre el cambio ocurrido.
     */
    void notifyObservers(String message);
}