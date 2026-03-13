package Insurance;

/**
 * Exception thrown when invalid command-line arguments are provided.
 * This exception is used to indicate problems with argument combinations,
 * missing required arguments, or invalid argument formats.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class InvalidArgumentException extends Exception {

    /**
     * Constructs a new InvalidArgumentException with the specified detail message.
     *
     * @param message The detail message explaining why the arguments are invalid
     */
    public InvalidArgumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidArgumentException with the specified detail message
     * and cause.
     *
     * @param message The detail message explaining why the arguments are invalid
     * @param cause The cause of this exception
     */
    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}