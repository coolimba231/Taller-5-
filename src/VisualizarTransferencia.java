import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Esta clase representa la interfaz gráfica para visualizar las transferencias de un usuario.
 * Implementa la interfaz Observer para recibir notificaciones de nuevas transferencias.
 */
public class VisualizarTransferencia extends JFrame implements Observer {
    private JPanel ultimosMovimientos;
    private JButton volverButton;
    private JTextArea textArea1;
    private String loggedInUserRut;
    private TransferenciaManager transferenciaManager;

    /**
     * Constructor de la clase VisualizarTransferencia.
     * Inicializa la interfaz gráfica, configura el observer y carga las transferencias.
     *
     * @param loggedInUserRut El RUT del usuario cuyas transferencias se están visualizando.
     */
    public VisualizarTransferencia(String loggedInUserRut) {
        this.loggedInUserRut = loggedInUserRut;
        this.transferenciaManager = TransferenciaManager.getInstance();
        this.transferenciaManager.addObserver(this);

        setContentPane(ultimosMovimientos);
        setTitle("Visualizar Transferencias");
        setSize(450, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        cargarTransferencias();

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferenciaManager.removeObserver(VisualizarTransferencia.this);
                MenuPrincipal menu = new MenuPrincipal(loggedInUserRut);
                menu.setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Carga las últimas 5 transferencias del usuario desde el archivo CSV.
     * Muestra las transferencias en el área de texto de la interfaz.
     */
    private void cargarTransferencias() {
        List<String> transferencias = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("transferencias.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if ((parts.length == 3 || parts.length == 4) && (parts[0].equals(loggedInUserRut) || parts[1].equals(loggedInUserRut))) {
                    transferencias.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las transferencias", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Collections.reverse(transferencias);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String transferencia : transferencias) {
            if (count >= 5) break;
            String[] parts = transferencia.split(";", -1);
            String tipo = parts[0].equals(loggedInUserRut) ? "Transferencia enviada" : "Transferencia recibida";
            String remitente = parts[0];
            String destinatario = parts[1];
            String monto = parts[2];
            String mensaje = parts.length > 3 ? parts[3] : "(Sin mensaje)";

            sb.append("Tipo: ").append(tipo).append("\n");
            sb.append("Remitente: ").append(remitente).append("\n");
            sb.append("Destinatario: ").append(destinatario).append("\n");
            sb.append("Monto: $").append(monto).append("\n");
            sb.append("Mensaje: ").append(mensaje).append("\n\n");
            count++;
        }
        textArea1.setText(sb.toString());
    }

    /**
     * Método de la interfaz Observer que se llama cuando se realiza una nueva transferencia.
     * Recarga las transferencias y muestra un mensaje de notificación.
     *
     * @param message El mensaje de notificación sobre la nueva transferencia.
     */
    @Override
    public void update(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cargarTransferencias();
                JOptionPane.showMessageDialog(VisualizarTransferencia.this,
                        "Nueva transferencia realizada: " + message,
                        "Actualización", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}