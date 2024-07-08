import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Esta clase representa la interfaz gráfica principal de la aplicación.
 * Proporciona una ventana de inicio de sesión para los usuarios.
 */
public class MainFrame extends JFrame {
    private JTextField tfRut;
    private JButton btnIs;
    private JButton btnCp;
    private JPasswordField tfContraseña;
    private JPanel mainPanel;
    private String loggedInUserRut;

    /**
     * Constructor de la clase MainFrame.
     * Inicializa la interfaz gráfica y configura los listeners para los botones.
     */
    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Bienvenido");
        setSize(450, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btnIs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LecturaArchivos lectura = LecturaArchivos.obtenerInstancia();
                ArrayList<Usuario> listaUsuario = new ArrayList<>();
                lectura.leerArchivoUsuarios(listaUsuario);
                ingresar(listaUsuario);
            }
        });

        btnCp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    /**
     * Realiza el ingreso de un usuario.
     * Verifica las credenciales del usuario contra la lista de usuarios existente.
     * Si las credenciales son válidas, abre la ventana del menú principal.
     *
     * @param listaUsuario La lista de usuarios existente.
     */
    public void ingresar(ArrayList<Usuario> listaUsuario) {
        String rut = tfRut.getText().trim();
        String contraseña = new String(tfContraseña.getPassword());

        if (!rut.isEmpty() && !contraseña.isEmpty()) {
            for (Usuario u : listaUsuario) {
                if (u.getRut().equals(rut) && u.getContraseña().equals(contraseña)) {
                    loggedInUserRut = rut;
                    MenuPrincipal menu = new MenuPrincipal(loggedInUserRut);
                    menu.setVisible(true);
                    dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Rut o contraseña inválido.");
        } else {
            JOptionPane.showMessageDialog(this, "Campos inválidos, por favor vuelva a intentarlo.");
        }
    }

    /**
     * Cierra la aplicación.
     * Termina la ejecución del programa.
     */
    public void close() {
        System.exit(0);
    }
}