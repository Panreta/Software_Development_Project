package Tax;

/**
 * The ContactInfo class encapsulates all contact-related information for a tax filer.
 * This includes the person's name, physical address, phone number, and email address.
 *
 * <p>This class provides validation for all contact fields to ensure data integrity.
 * Email addresses are validated for basic format requirements, and all fields are
 * checked for non-null and non-empty values.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * Name name = new Name("John", "Doe");
 * ContactInfo contact = new ContactInfo(
 *     name,
 *     "123 Main St, New York, NY 10001",
 *     "555-123-4567",
 *     "john.doe@email.com"
 * );
 * </pre>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class ContactInfo {
    /** The person's name */
    private Name name;

    /** The physical mailing address */
    private String address;

    /** The contact phone number */
    private String phoneNumber;

    /** The contact email address */
    private String emailAddress;

    /**
     * Constructs a new ContactInfo object with the specified contact details.
     * All fields are validated and email addresses are normalized to lowercase.
     *
     * @param name the person's name as a Name object, must not be null
     * @param address the physical mailing address, must not be null or empty
     * @param phoneNumber the contact phone number, must not be null or empty
     * @param emailAddress the contact email address, must be valid format
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public ContactInfo(Name name, String address, String phoneNumber, String emailAddress) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (emailAddress == null || !isValidEmail(emailAddress)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        this.name = name;
        this.address = address.trim();
        this.phoneNumber = phoneNumber.trim();
        this.emailAddress = emailAddress.trim().toLowerCase();
    }

    /** Not necessary, but add as a reference
     * Validates the format of an email address.
     * Checks for presence of @ symbol and at least one dot.
     *
     * @param email the email address to validate
     * @return true if the email format is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    /**
     * Returns the name associated with this contact information.
     *
     * @return the Name object
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the name for this contact information.
     *
     * @param name the Name object to set, must not be null
     * @throws IllegalArgumentException if name is null
     */
    public void setName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    /**
     * Returns the physical address.
     *
     * @return the address as a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the physical address.
     * Address is trimmed of leading and trailing whitespace.
     *
     * @param address the address to set, must not be null or empty
     * @throws IllegalArgumentException if address is null or empty
     */
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        this.address = address.trim();
    }

    /**
     * Returns the phone number.
     *
     * @return the phone number as a String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     * Phone number is trimmed of leading and trailing whitespace.
     *
     * @param phoneNumber the phone number to set, must not be null or empty
     * @throws IllegalArgumentException if phone number is null or empty
     */
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    /**
     * Returns the email address.
     *
     * @return the email address as a String (lowercase)
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address.
     * Email address is normalized to lowercase and validated for format.
     *
     * @param emailAddress the email address to set, must be valid format
     * @throws IllegalArgumentException if email address is invalid
     */
    public void setEmailAddress(String emailAddress) {
        if (!isValidEmail(emailAddress)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.emailAddress = emailAddress.trim().toLowerCase();
    }

    /**
     * Returns a string representation of this ContactInfo object.
     *
     * @return a formatted string containing all contact information
     */
    @Override
    public String toString() {
        return String.format("ContactInfo[name=%s, address=%s, phone=%s, email=%s]",
                name, address, phoneNumber, emailAddress);
    }
}