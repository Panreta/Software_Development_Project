package Tax;


/**
 * The MarriedFilingJointly class represents a married couple who choose to
 * file their taxes together on a single return. This filing status typically
 * provides the most favorable tax rates and the highest standard deduction
 * for married couples.
 *
 * <p>Married filing jointly status allows couples to combine their incomes
 * and deductions, often resulting in a lower overall tax liability compared
 * to filing separately.</p>
 *
 * <p>2024 Standard Deduction: $27,700</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * MarriedFilingJointly couple = new MarriedFilingJointly("123-45-6789", contactInfo);
 * couple.setLastYearEarnings(150000.0);  // Combined income
 * couple.setNumberOfMinorChildren(2);
 * Double taxOwed = couple.calculateTax();
 * </pre>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class MarriedFilingJointly extends GroupFiler {
    /** 2024 standard deduction for married filing jointly */
    private static final Double STANDARD_DEDUCTION = 27700.0;

    /**
     * Constructs a new MarriedFilingJointly filer with the specified tax ID and contact information.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this married couple
     */
    public MarriedFilingJointly(String taxId, ContactInfo contactInfo) {
        super(taxId, contactInfo);
    }

    /**
     * Calculates the federal income tax for married filing jointly using 2024 tax brackets.
     * Applies child tax credit and dependent care credit where applicable.
     *
     * <p>2024 Tax Brackets for Married Filing Jointly:</p>
     * <ul>
     * <li>10% on income up to $23,200</li>
     * <li>12% on income from $23,201 to $94,300</li>
     * <li>22% on income from $94,301 to $201,050</li>
     * <li>24% on income from $201,051 to $383,900</li>
     * <li>32% on income from $383,901 to $487,450</li>
     * <li>35% on income from $487,451 to $731,200</li>
     * <li>37% on income over $731,200</li>
     * </ul>
     *
     * @return the calculated tax amount as a Double (positive for amount owed)
     */
    @Override
    public Double calculateTax() {
        Double income = getLastYearEarnings();
        Double deductions = Math.max(STANDARD_DEDUCTION, getTotalDeductions());
        Double taxableIncome = Math.max(0, income - deductions);

        // 2024 Tax brackets for married filing jointly
        Double tax = 0.0;

        if (taxableIncome <= 23200) {
            tax = taxableIncome * 0.10;
        } else if (taxableIncome <= 94300) {
            tax = 2320 + (taxableIncome - 23200) * 0.12;
        } else if (taxableIncome <= 201050) {
            tax = 10852 + (taxableIncome - 94300) * 0.22;
        } else if (taxableIncome <= 383900) {
            tax = 34337 + (taxableIncome - 201050) * 0.24;
        } else if (taxableIncome <= 487450) {
            tax = 78221 + (taxableIncome - 383900) * 0.32;
        } else if (taxableIncome <= 731200) {
            tax = 111357 + (taxableIncome - 487450) * 0.35;
        } else {
            tax = 196669.50 + (taxableIncome - 731200) * 0.37;
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
     * @return "Married Filing Jointly" as the filing status
     */
    @Override
    public String getFilingStatus() {
        return "Married Filing Jointly";
    }

    /**
     * Generates a comprehensive tax summary for married filing jointly.
     * Includes combined income, deductions, credits, and final tax calculation.
     *
     * @return a formatted String containing the complete tax summary
     */
    public String getTaxSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Tax Summary for Married Filing Jointly ===\n");
        summary.append("Tax ID: ").append(getTaxId()).append("\n");
        summary.append("Primary Contact: ").append(getContactInfo().getName().getFullName()).append("\n");
        summary.append("Filing Status: ").append(getFilingStatus()).append("\n");
        summary.append("Combined Income: $").append(String.format("%.2f", getLastYearEarnings())).append("\n");
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
}
