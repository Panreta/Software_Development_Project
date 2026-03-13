package Tax;


/**
 * The HeadOfHousehold class represents an unmarried individual who maintains
 * a household for qualifying dependents. This filing status provides more
 * favorable tax rates than single filing status but requires meeting specific
 * qualification criteria.
 *
 * <p>Qualification Requirements:</p>
 * <ul>
 * <li>Must be unmarried or considered unmarried on the last day of the year</li>
 * <li>Must have paid more than half the cost of maintaining a home</li>
 * <li>Must have a qualifying dependent living with them for more than half the year</li>
 * </ul>
 *
 * <p>2024 Standard Deduction: $20,800</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * HeadOfHousehold filer = new HeadOfHousehold("123-45-6789", contactInfo);
 * filer.setNumberOfDependents(1);
 * filer.setNumberOfMinorChildren(1);
 * if (filer.isQualified()) {
 *     Double taxOwed = filer.calculateTax();
 * }
 * </pre>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class HeadOfHousehold extends GroupFiler {
    /** 2024 standard deduction for head of household */
    private static final Double STANDARD_DEDUCTION = 20800.0;

    /**
     * Constructs a new HeadOfHousehold filer with the specified tax ID and contact information.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this head of household
     */
    public HeadOfHousehold(String taxId, ContactInfo contactInfo) {
        super(taxId, contactInfo);
    }

    /**
     * Calculates the federal income tax for head of household using 2024 tax brackets.
     * Applies child tax credit and dependent care credit where applicable.
     *
     * <p>2024 Tax Brackets for Head of Household:</p>
     * <ul>
     * <li>10% on income up to $16,550</li>
     * <li>12% on income from $16,551 to $63,100</li>
     * <li>22% on income from $63,101 to $100,500</li>
     * <li>24% on income from $100,501 to $191,950</li>
     * <li>32% on income from $191,951 to $243,700</li>
     * <li>35% on income from $243,701 to $609,350</li>
     * <li>37% on income over $609,350</li>
     * </ul>
     *
     * @return the calculated tax amount as a Double (positive for amount owed)
     */
    @Override
    public Double calculateTax() {
        Double income = getLastYearEarnings();
        Double deductions = Math.max(STANDARD_DEDUCTION, getTotalDeductions());
        Double taxableIncome = Math.max(0, income - deductions);

        // 2024 Tax brackets for head of household
        Double tax = 0.0;

        if (taxableIncome <= 16550) {
            tax = taxableIncome * 0.10;
        } else if (taxableIncome <= 63100) {
            tax = 1655 + (taxableIncome - 16550) * 0.12;
        } else if (taxableIncome <= 100500) {
            tax = 7241 + (taxableIncome - 63100) * 0.22;
        } else if (taxableIncome <= 191950) {
            tax = 15469 + (taxableIncome - 100500) * 0.24;
        } else if (taxableIncome <= 243700) {
            tax = 37417 + (taxableIncome - 191950) * 0.32;
        } else if (taxableIncome <= 609350) {
            tax = 53977 + (taxableIncome - 243700) * 0.35;
        } else {
            tax = 181954.50 + (taxableIncome - 609350) * 0.37;
        }

        // Apply tax credits
        tax -= calculateChildTaxCredit();
        tax -= calculateDependentCareCredit();

        // Return the tax owed (tax calculated minus tax already paid)
        return Math.max(0, tax - getTotalIncomeTaxPaid());
    }

    /**
     * Returns the filing status.
     *
     * @return "Head of Household" as the filing status
     */
    @Override
    public String getFilingStatus() {
        return "Head of Household";
    }

    /**
     * Validates that this filer qualifies for head of household status.
     * To qualify, the filer must have at least one dependent.
     *
     * @return true if qualified (has at least one dependent), false otherwise
     */
    public boolean isQualified() {
        return getNumberOfDependents() > 0;
    }

    /**
     * Generates a comprehensive tax summary for head of household filers.
     * Includes qualification status and all relevant tax calculations.
     *
     * @return a formatted String containing the tax summary
     */
    public String getTaxSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Tax Summary for Head of Household ===\n");
        summary.append("Tax ID: ").append(getTaxId()).append("\n");
        summary.append("Name: ").append(getContactInfo().getName().getFullName()).append("\n");
        summary.append("Filing Status: ").append(getFilingStatus()).append("\n");

        // Check qualification status
        if (!isQualified()) {
            summary.append("\nWARNING: May not qualify for Head of Household status (no dependents)\n");
            summary.append("Consider filing as Single instead.\n\n");
        } else {
            summary.append("Qualification Status: QUALIFIED\n\n");
        }

        summary.append("Income: $").append(String.format("%.2f", getLastYearEarnings())).append("\n");
        summary.append("Standard Deduction: $").append(String.format("%.2f", STANDARD_DEDUCTION)).append("\n");
        summary.append("Itemized Deductions: $").append(String.format("%.2f", getTotalDeductions())).append("\n");
        summary.append("Tax Already Paid: $").append(String.format("%.2f", getTotalIncomeTaxPaid())).append("\n");

        // Add dependent information
        summary.append("\n").append(getDependentSummary());

        // Calculate and display tax result
        Double taxOwed = calculateTax();
        summary.append("\n");
        if (taxOwed > 0) {
            summary.append("Tax Owed: $").append(String.format("%.2f", taxOwed)).append("\n");
        } else {
            summary.append("Tax Refund: $").append(String.format("%.2f", Math.abs(taxOwed))).append("\n");
        }

        return summary.toString();
    }

    /**
     * Returns the higher standard deduction amount compared to single filing.
     * This method can be used to show the benefit of HOH status.
     *
     * @return the additional deduction benefit compared to single filing
     */
    public Double getDeductionBenefit() {
        Double singleDeduction = 13850.0; // 2024 single filer standard deduction
        return STANDARD_DEDUCTION - singleDeduction;
    }
}