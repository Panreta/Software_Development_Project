package Insurance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses CSV files containing customer data.
 * This parser handles quoted fields and commas within quoted values correctly.
 * The CSV format expected has headers in the first row and data in subsequent rows.
 * All fields are enclosed in double quotes and separated by commas.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class CSVParser {

    /**
     * Parses a CSV file and returns a list of Customer objects.
     * Each row in the CSV (except the header) becomes one Customer object.
     *
     * @param filePath The path to the CSV file to parse
     * @return A list of Customer objects representing the CSV data
     * @throws IOException if there's an error reading the file
     * @throws CSVParseException if the CSV format is invalid
     */
    public static List<Customer> parseCSV(String filePath)
            throws IOException, CSVParseException {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read and parse the header line
            String headerLine = reader.readLine();
            if (headerLine == null || headerLine.trim().isEmpty()) {
                throw new CSVParseException("CSV file is empty or has no header.");
            }

            List<String> headers = parseLine(headerLine);
            if (headers.isEmpty()) {
                throw new CSVParseException("CSV header line is empty.");
            }

            // Read and parse data lines
            String line;
            int lineNumber = 2; // Start at 2 since line 1 is the header
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    lineNumber++;
                    continue;
                }

                List<String> values = parseLine(line);

                // Validate that the number of values matches the number of headers
                if (values.size() != headers.size()) {
                    throw new CSVParseException(
                            "Line " + lineNumber + " has " + values.size() +
                                    " values but expected " + headers.size() + " values.");
                }

                // Create a map of header -> value
                Map<String, String> customerData = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    customerData.put(headers.get(i), values.get(i));
                }

                customers.add(new Customer(customerData));
                lineNumber++;
            }
        }

        return customers;
    }

    /**
     * Parses a single line of CSV data.
     * Handles quoted fields and commas within quotes correctly.
     * Expected format: "value1","value2","value3"
     *
     * @param line The CSV line to parse
     * @return A list of values extracted from the line
     * @throws CSVParseException if the line format is invalid
     */
    private static List<String> parseLine(String line) throws CSVParseException {
        List<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // Toggle the insideQuotes flag
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                // End of a field - add the current value
                values.add(currentValue.toString().trim());
                currentValue = new StringBuilder();
            } else {
                // Regular character - add to current value
                currentValue.append(c);
            }
        }

        // Add the last value
        values.add(currentValue.toString().trim());

        // Check if quotes were properly closed
        if (insideQuotes) {
            throw new CSVParseException("Unclosed quote in CSV line: " + line);
        }

        return values;
    }
}