package Insurance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CSVParser.
 * Tests CSV file parsing including handling of commas within quoted values.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class CSVParserTest {

    @TempDir
    Path tempDir;  // JUnit creates a temporary directory for each test

    private String testFilePath;

    /**
     * Sets up a test CSV file before each test.
     */
    @BeforeEach
    public void setUp() {
        testFilePath = tempDir.resolve("test.csv").toString();
    }

    /**
     * Tests parsing a simple CSV file with no commas in values.
     */
    @Test
    public void testParseSimpleCSV() throws IOException, CSVParseException {
        // Create a simple test CSV
        createTestCSV(
                "\"first_name\",\"last_name\",\"email\"",
                "\"John\",\"Doe\",\"john@test.com\"",
                "\"Jane\",\"Smith\",\"jane@test.com\""
        );

        // Parse the CSV
        List<Customer> customers = CSVParser.parseCSV(testFilePath);

        // Verify results
        assertEquals(2, customers.size(), "Should have 2 customers");

        Customer customer1 = customers.get(0);
        assertEquals("John", customer1.get("first_name"));
        assertEquals("Doe", customer1.get("last_name"));
        assertEquals("john@test.com", customer1.get("email"));

        Customer customer2 = customers.get(1);
        assertEquals("Jane", customer2.get("first_name"));
        assertEquals("Smith", customer2.get("last_name"));
        assertEquals("jane@test.com", customer2.get("email"));
    }

    /**
     * Tests parsing CSV with commas inside quoted values.
     */
    @Test
    public void testParseCSVWithCommasInValues() throws IOException, CSVParseException {
        // Create CSV with commas in values
        createTestCSV(
                "\"first_name\",\"last_name\",\"company_name\",\"email\"",
                "\"James\",\"Reign, Jr.\",\"Company, LLC\",\"james@test.com\"",
                "\"Art\",\"Venere\",\"Chemel, James L Cpa\",\"art@test.com\""
        );

        // Parse the CSV
        List<Customer> customers = CSVParser.parseCSV(testFilePath);

        // Verify results
        assertEquals(2, customers.size(), "Should have 2 customers");

        Customer customer1 = customers.get(0);
        assertEquals("James", customer1.get("first_name"));
        assertEquals("Reign, Jr.", customer1.get("last_name"),
                "Last name should include comma");
        assertEquals("Company, LLC", customer1.get("company_name"),
                "Company name should include comma");

        Customer customer2 = customers.get(1);
        assertEquals("Chemel, James L Cpa", customer2.get("company_name"),
                "Company name should include comma");
    }

    /**
     * Tests parsing an empty CSV file.
     */
    @Test
    public void testParseEmptyCSV() {
        // Create empty CSV file
        assertThrows(CSVParseException.class, () -> {
            createTestCSV();  // No lines
            CSVParser.parseCSV(testFilePath);
        }, "Should throw exception for empty CSV");
    }

    /**
     * Tests parsing CSV with only headers (no data).
     */
    @Test
    public void testParseCSVHeaderOnly() throws IOException, CSVParseException {
        // Create CSV with only header
        createTestCSV("\"first_name\",\"last_name\",\"email\"");

        List<Customer> customers = CSVParser.parseCSV(testFilePath);

        assertEquals(0, customers.size(), "Should have 0 customers");
    }

    /**
     * Tests parsing CSV with mismatched column count.
     */
    @Test
    public void testParseCSVMismatchedColumns() {
        assertThrows(CSVParseException.class, () -> {
            createTestCSV(
                    "\"first_name\",\"last_name\",\"email\"",
                    "\"John\",\"Doe\""  // Only 2 values, but header has 3
            );
            CSVParser.parseCSV(testFilePath);
        }, "Should throw exception for mismatched column count");
    }

    /**
     * Tests parsing CSV with unclosed quotes.
     */
    @Test
    public void testParseCSVUnclosedQuotes() {
        assertThrows(CSVParseException.class, () -> {
            createTestCSV(
                    "\"first_name\",\"last_name\",\"email\"",
                    "\"John\",\"Doe,\"test@test.com\""  // Unclosed quote
            );
            CSVParser.parseCSV(testFilePath);
        }, "Should throw exception for unclosed quotes");
    }

    /**
     * Tests parsing CSV with special characters.
     */
    @Test
    public void testParseCSVWithSpecialCharacters() throws IOException, CSVParseException {
        createTestCSV(
                "\"first_name\",\"last_name\",\"email\"",
                "\"O'Brien\",\"Smith-Jones\",\"test@test.com\""
        );

        List<Customer> customers = CSVParser.parseCSV(testFilePath);

        assertEquals(1, customers.size());
        assertEquals("O'Brien", customers.get(0).get("first_name"));
        assertEquals("Smith-Jones", customers.get(0).get("last_name"));
    }

    /**
     * Tests parsing CSV with spaces around values.
     */
    @Test
    public void testParseCSVWithSpaces() throws IOException, CSVParseException {
        createTestCSV(
                "\"first_name\",\"last_name\",\"email\"",
                "\"  John  \",\"  Doe  \",\"  john@test.com  \""
        );

        List<Customer> customers = CSVParser.parseCSV(testFilePath);

        // Values should be trimmed
        assertEquals("John", customers.get(0).get("first_name"));
        assertEquals("Doe", customers.get(0).get("last_name"));
        assertEquals("john@test.com", customers.get(0).get("email"));
    }

    /**
     * Tests that Customer object has all expected columns.
     */
    @Test
    public void testCustomerHasAllColumns() throws IOException, CSVParseException {
        createTestCSV(
                "\"first_name\",\"last_name\",\"email\",\"phone\"",
                "\"John\",\"Doe\",\"john@test.com\",\"555-1234\""
        );

        List<Customer> customers = CSVParser.parseCSV(testFilePath);
        Customer customer = customers.get(0);

        assertTrue(customer.hasColumn("first_name"));
        assertTrue(customer.hasColumn("last_name"));
        assertTrue(customer.hasColumn("email"));
        assertTrue(customer.hasColumn("phone"));
        assertFalse(customer.hasColumn("nonexistent"));
    }

    /**
     * Helper method to create a test CSV file.
     *
     * @param lines The lines to write to the CSV file
     */
    private void createTestCSV(String... lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFilePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}