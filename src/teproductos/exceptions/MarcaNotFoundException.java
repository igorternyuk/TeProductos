package teproductos.exceptions;

/**
 *
 * @author igor
 */
public class MarcaNotFoundException extends Exception {

    public MarcaNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MarcaNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MarcaNotFoundException(String msg) {
        super(msg);
    }
}
