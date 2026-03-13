package Tax;

/**
 * The TaxFiler abstract class serves as the base class for all types of tax filers
 * in the tax return preparation system. It encapsulates common attributes and behaviors
 * shared by both individual and group tax filers.
 *
 * <p>This class maintains information about earnings, deductions, and various tax-related
 * expenses that are common to all types of filers. It provides abstract methods that must
 * be implemented by concrete subclasses to calculate taxes and determine filing status.</p>
 *
 * <p>All monetary values are stored as Double and initialized to 0.0 by default.
 * Validation ensures that no monetary value can be negative.</p>
 *
 * @author Tingyu Zhang
 * @version 1.0
 * @since 2024
 */
public abstract class TaxFiler {
    /** Unique tax identification number */
    private String taxId;

    /** Contact information for this tax filer */
    private ContactInfo contactInfo;

    /** Previous year's total earnings */
    private Double lastYearEarnings;

    /** Total income tax already paid through withholdings */
    private Double totalIncomeTaxPaid;

    /** Total mortgage interest paid (deductible) */
    private Double mortgageInterestPaid;

    /** Total property taxes paid (deductible) */
    private Double propertyTaxesPaid;

    /** Total student loan interest and tuition paid (deductible) */
    private Double studentLoanAndTuitionPaid;

    /** Total contributions to retirement accounts (deductible) */
    private Double retirementContributions;

    /** Total contributions to health savings accounts (deductible) */
    private Double healthSavingsContributions;

    /** Total charitable donations and contributions (deductible) */
    private Double charitableContributions;

    /**
     * Constructs a new TaxFiler with the specified tax ID and contact information.
     * All monetary values are initialized to 0.0.
     *
     * @param taxId the unique tax identification number, must not be null or empty
     * @param contactInfo the contact information for this tax filer, must not be null
     * @throws IllegalArgumentException if taxId or contactInfo is invalid
     */
    public TaxFiler(String taxId, ContactInfo contactInfo) {
        if (taxId == null || taxId.trim().isEmpty()) {
            throw new IllegalArgumentException("Tax ID cannot be null or empty");
        }
        if (contactInfo == null) {
            throw new IllegalArgumentException("Contact info cannot be null");
        }

        this.taxId = taxId.trim();
        this.contactInfo = contactInfo;

        // Initialize all monetary fields to 0.0
        this.lastYearEarnings = 0.0;
        this.totalIncomeTaxPaid = 0.0;
        this.mortgageInterestPaid = 0.0;
        this.propertyTaxesPaid = 0.0;
        this.studentLoanAndTuitionPaid = 0.0;
        this.retirementContributions = 0.0;
        this.healthSavingsContributions = 0.0;
        this.charitableContributions = 0.0;
    }

    /**
     * Calculates the total tax liability for this filer.
     * This method must be implemented by all concrete subclasses to provide
     * filing-status-specific tax calculations.
     *
     * @return the calculated tax amount as a Double (positive for amount owed, negative for refund)
     */
    public abstract Double calculateTax();

    /**
     * Returns the filing status of this tax filer.
     * This method must be implemented by all concrete subclasses.
     *
     * @return a String representing the filing status (e.g., "Single", "Married Filing Jointly")
     */
    public abstract String getFilingStatus();

    /**
     * Calculates the total itemized deductions for this tax filer.
     * Includes mortgage interest, property taxes, student loans, retirement,
     * health savings, and charitable contributions.
     *
     * @return the total deductions as a Double
     */
    public Double getTotalDeductions() {
        return mortgageInterestPaid +
                propertyTaxesPaid +
                studentLoanAndTuitionPaid +
                retirementContributions +
                healthSavingsContributions +
                charitableContributions;
    }

    /**
     * Calculates the taxes for this filer according to the system's tax rules.
     *
     * @return the calculated tax amount as a Double
     */
    public Double calculateTaxes() {
        // Step 1: Calculate basic taxable income
        Double taxableIncome = lastYearEarnings - totalIncomeTaxPaid;

        // Step 2: Apply retirement and health savings deduction
        Double retirementHealthTotal = retirementContributions + healthSavingsContributions;
        Double retirementHealthDeduction;

        if (this instanceof GroupFiler) {
            // Group filers: multiply by 0.65, cap at $17,500
            retirementHealthDeduction = Math.min(retirementHealthTotal * 0.65, 17500.0);
        } else {
            // Individual filers: multiply by 0.7
            retirementHealthDeduction = retirementHealthTotal * 0.7;
        }

        taxableIncome = Math.max(0, taxableIncome - retirementHealthDeduction);

        // Step 3: Apply mortgage interest and property deduction
        if (lastYearEarnings < 250000.0 &&
                (mortgageInterestPaid + propertyTaxesPaid) > 12500.0) {
            taxableIncome = Math.max(0, taxableIncome - 2500.0);
        }

        // Step 4: Apply childcare deduction (group filers only)
        if (this instanceof GroupFiler) {
            GroupFiler groupFiler = (GroupFiler) this;
            if (lastYearEarnings < 200000.0 &&
                    groupFiler.getChildcareExpenses() > 5000.0) {
                taxableIncome = Math.max(0, taxableIncome - 1250.0);
            }
        }

        // Step 5: Calculate tax amount based on filing type and income level
        Double taxAmount;
        if (this instanceof GroupFiler) {
            // Group filer tax rates
            if (taxableIncome < 90000.0) {
                taxAmount = taxableIncome * 0.145;
            } else {
                taxAmount = taxableIncome * 0.185;
            }
        } else {
            // Individual filer tax rates
            if (taxableIncome < 55000.0) {
                taxAmount = taxableIncome * 0.15;
            } else {
                taxAmount = taxableIncome * 0.19;
            }
        }

        return taxAmount;
    }

    /**
     * Validates that a monetary amount is non-negative.
     *
     * @param amount the amount to validate
     * @param fieldName the name of the field for error messaging
     * @throws IllegalArgumentException if amount is negative
     */
    protected void validateAmount(Double amount, String fieldName) {
        if (amount != null && amount < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }

    // Getters and Setters with validation

    /**
     * Returns the tax ID.
     *
     * @return the tax ID as a String
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Returns the contact information.
     *
     * @return the ContactInfo object
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the contact information.
     *
     * @param contactInfo the ContactInfo to set, must not be null
     * @throws IllegalArgumentException if contactInfo is null
     */
    public void setContactInfo(ContactInfo contactInfo) {
        if (contactInfo == null) {
            throw new IllegalArgumentException("Contact info cannot be null");
        }
        this.contactInfo = contactInfo;
    }

    /**
     * Returns the last year's earnings.
     *
     * @return the earnings as a Double
     */
    public Double getLastYearEarnings() {
        return lastYearEarnings;
    }

    /**
     * Sets the last year's earnings.
     *
     * @param lastYearEarnings the earnings amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setLastYearEarnings(Double lastYearEarnings) {
        validateAmount(lastYearEarnings, "Last year earnings");
        this.lastYearEarnings = lastYearEarnings;
    }

    /**
     * Returns the total income tax already paid.
     *
     * @return the tax paid as a Double
     */
    public Double getTotalIncomeTaxPaid() {
        return totalIncomeTaxPaid;
    }

    /**
     * Sets the total income tax already paid.
     *
     * @param totalIncomeTaxPaid the tax amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setTotalIncomeTaxPaid(Double totalIncomeTaxPaid) {
        validateAmount(totalIncomeTaxPaid, "Total income tax paid");
        this.totalIncomeTaxPaid = totalIncomeTaxPaid;
    }

    /**
     * Returns the mortgage interest paid.
     *
     * @return the mortgage interest as a Double
     */
    public Double getMortgageInterestPaid() {
        return mortgageInterestPaid;
    }

    /**
     * Sets the mortgage interest paid.
     *
     * @param mortgageInterestPaid the interest amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setMortgageInterestPaid(Double mortgageInterestPaid) {
        validateAmount(mortgageInterestPaid, "Mortgage interest paid");
        this.mortgageInterestPaid = mortgageInterestPaid;
    }

    /**
     * Returns the property taxes paid.
     *
     * @return the property taxes as a Double
     */
    public Double getPropertyTaxesPaid() {
        return propertyTaxesPaid;
    }

    /**
     * Sets the property taxes paid.
     *
     * @param propertyTaxesPaid the tax amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setPropertyTaxesPaid(Double propertyTaxesPaid) {
        validateAmount(propertyTaxesPaid, "Property taxes paid");
        this.propertyTaxesPaid = propertyTaxesPaid;
    }

    /**
     * Returns the student loan and tuition paid.
     *
     * @return the education expenses as a Double
     */
    public Double getStudentLoanAndTuitionPaid() {
        return studentLoanAndTuitionPaid;
    }

    /**
     * Sets the student loan and tuition paid.
     *
     * @param studentLoanAndTuitionPaid the education expense amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setStudentLoanAndTuitionPaid(Double studentLoanAndTuitionPaid) {
        validateAmount(studentLoanAndTuitionPaid, "Student loan and tuition paid");
        this.studentLoanAndTuitionPaid = studentLoanAndTuitionPaid;
    }

    /**
     * Returns the retirement account contributions.
     *
     * @return the retirement contributions as a Double
     */
    public Double getRetirementContributions() {
        return retirementContributions;
    }

    /**
     * Sets the retirement account contributions.
     *
     * @param retirementContributions the contribution amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setRetirementContributions(Double retirementContributions) {
        validateAmount(retirementContributions, "Retirement contributions");
        this.retirementContributions = retirementContributions;
    }

    /**
     * Returns the health savings account contributions.
     *
     * @return the HSA contributions as a Double
     */
    public Double getHealthSavingsContributions() {
        return healthSavingsContributions;
    }

    /**
     * Sets the health savings account contributions.
     *
     * @param healthSavingsContributions the HSA contribution amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setHealthSavingsContributions(Double healthSavingsContributions) {
        validateAmount(healthSavingsContributions, "Health savings contributions");
        this.healthSavingsContributions = healthSavingsContributions;
    }

    /**
     * Returns the charitable donations and contributions.
     *
     * @return the charitable contributions as a Double
     */
    public Double getCharitableContributions() {
        return charitableContributions;
    }

    /**
     * Sets the charitable donations and contributions.
     *
     * @param charitableContributions the donation amount to set
     * @throws IllegalArgumentException if amount is negative
     */
    public void setCharitableContributions(Double charitableContributions) {
        validateAmount(charitableContributions, "Charitable contributions");
        this.charitableContributions = charitableContributions;
    }
}
