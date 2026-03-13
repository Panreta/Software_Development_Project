package Tax;


/**
 * The Employee class represents a tax filer who is employed by a company.
 * This is a specialized type of IndividualFiler that may include additional
 * employment-specific tax considerations such as W-2 income and employer information.
 *
 * <p>This class extends IndividualFiler and adds employment-specific fields
 * that can be used for more detailed tax reporting and validation.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * Employee employee = new Employee("123-45-6789", contactInfo, "EIN-99-9999999", "Tech Corp");
 * employee.setW2Income(85000.0);
 * employee.setTotalIncomeTaxPaid(15000.0);
 * Double taxOwed = employee.calculateTax();
 * </pre>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class Employee extends IndividualFiler {
    /** The employer's identification number (EIN) */
    private String employerId;

    /** The name of the employer */
    private String employerName;

    /** The W-2 income reported by the employer */
    private Double w2Income;

    /**
     * Constructs a new Employee filer with the specified tax ID and contact information.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this employee
     */
    public Employee(String taxId, ContactInfo contactInfo) {
        super(taxId, contactInfo);
    }

    /**
     * Constructs a new Employee filer with employer information.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this employee
     * @param employerId the employer's identification number (EIN)
     * @param employerName the name of the employer
     */
    public Employee(String taxId, ContactInfo contactInfo, String employerId, String employerName) {
        super(taxId, contactInfo);
        this.employerId = employerId;
        this.employerName = employerName;
    }

    /**
     * Returns the filing status for an employee filer.
     *
     * @return "Single - Employee" as the filing status
     */
    @Override
    public String getFilingStatus() {
        return "Single - Employee";
    }

    /**
     * Returns the employer identification number.
     *
     * @return the employer ID as a String, may be null if not set
     */
    public String getEmployerId() {
        return employerId;
    }

    /**
     * Sets the employer identification number.
     *
     * @param employerId the employer ID to set
     */
    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    /**
     * Returns the employer name.
     *
     * @return the employer name as a String, may be null if not set
     */
    public String getEmployerName() {
        return employerName;
    }

    /**
     * Sets the employer name.
     *
     * @param employerName the employer name to set
     */
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    /**
     * Returns the W-2 income reported by the employer.
     * This should match the last year earnings for employed individuals.
     *
     * @return the W-2 income as a Double, may be null if not set
     */
    public Double getW2Income() {
        return w2Income;
    }

    /**
     * Sets the W-2 income reported by the employer.
     * This amount should typically match the last year earnings.
     *
     * @param w2Income the W-2 income to set
     * @throws IllegalArgumentException if income is negative
     */
    public void setW2Income(Double w2Income) {
        validateAmount(w2Income, "W-2 Income");
        this.w2Income = w2Income;
    }

    /**
     * Validates that W-2 income matches reported earnings.
     * This is a helper method to ensure data consistency.
     *
     * @return true if W-2 income matches last year earnings, false otherwise
     */
    public boolean validateW2Income() {
        if (w2Income == null || getLastYearEarnings() == null) {
            return false;
        }
        return Math.abs(w2Income - getLastYearEarnings()) < 0.01;
    }

    /**
     * Generates an employment-specific tax summary.
     * Includes employer information and W-2 details in addition to standard tax summary.
     *
     * @return a formatted String containing the employee tax summary
     */
    @Override
    public String getTaxSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Tax Summary for Employee Filer ===\n");
        summary.append("Tax ID: ").append(getTaxId()).append("\n");
        summary.append("Name: ").append(getContactInfo().getName().getFullName()).append("\n");
        summary.append("Filing Status: ").append(getFilingStatus()).append("\n");

        if (employerName != null) {
            summary.append("Employer: ").append(employerName).append("\n");
        }
        if (employerId != null) {
            summary.append("Employer ID: ").append(employerId).append("\n");
        }
        if (w2Income != null) {
            summary.append("W-2 Income: $").append(String.format("%.2f", w2Income)).append("\n");
        }

        summary.append("Gross Income: $").append(String.format("%.2f", getLastYearEarnings())).append("\n");
        summary.append("Tax Already Paid: $").append(String.format("%.2f", getTotalIncomeTaxPaid())).append("\n");

        Double taxOwed = calculateTax();
        if (taxOwed > 0) {
            summary.append("Tax Owed: $").append(String.format("%.2f", taxOwed)).append("\n");
        } else {
            summary.append("Tax Refund: $").append(String.format("%.2f", Math.abs(taxOwed))).append("\n");
        }

        return summary.toString();
    }
}
