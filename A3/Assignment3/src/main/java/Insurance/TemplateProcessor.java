package Insurance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Processes template files by replacing placeholders with customer data.
 * Templates contain placeholders in the format [[column_name]] which are
 * replaced with actual values from customer records.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class TemplateProcessor {

    /** Regular expression pattern to match placeholders like [[column_name]] */
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\[\\[(\\w+)\\]\\]");

    /**
     * Reads the entire contents of a template file.
     *
     * @param filePath The path to the template file
     * @return The contents of the template file as a string
     * @throws IOException if there's an error reading the file
     */
    public static String readTemplate(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath)); // for the datas are byte on desk
        return new String(bytes);
    }

    /**
     * Processes a template by replacing all placeholders with customer data.
     * Placeholders in the format [[column_name]] are replaced with the
     * corresponding value from the customer object.
     *
     * If a placeholder refers to a column that doesn't exist in the customer data,
     * it will be replaced with an empty string.
     *
     * @param template The template string containing placeholders
     * @param customer The customer object containing data for replacement
     * @return The processed template with all placeholders replaced
     */
    public static String processTemplate(String template, Customer customer) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // Extract the column name from the placeholder
            String columnName = matcher.group(1);

            // Get the value from the customer data
            String value = customer.get(columnName);

            // If the column doesn't exist, use empty string
            if (value == null) {
                value = "";
            }

            // Replace the placeholder with the actual value
            // Use Matcher.quoteReplacement to handle special characters in the value
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }

        matcher.appendTail(result);
        return result.toString();
    }
}