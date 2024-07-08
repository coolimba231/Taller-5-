import java.io.*;
import java.util.ArrayList;

/**
 * Clase encargada de leer archivos CSV relacionados con transferencias y usuarios.
 * Esta clase implementa el patrón Singleton para asegurar una única instancia.
 */
public class LecturaArchivos {
    /** Instancia única de LecturaArchivos */
    private static LecturaArchivos instancia = null;

    /**
     * Constructor privado para evitar instanciación directa.
     */
    private LecturaArchivos() {}

    /**
     * Método estático para obtener la instancia única (Singleton).
     *
     * @return La instancia única de LecturaArchivos.
     */
    public static LecturaArchivos obtenerInstancia() {
        if (instancia == null) {
            instancia = new LecturaArchivos();
        }
        return instancia;
    }

    /**
     * Método encargado de leer el archivo "transferencias.csv".
     *
     * @param transferencias Lista de transferencias a la que se añadirán las nuevas transferencias leídas.
     * @return ArrayList actualizado con las transferencias leídas del archivo.
     */
    public ArrayList<Transferencias> leerArchivoTransferencias(ArrayList<Transferencias> transferencias) {
        try (BufferedReader br = new BufferedReader(new FileReader("transferencias.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] chain = line.split(";");
                if (chain.length >= 4) { // Verificación de la longitud del array
                    String rutRemitente = chain[0];
                    String rutDestinatario = chain[1];
                    int monto = Integer.parseInt(chain[2]);
                    String mensaje = chain[3];

                    Transferencias transferencia = new Transferencias.TransferenciasBuilder(rutRemitente, rutDestinatario, monto)
                            .mensaje(mensaje)
                            .build();
                    transferencias.add(transferencia);
                } else {
                    System.out.println("Línea inválida en transferencias.csv: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de transferencias: " + e.getMessage());
        }
        return transferencias;
    }

    /**
     * Método encargado de leer el archivo "clientes.csv".
     *
     * @param usuarios Lista de usuarios a la que se añadirán los nuevos usuarios leídos.
     * @return ArrayList actualizado con los usuarios leídos del archivo.
     */
    public ArrayList<Usuario> leerArchivoUsuarios(ArrayList<Usuario> usuarios) {
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignorar líneas vacías
                }
                String[] chain = line.split(";");
                if (chain.length >= 4) {
                    Usuario usuario = new Usuario.UsuarioBuilder(chain[0])
                            .nombre(chain[1])
                            .monto(Integer.parseInt(chain[2]))
                            .contraseña(chain[3])
                            .build();
                    usuarios.add(usuario);
                } else {
                    System.out.println("Línea inválida en clientes.csv: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de clientes: " + e.getMessage());
        }
        return usuarios;
    }
}