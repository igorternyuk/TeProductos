package teproductos.exceptions;

/**
 *
 * @author igor
 */
public class ProductoNotFoundException extends Exception {

    public ProductoNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ProductoNoFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ProductoNotFoundException(String msg) {
        super(msg);
    }
}
