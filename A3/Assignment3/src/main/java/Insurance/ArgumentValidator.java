package Insurance;

/**
 * Validates command-line arguments parsed by ArgumentParser.
 * This class ensures that all required arguments are present and that
 * argument combinations are valid according to the program's requirements.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class ArgumentValidator {

    /**
     * Validates the parsed arguments to ensure they meet all requirements.
     *
     * Required validations:
     * - --csv-file must be provided
     * - --output-dir must be provided
     * - If --email is provided, --email-template must also be provided
     * - If --letter is provided, --letter-template must also be provided
     * - At least one of --email or --letter must be requested
     *
     * @param parser The ArgumentParser containing parsed arguments
     * @throws InvalidArgumentException if validation fails
     */
    public static void validate(ArgumentParser parser) throws InvalidArgumentException {
        // Validate required arguments
        validateRequiredArguments(parser);

        // Validate email options
        validateEmailOptions(parser);

        // Validate letter options
        validateLetterOptions(parser);

        // Validate that at least one output type is requested
        validateOutputTypeRequested(parser);
    }

    /**
     * Validates that all required arguments are provided.
     *
     * @param parser The ArgumentParser to validate
     * @throws InvalidArgumentException if required arguments are missing
     */
    private static void validateRequiredArguments(ArgumentParser parser)
            throws InvalidArgumentException {
        if (parser.getCsvFilePath() == null) {
            throw new InvalidArgumentException(
                    "Error: --csv-file is required.");
        }

        if (parser.getOutputDir() == null) {
            throw new InvalidArgumentException(
                    "Error: --output-dir is required.");
        }
    }

    /**
     * Validates email-related arguments.
     * If --email is provided, --email-template must also be provided.
     *
     * @param parser The ArgumentParser to validate
     * @throws InvalidArgumentException if email options are invalid
     */
    private static void validateEmailOptions(ArgumentParser parser)
            throws InvalidArgumentException {
        if (parser.isGenerateEmail() && parser.getEmailTemplatePath() == null) {
            throw new InvalidArgumentException(
                    "Error: --email requires --email-template to be provided.");
        }
    }

    /**
     * Validates letter-related arguments.
     * If --letter is provided, --letter-template must also be provided.
     *
     * @param parser The ArgumentParser to validate
     * @throws InvalidArgumentException if letter options are invalid
     */
    private static void validateLetterOptions(ArgumentParser parser)
            throws InvalidArgumentException {
        if (parser.isGenerateLetter() && parser.getLetterTemplatePath() == null) {
            throw new InvalidArgumentException(
                    "Error: --letter requires --letter-template to be provided.");
        }
    }

    /**
     * Validates that at least one output type (email or letter) is requested.
     *
     * @param parser The ArgumentParser to validate
     * @throws InvalidArgumentException if no output type is requested
     */
    private static void validateOutputTypeRequested(ArgumentParser parser)
            throws InvalidArgumentException {
        if (!parser.isGenerateEmail() && !parser.isGenerateLetter()) { // !(email || letter)
            throw new InvalidArgumentException(
                    "Error: At least one of --email or --letter must be specified.");
        }
    }

    /**
     * Returns a usage message explaining how to use the program.
     * This message includes example commands and explains all available options.
     *
     * @return A formatted usage message string
     */
    public static String getUsageMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Insurance Company Communication System ===\n\n");
        sb.append("Usage:\n");
        sb.append("  java Insurance.Main [OPTIONS]\n\n");
        sb.append("Required Options:\n");
        sb.append("  --csv-file <path>        Path to the CSV file containing customer data\n");
        sb.append("  --output-dir <path>      Directory where generated files will be saved\n\n");
        sb.append("Email Options:\n");
        sb.append("  --email                  Generate email messages\n");
        sb.append("  --email-template <path>  Path to the email template file (required if --email is used)\n\n");
        sb.append("Letter Options:\n");
        sb.append("  --letter                 Generate letters\n");
        sb.append("  --letter-template <path> Path to the letter template file (required if --letter is used)\n\n");
        sb.append("Note: At least one of --email or --letter must be specified.\n");
        sb.append("      Arguments can be provided in any order.\n\n");
        sb.append("Examples:\n\n");
        sb.append("  Generate emails only:\n");
        sb.append("    java Insurance.Main --csv-file customers.csv --email \\\n");
        sb.append("         --email-template email-template.txt --output-dir output/\n\n");
        sb.append("  Generate letters only:\n");
        sb.append("    java Insurance.Main --csv-file customers.csv --letter \\\n");
        sb.append("         --letter-template letter-template.txt --output-dir output/\n\n");
        sb.append("  Generate both emails and letters:\n");
        sb.append("    java Insurance.Main --csv-file customers.csv --email \\\n");
        sb.append("         --email-template email-template.txt --letter \\\n");
        sb.append("         --letter-template letter-template.txt --output-dir output/\n");

        return sb.toString();
    }
}