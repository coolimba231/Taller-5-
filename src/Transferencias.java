/**
 * Representa una transferencia bancaria entre dos usuarios.
 * Esta clase utiliza el patrón Builder para su construcción.
 */
public class Transferencias {
    private final String rutRemitente;
    private final String rutDestinatario;
    private final int monto;
    private final String mensaje;

    /**
     * Constructor privado que inicializa una transferencia a partir de un builder.
     *
     * @param builder El builder que contiene los datos de la transferencia.
     */
    private Transferencias(TransferenciasBuilder builder) {
        this.rutRemitente = builder.rutRemitente;
        this.rutDestinatario = builder.rutDestinatario;
        this.monto = builder.monto;
        this.mensaje = builder.mensaje;
    }

    /**
     * @return El RUT del remitente de la transferencia.
     */
    public String getRutRemitente() {
        return rutRemitente;
    }

    /**
     * @return El RUT del destinatario de la transferencia.
     */
    public String getRutDestinatario() {
        return rutDestinatario;
    }

    /**
     * @return El monto de la transferencia.
     */
    public int getMonto() {
        return monto;
    }

    /**
     * @return El mensaje asociado a la transferencia, si existe.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Clase Builder para construir objetos Transferencias.
     */
    public static class TransferenciasBuilder {
        private String rutRemitente;
        private String rutDestinatario;
        private int monto;
        private String mensaje;

        /**
         * Constructor del Builder que inicializa los campos obligatorios.
         *
         * @param rutRemitente El RUT del remitente.
         * @param rutDestinatario El RUT del destinatario.
         * @param monto El monto de la transferencia.
         */
        public TransferenciasBuilder(String rutRemitente, String rutDestinatario, int monto) {
            this.rutRemitente = rutRemitente;
            this.rutDestinatario = rutDestinatario;
            this.monto = monto;
        }

        /**
         * Establece el mensaje opcional de la transferencia.
         *
         * @param mensaje El mensaje a asociar con la transferencia.
         * @return El builder actualizado.
         */
        public TransferenciasBuilder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        /**
         * Construye y devuelve un nuevo objeto Transferencias.
         *
         * @return Una nueva instancia de Transferencias.
         */
        public Transferencias build() {
            return new Transferencias(this);
        }
    }
}