package Insurance;

/**
 * Parses command-line arguments for the insurance company communication system.
 * This class processes arguments in any order and stores them for validation and use.
 *
 * Supported arguments:
 * --email: Generate email messages
 * --email-template <file>: Path to email template file
 * --letter: Generate letters
 * --letter-template <file>: Path to letter template file
 * --csv-file <file>: Path to CSV file (required)
 * --output-dir <directory>: Output directory for generated files (required)
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class ArgumentParser {

    /** Flag indicating whether to generate email messages */
    private boolean generateEmail;

    /** Flag indicating whether to generate letters */
    private boolean generateLetter;

    /** Path to the email template file */
    private String emailTemplatePath;

    /** Path to the letter template file */
    private String letterTemplatePath;

    /** Path to the CSV file containing customer data */
    private String csvFilePath;

    /** Path to the output directory for generated files */
    private String outputDir;

    /** The original command-line arguments */
    private String[] args;

    /**
     * Constructs an ArgumentParser with the given command-line arguments.
     *
     * @param args The command-line arguments to parse
     */
    public ArgumentParser(String[] args) {
        this.args = args;
        this.generateEmail = false;
        this.generateLetter = false;
        this.emailTemplatePath = null;
        this.letterTemplatePath = null;
        this.csvFilePath = null;
        this.outputDir = null;
    }

    /**
     * Parses the command-line arguments and populates the fields.
     * Arguments can be provided in any order.
     *
     * @throws InvalidArgumentException if arguments are malformed or incomplete
     */
    public void parse() throws InvalidArgumentException { // May not need 6 args always
        int i = 0;
        while (i < args.length) {
            String arg = args[i];

            switch (arg) {
                case "--email":
                    generateEmail = true;
                    i++;
                    break;

                case "--email-template":
                    if (i + 1 >= args.length) {
                        throw new InvalidArgumentException(
                                "--email-template requires a file path argument");
                    }
                    emailTemplatePath = args[i + 1];
                    i += 2;
                    break;

                case "--letter":
                    generateLetter = true;
                    i++;
                    break;

                case "--letter-template":
                    if (i + 1 >= args.length) {
                        throw new InvalidArgumentException(
                                "--letter-template requires a file path argument");
                    }
                    letterTemplatePath = args[i + 1];
                    i += 2;
                    break;

                case "--csv-file":
                    if (i + 1 >= args.length) {
                        throw new InvalidArgumentException(
                                "--csv-file requires a file path argument");
                    }
                    csvFilePath = args[i + 1];
                    i += 2;
                    break;

                case "--output-dir":
                    if (i + 1 >= args.length) {
                        throw new InvalidArgumentException(
                                "--output-dir requires a directory path argument");
                    }
                    outputDir = args[i + 1];
                    i += 2;
                    break;

                default:
                    throw new InvalidArgumentException(
                            "Unknown argument: " + arg);
            }
        }
    }

    /**
     * Gets whether email generation was requested.
     *
     * @return true if --email flag was provided, false otherwise
     */
    public boolean isGenerateEmail() {
        return generateEmail;
    }

    /**
     * Gets whether letter generation was requested.
     *
     * @return true if --letter flag was provided, false otherwise
     */
    public boolean isGenerateLetter() {
        return generateLetter;
    }

    /**
     * Gets the path to the email template file.
     *
     * @return the email template file path, or null if not provided
     */
    public String getEmailTemplatePath() {
        return emailTemplatePath;
    }

    /**
     * Gets the path to the letter template file.
     *
     * @return the letter template file path, or null if not provided
     */
    public String getLetterTemplatePath() {
        return letterTemplatePath;
    }

    /**
     * Gets the path to the CSV file.
     *
     * @return the CSV file path, or null if not provided
     */
    public String getCsvFilePath() {
        return csvFilePath;
    }

    /**
     * Gets the output directory path.
     *
     * @return the output directory path, or null if not provided
     */
    public String getOutputDir() {
        return outputDir;
    }
}