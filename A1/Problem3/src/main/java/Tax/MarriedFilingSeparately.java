package Tax;


/**
 * The MarriedFilingSeparately class represents a married individual who chooses
 * to file taxes separately from their spouse. This filing status may be beneficial
 * in specific circumstances but generally results in higher tax rates and fewer
 * available credits compared to filing jointly.
 *
 * <p>Important limitations for Married Filing Separately:</p>
 * <ul>
 * <li>Cannot claim dependent care credit</li>
 * <li>Limited or no education credits</li>
 * <li>Lower income limits for deductions</li>
 * <li>Both spouses must use same deduction method (standard or itemized)</li>
 * </ul>
 *
 * <p>2024 Standard Deduction: $13,850</p>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class MarriedFilingSeparately extends GroupFiler {
    /** 2024 standard deduction for married filing separately */
    private static final Double STANDARD_DEDUCTION = 13850.0;

    /**
     * Constructs a new MarriedFilingSeparately filer with the specified tax ID and contact information.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this married individual
     */
    public MarriedFilingSeparately(String taxId, ContactInfo contactInfo) {
        super(taxId, contactInfo);
    }

    /**
     * Calculates the federal income tax for married filing separately using 2024 tax brackets.
     * Note that certain credits are limited or unavailable for this filing status.
     *
     * <p>2024 Tax Brackets for Married Filing Separately:</p>
     * <ul>
     * <li>10% on income up to $11,600</li>
     * <li>12% on income from $11,601 to $47,150</li>
     * <li>22% on income from $47,151 to $100,525</li>
     * <li>24% on income from $100,526 to $191,950</li>
     * <li>32% on income from $191,951 to $243,725</li>
     * <li>35% on income from $243,726 to $365,600</li>
     * <li>37% on income over $365,600</li>
     * </ul>
     *
     * @return the calculated tax amount as a Double (positive for amount owed)
     */
    @Override
    public Double calculateTax() {
        Double income = getLastYearEarnings();
        Double deductions = Math.max(STANDARD_DEDUCTION, getTotalDeductions());
        Double taxableIncome = Math.max(0, income - deductions);

        // 2024 Tax brackets for married filing separately
        Double tax = 0.0;

        if (taxableIncome <= 11600) {
            tax = taxableIncome * 0.10;
        } else if (taxableIncome <= 47150) {
            tax = 1160 + (taxableIncome - 11600) * 0.12;
        } else if (taxableIncome <= 100525) {
            tax = 5426 + (taxableIncome - 47150) * 0.22;
        } else if (taxableIncome <= 191950) {
            tax = 17168.50 + (taxableIncome - 100525) * 0.24;
        } else if (taxableIncome <= 243725) {
            tax = 39110.50 + (taxableIncome - 191950) * 0.32;
        } else if (taxableIncome <= 365600) {
            tax = 55678.50 + (taxableIncome - 243725) * 0.35;
        } else {
            tax = 98334.75 + (taxableIncome - 365600) * 0.37;
        }

        // Apply tax credits (limited for MFS)
        tax -= calculateChildTaxCredit();
        // Note: Dependent care credit is typically not available for MFS

        // Return the tax owed (tax calculated minus tax already paid)
        return Math.max(0, tax - getTotalIncomeTaxPaid());
    }

    /**
     * Returns the filing status.
     *
     * @return "Married Filing Separately" as the filing status
     */
    @Override
    public String getFilingStatus() {
        return "Married Filing Separately";
    }

    /**
     * Overrides the dependent care credit calculation as it's typically not available for MFS.
     * The dependent care credit is generally not allowed for married filing separately status
     * unless the spouse lived apart for the last 6 months of the year.
     *
     * @return 0.0 as MFS filers generally cannot claim this credit
     */
    @Override
    protected Double calculateDependentCareCredit() {
        // Dependent care credit is generally not available for MFS
        return 0.0;
    }

    /**
     * Generates a tax summary specific to married filing separately status.
     * Includes warnings about limitations of this filing status.
     *
     * @return a formatted String containing the tax summary
     */
    public String getTaxSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Tax Summary for Married Filing Separately ===\n");
        summary.append("Tax ID: ").append(getTaxId()).append("\n");
        summary.append("Name: ").append(getContactInfo().getName().getFullName()).append("\n");
        summary.append("Filing Status: ").append(getFilingStatus()).append("\n");
        summary.append("Individual Income: $").append(String.format("%.2f", getLastYearEarnings())).append("\n");
        summary.append("Standard Deduction: $").append(String.format("%.2f", STANDARD_DEDUCTION)).append("\n");
        summary.append("Itemized Deductions: $").append(String.format("%.2f", getTotalDeductions())).append("\n");
        summary.append("Tax Already Paid: $").append(String.format("%.2f", getTotalIncomeTaxPaid())).append("\n");

        // Add dependent information with note about limitations
        summary.append("\n=== Dependent Information ===\n");
        summary.append("Number of Dependents: ").append(getNumberOfDependents()).append("\n");
        summary.append("Minor Children: ").append(getNumberOfMinorChildren()).append("\n");
        summary.append("Child Tax Credit: $").append(String.format("%.2f", calculateChildTaxCredit())).append("\n");
        summary.append("Note: Dependent care credit not available for MFS\n");

        // Calculate and display tax result
        Double taxOwed = calculateTax();
        summary.append("\n");
        if (taxOwed > 0) {
            summary.append("Tax Owed: $").append(String.format("%.2f", taxOwed)).append("\n");
        } else {
            summary.append("Tax Refund: $").append(String.format("%.2f", Math.abs(taxOwed))).append("\n");
        }

        summary.append("\nIMPORTANT: Consider filing jointly for potentially lower taxes and more credits.\n");

        return summary.toString();
    }
}