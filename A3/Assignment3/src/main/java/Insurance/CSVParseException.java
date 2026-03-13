package Insurance;

/**
 * Exception thrown when there is an error parsing a CSV file.
 * This exception indicates problems such as malformed CSV data,
 * invalid format, or inconsistent column counts.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class CSVParseException extends Exception {

    /**
     * Constructs a new CSVParseException with the specified detail message.
     *
     * @param message The detail message explaining the parsing error
     */
    public CSVParseException(String message) {
        super(message);
    }

    /**
     * Constructs a new CSVParseException with the specified detail message
     * and cause.
     *
     * @param message The detail message explaining the parsing error
     * @param cause The cause of this exception
     */
    public CSVParseException(String message, Throwable cause) {
        super(message, cause);
    }
}