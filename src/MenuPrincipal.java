import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase representa la interfaz gráfica del menú principal de la aplicación.
 * Proporciona opciones para realizar transferencias, visualizar transferencias y cerrar sesión.
 */
public class MenuPrincipal extends JFrame {
    private JButton realizarTransferenciaButton;
    private JButton visualizarMisTransferenciasButton;
    private JPanel menu;
    private JButton cerrarSesionButton;
    private String loggedInUserRut;

    /**
     * Constructor de la clase MenuPrincipal.
     * Inicializa la interfaz gráfica del menú principal y configura los listeners para los botones.
     *
     * @param loggedInUserRut El RUT del usuario que ha iniciado sesión.
     */
    public MenuPrincipal(String loggedInUserRut) {
        this.loggedInUserRut = loggedInUserRut;
        setContentPane(menu);
        setTitle("Menu");
        setSize(450,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Configuración de los listeners para los botones

        // Listener para el botón de cerrar sesión
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame inicio = new MainFrame();
                dispose();
            }
        });

        // Listener para el botón de realizar transferencia
        realizarTransferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RealizarTransferencia realizarTransferencia = new RealizarTransferencia(loggedInUserRut);
                realizarTransferencia.setVisible(true);
                dispose();
            }
        });

        // Listener para el botón de visualizar transferencias
        visualizarMisTransferenciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizarTransferencia visualizarTransferencia = new VisualizarTransferencia(loggedInUserRut);
                dispose();
            }
        });
    }
}