package Insurance;

import java.util.List;

/**
 * Main entry point for the Insurance Company Communication System.
 * This program generates personalized emails and/or letters for customers
 * based on CSV data and template files.
 *
 * The program processes command-line arguments, reads customer data from a CSV file,
 * applies templates with placeholder replacement, and generates output files.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */



public class Main {

    /**
     * Main method - entry point of the application.
     *
     * @param args Command-line arguments
     */


    public static void main(String[] args) {
        // TEMPORARY TEST - Remove this later
        if (args.length == 0) { // default test mode
            args = new String[] {
                    "--csv-file", "test-files/insurance-company-members.csv",
                    "--email",
                    "--email-template", "test-files/email-template.txt",
                    "--letter",
                    "--letter-template", "test-files/letter-template.txt",
                    "--output-dir", "output/"
            };

        }

        try {
            run(args);
        } catch (InvalidArgumentException e) {
            // Handle argument-related errors
            System.err.println(e.getMessage());
            System.err.println(ArgumentValidator.getUsageMessage()); // What the error will be,just use StringBuilder to print all
            System.exit(1);
        } catch (CSVParseException e) {
            // Handle CSV parsing errors
            System.err.println("Error parsing CSV file: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Main execution logic of the program.
     *
     * @param args Command-line arguments
     * @throws Exception if any error occurs during processing
     */
    private static void run(String[] args) throws Exception {
        // Step 1: Parse command-line arguments
        ArgumentParser parser = new ArgumentParser(args);

        parser.parse();

        // Step 2: Validate arguments
        ArgumentValidator.validate(parser);

        // Step 3: Parse the CSV file
        System.out.println("Reading CSV file: " + parser.getCsvFilePath());
        List<Customer> customers = CSVParser.parseCSV(parser.getCsvFilePath());
        System.out.println("Successfully loaded " + customers.size() + " customer records.");

        // Step 4: Generate emails if requested
        if (parser.isGenerateEmail()) {
            System.out.println("\nGenerating email messages...");
            String emailTemplate = TemplateProcessor.readTemplate(parser.getEmailTemplatePath());
            FileGenerator.generateFiles(
                    customers,
                    emailTemplate,
                    parser.getOutputDir(),
                    "email"
            );
            System.out.println("Successfully generated " + customers.size() + " email files.");
        }

        // Step 5: Generate letters if requested
        if (parser.isGenerateLetter()) {
            System.out.println("\nGenerating letters...");
            String letterTemplate = TemplateProcessor.readTemplate(parser.getLetterTemplatePath());
            FileGenerator.generateFiles(
                    customers,
                    letterTemplate,
                    parser.getOutputDir(),
                    "letter"
            );
            System.out.println("Successfully generated " + customers.size() + " letter files.");
        }

        // Step 6: Success message
        System.out.println("\n=== All files generated successfully! ===");
        System.out.println("Output directory: " + parser.getOutputDir());
    }
}

