package Tax;

/**
 * The Name class represents a person's full name consisting of a first name and a last name.
 * This class provides immutable storage and access to name components.
 *
 * <p>This class is designed to be immutable - once a Name object is created, its values
 * cannot be changed. This ensures thread-safety and prevents accidental modifications.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * Name name = new Name("John", "Doe");
 * System.out.println(name.getFullName()); // Outputs: John Doe
 * </pre>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class Name {
    /** The person's first name */
    private final String firstName;

    /** The person's last name */
    private final String lastName;

    /**
     * Constructs a new Name object with the specified first and last names.
     * Both names are trimmed of leading and trailing whitespace before storage.
     *
     * @param firstName the person's first name, must not be null or empty
     * @param lastName the person's last name, must not be null or empty
     * @throws IllegalArgumentException if either firstName or lastName is null or empty
     */
    public Name(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    /**
     * Returns the first name.
     *
     * @return the first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name.
     *
     * @return the last name as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the full name in the format "FirstName LastName".
     *
     * @return the full name as a String
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns a string representation of this Name object.
     * The string representation is the same as the full name.
     *
     * @return the full name as a String
     */
    @Override
    public String toString() {
        return getFullName();
    }

    /**
     * Compares this Name object with another object for equality.
     * Two Name objects are considered equal if they have the same first name
     * and last name (case-sensitive comparison).
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Name name = (Name) obj;
        return firstName.equals(name.firstName) && lastName.equals(name.lastName);
    }

    /**
     * Returns a hash code value for this Name object.
     * The hash code is computed based on both the first name and last name.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return firstName.hashCode() * 31 + lastName.hashCode();
    }
}
