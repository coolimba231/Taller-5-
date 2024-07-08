import java.util.ArrayList;
import java.util.List;

/**
 * Administrador de transferencias que implementa el patrón Singleton y el patrón Observer.
 * Esta clase maneja las transferencias entre usuarios y notifica a los observadores sobre las transacciones.
 */
public class TransferenciaManager implements Subject {
    /** Instancia única de TransferenciaManager */
    private static TransferenciaManager instance;

    /** Lista de observadores registrados */
    private List<Observer> observers = new ArrayList<>();

    /**
     * Constructor privado para evitar instanciación directa.
     */
    private TransferenciaManager() {}

    /**
     * Método estático para obtener la única instancia de la clase.
     *
     * @return La instancia única de TransferenciaManager.
     */
    public static TransferenciaManager getInstance() {
        if (instance == null) {
            instance = new TransferenciaManager();
        }
        return instance;
    }
    /**
     * Añade un observador a la lista de observadores.
     *
     * @param o El observador a añadir.
     */
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Elimina un observador de la lista de observadores.
     *
     * @param o El observador a eliminar.
     */
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifica a todos los observadores registrados con un mensaje.
     *
     * @param message El mensaje a enviar a los observadores.
     */
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * Realiza una transferencia entre dos usuarios y notifica a los observadores.
     *
     * @param remitente El usuario que envía el dinero.
     * @param destinatario El usuario que recibe el dinero.
     * @param monto La cantidad de dinero a transferir.
     * @param mensaje Un mensaje opcional asociado con la transferencia.
     */
    public void realizarTransferencia(Usuario remitente, Usuario destinatario, int monto, String mensaje) {

        remitente.setMonto(remitente.getMonto() - monto);
        destinatario.setMonto(destinatario.getMonto() + monto);

        notifyObservers("Se ha realizado una transferencia de " + monto + " desde " + remitente.getNombre() + " a " + destinatario.getNombre());
    }
}