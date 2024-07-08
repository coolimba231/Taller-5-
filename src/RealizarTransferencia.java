import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Esta clase representa la interfaz gráfica para realizar transferencias.
 * Permite a los usuarios ingresar los detalles de una transferencia y ejecutarla.
 */
public class RealizarTransferencia extends JFrame {
    private JPanel realizarTransferencia;
    private JButton Depositar;
    private JButton volverButton;
    private JTextField campoRUT;
    private JTextField campoApodo;
    private JTextField campoMonto;
    private JTextField campoMensaje;
    private String rutRemitente;
    private TransferenciaManager transferenciaManager;

    /**
     * Constructor de la clase RealizarTransferencia.
     * Inicializa la interfaz gráfica y configura los listeners para los botones.
     *
     * @param rutRemitente El RUT del usuario que está realizando la transferencia.
     */
    public RealizarTransferencia(String rutRemitente) {
        this.rutRemitente = rutRemitente;
        this.transferenciaManager = TransferenciaManager.getInstance();

        setContentPane(realizarTransferencia);
        setTitle("Realizar Transferencia");
        setSize(450, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal menu = new MenuPrincipal(rutRemitente);
                dispose();
            }
        });

        Depositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LecturaArchivos lectura = LecturaArchivos.obtenerInstancia();
                ArrayList<Usuario> listaUsuario = new ArrayList<>();
                lectura.leerArchivoUsuarios(listaUsuario);
                deposito(listaUsuario);
            }
        });
    }

    /**
     * Realiza el proceso de depósito (transferencia) entre usuarios.
     * Valida los datos ingresados, actualiza los saldos, guarda la transferencia
     * y notifica a los usuarios involucrados.
     *
     * @param listaUsuario La lista de todos los usuarios del sistema.
     */
    public void deposito(ArrayList<Usuario> listaUsuario) {
        String rutDestinatario = campoRUT.getText().trim();
        String apodo = campoApodo.getText().trim();
        String montoStr = campoMonto.getText().trim();
        String mensaje = campoMensaje.getText().trim();

        if (rutDestinatario.isEmpty() || apodo.isEmpty() || montoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos excepto el mensaje son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int monto;
        try {
            monto = Integer.parseInt(montoStr);
            if (monto <= 500) {
                JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 500 pesos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario remitente = null;
        Usuario destinatario = null;
        for (Usuario usuario : listaUsuario) {
            if (usuario.getRut().equals(rutRemitente)) {
                remitente = usuario;
            } else if (usuario.getRut().equals(rutDestinatario)) {
                destinatario = usuario;
            }
        }

        if (remitente == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario remitente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (destinatario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario destinatario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (remitente.getMonto() < monto) {
            JOptionPane.showMessageDialog(this, "Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Agregar remitente y destinatario como observadores
        transferenciaManager.addObserver(remitente);
        transferenciaManager.addObserver(destinatario);

        // Realizar la transferencia
        transferenciaManager.realizarTransferencia(remitente, destinatario, monto, mensaje);

        // Guardar la transferencia en el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transferencias.csv", true))) {
            bw.write(rutRemitente + ";" + rutDestinatario + ";" + monto + ";" + mensaje);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la transferencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar los saldos en el archivo de usuarios
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientes.csv"))) {
            for (Usuario usuario : listaUsuario) {
                bw.write(usuario.getRut() + ";" + usuario.getNombre() + ";" + usuario.getMonto() + ";" + usuario.getContraseña());
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar los saldos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Remover observadores después de la notificación
        transferenciaManager.removeObserver(remitente);
        transferenciaManager.removeObserver(destinatario);

        JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        MenuPrincipal menu = new MenuPrincipal(rutRemitente);
        dispose();
    }
}