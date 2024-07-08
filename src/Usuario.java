/**
 * Representa un usuario del sistema bancario.
 * Esta clase implementa la interfaz Observer y utiliza el patrón Builder para su construcción.
 */
public class Usuario implements Observer {
    private String rut;
    private String nombre;
    private int monto;
    private String contraseña;

    /**
     * Constructor privado que inicializa un usuario a partir de un builder.
     *
     * @param builder El builder que contiene los datos del usuario.
     */
    private Usuario(UsuarioBuilder builder) {
        this.rut = builder.rut;
        this.nombre = builder.nombre;
        this.monto = builder.monto;
        this.contraseña = builder.contraseña;
    }

    /**
     * @return El RUT del usuario.
     */
    public String getRut() {
        return rut;
    }

    /**
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return El monto disponible del usuario.
     */
    public int getMonto() {
        return monto;
    }

    /**
     * @return La contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece el RUT del usuario.
     * @param rut El nuevo RUT.
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el monto disponible del usuario.
     * @param monto El nuevo monto.
     */
    public void setMonto(int monto) {
        this.monto = monto;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contraseña La nueva contraseña.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Método de la interfaz Observer que recibe notificaciones.
     *
     * @param message El mensaje de notificación recibido.
     */
    @Override
    public void update(String message) {
        System.out.println("Notificación para " + this.nombre + ": " + message);
    }

    /**
     * Clase Builder para construir objetos Usuario.
     */
    public static class UsuarioBuilder {
        private String rut;
        private String nombre;
        private int monto;
        private String contraseña;

        /**
         * Constructor del Builder que inicializa el RUT del usuario.
         *
         * @param rut El RUT del usuario.
         */
        public UsuarioBuilder(String rut) {
            this.rut = rut;
        }

        /**
         * Establece el nombre del usuario.
         *
         * @param nombre El nombre del usuario.
         * @return El builder actualizado.
         */
        public UsuarioBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        /**
         * Establece el monto disponible del usuario.
         *
         * @param monto El monto disponible del usuario.
         * @return El builder actualizado.
         */
        public UsuarioBuilder monto(int monto) {
            this.monto = monto;
            return this;
        }

        /**
         * Establece la contraseña del usuario.
         *
         * @param contraseña La contraseña del usuario.
         * @return El builder actualizado.
         */
        public UsuarioBuilder contraseña(String contraseña) {
            this.contraseña = contraseña;
            return this;
        }

        /**
         * Construye y devuelve un nuevo objeto Usuario.
         *
         * @return Una nueva instancia de Usuario.
         */
        public Usuario build() {
            return new Usuario(this);
        }
    }
}