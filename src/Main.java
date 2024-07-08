/**
 * Clase principal que inicia la aplicación.
 */
public class Main {

    /**
     * Punto de entrada principal de la aplicación.
     * Este método crea una instancia de SistemaImpl y llama a su método MainFrame.
     *
     * @param args Argumentos de línea de comando (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {
        SistemaImpl s = new SistemaImpl();
        s.MainFrame();
    }
}