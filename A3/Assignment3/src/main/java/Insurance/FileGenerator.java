package Insurance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Generates output files from processed templates.
 * This class handles creating the output directory and writing
 * individual files for each customer with personalized content.
 * Files are organized into subdirectories based on type (email or letter).
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class FileGenerator {

    /**
     * Generates output files for all customers using the provided template.
     * Creates one file per customer with a unique filename.
     * Files are organized into subdirectories based on the prefix (email or letter).
     *
     * @param customers The list of customers to generate files for
     * @param template The template string with placeholders
     * @param outputDir The base directory where files will be created
     * @param prefix The prefix for generated filenames (e.g., "email" or "letter")
     * @throws IOException if there's an error creating directories or writing files
     */
    public static void generateFiles(List<Customer> customers, String template,
                                     String outputDir, String prefix) throws IOException {
        // Create subdirectory based on prefix (e.g., output/email/ or output/letter/)
        String subDir = Paths.get(outputDir, prefix).toString();
        ensureDirectoryExists(subDir);

        // Generate a file for each customer
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);

            // Process the template with customer data
            String processedContent = TemplateProcessor.processTemplate(template, customer);

            // Generate a unique filename
            String fileName = generateFileName(customer, prefix, i + 1);
            String filePath = Paths.get(subDir, fileName).toString();

            // Write the file
            writeFile(filePath, processedContent);
        }
    }

    /**
     * Generates a unique filename for a customer's output file.
     * The filename format is: prefix_FirstName_LastName.txt
     * If first_name or last_name are not available, uses the index instead.
     *
     * @param customer The customer object
     * @param prefix The prefix for the filename (e.g., "email" or "letter")
     * @param index The index number of this customer (1-based)
     * @return A unique filename string
     */
    private static String generateFileName(Customer customer, String prefix, int index) {
        String firstName = customer.get("first_name");
        String lastName = customer.get("last_name");

        // If we have both first and last name, use them
        if (firstName != null && !firstName.isEmpty() &&
                lastName != null && !lastName.isEmpty()) {
            // Remove any characters that might be problematic in filenames
            firstName = sanitizeForFilename(firstName);
            lastName = sanitizeForFilename(lastName);
            return prefix + "_" + firstName + "_" + lastName + ".txt";
        } else {
            // Otherwise, use the index
            return prefix + "_" + index + ".txt";
        }
    }

    /**
     * Sanitizes a string to be safe for use in a filename.
     * Removes or replaces characters that are not allowed in filenames.
     *
     * @param input The string to sanitize
     * @return A sanitized string safe for filenames
     */
    private static String sanitizeForFilename(String input) {
        // Replace spaces and special characters with underscores
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }// Only with the regular charactors

    /**
     * Ensures that a directory exists, creating it if necessary.
     * Creates all parent directories as needed.
     *
     * @param dirPath The path to the directory
     * @throws IOException if the directory cannot be created
     */
    private static void ensureDirectoryExists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Writes content to a file.
     * Creates a new file or overwrites an existing file.
     *
     * @param filePath The path where the file should be written
     * @param content The content to write to the file
     * @throws IOException if there's an error writing the file
     */
    private static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}