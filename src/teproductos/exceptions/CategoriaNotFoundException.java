package teproductos.exceptions;

/**
 *
 * @author igor
 */
public class CategoriaNotFoundException extends Exception {

    public CategoriaNotFoundException() {
    }

    /**
     * Constructs an instance of <code>CategoriaNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CategoriaNotFoundException(String msg) {
        super(msg);
    }
}
