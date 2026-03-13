package Insurance;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents a single customer record from the CSV file.
 * This class stores customer information as key-value pairs where keys are
 * column names from the CSV header and values are the corresponding data.
 *
 * @author Insurance Company IT Team
 * @version 1.0
 */
public class Customer {

    /** Map storing customer data with column names as keys */
    private Map<String, String> data;

    /**
     * Constructs a Customer object with the provided data.
     *
     * @param data A map containing customer information where keys are column names
     *             and values are the corresponding customer data
     */
    public Customer(Map<String, String> data) {
        this.data = new HashMap<>(data);
    }

    /**
     * Retrieves the value for a specific column.
     *
     * @param columnName The name of the column to retrieve
     * @return The value associated with the column name, or null if not found
     */
    public String get(String columnName) {
        return data.get(columnName);
    }

    /**
     * Gets all column names available for this customer.
     *
     * @return A set of all column names
     */
    public Set<String> getColumnNames() {
        return data.keySet();
    }

    /**
     * Checks if a specific column exists in the customer data.
     *
     * @param columnName The name of the column to check
     * @return true if the column exists, false otherwise
     */
    public boolean hasColumn(String columnName) {
        return data.containsKey(columnName);
    }

    /**
     * Returns a string representation of the customer data.
     *
     * @return A string containing all customer information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer{");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        if (sb.length() > 9) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}