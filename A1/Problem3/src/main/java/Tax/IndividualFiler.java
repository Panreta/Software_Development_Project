package Tax;

/**
 * The IndividualFiler class represents a single person filing taxes independently.
 * This class extends TaxFiler and provides specific tax calculation logic for
 * individual tax filers using the 2024 federal tax brackets.
 *
 * <p>Individual filers can choose between the standard deduction or itemized deductions,
 * whichever provides the greater tax benefit. The standard deduction for 2024 is $13,850.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * IndividualFiler filer = new IndividualFiler("123-45-6789", contactInfo);
 * filer.setLastYearEarnings(75000.0);
 * filer.setTotalIncomeTaxPaid(12000.0);
 * Double taxOwed = filer.calculateTax();
 * </pre>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public class IndividualFiler extends TaxFiler {
    /** 2024 standard deduction for single filers */
    private static final Double STANDARD_DEDUCTION = 13850.0;

    /**
     * Constructs a new IndividualFiler with the specified tax ID and contact information.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this filer
     */
    public IndividualFiler(String taxId, ContactInfo contactInfo) {
        super(taxId, contactInfo);
    }

    /**
     * Calculates the federal income tax for an individual filer using 2024 tax brackets.
     * Uses the greater of standard deduction or itemized deductions to minimize tax liability.
     *
     * <p>2024 Tax Brackets for Single Filers:</p>
     * <ul>
     * <li>10% on income up to $11,600</li>
     * <li>12% on income from $11,601 to $47,150</li>
     * <li>22% on income from $47,151 to $100,525</li>
     * <li>24% on income from $100,526 to $191,950</li>
     * <li>32% on income from $191,951 to $243,725</li>
     * <li>35% on income from $243,726 to $609,350</li>
     * <li>37% on income over $609,350</li>
     * </ul>
     *
     * @return the calculated tax amount as a Double (positive for amount owed, zero for no tax due)
     */
    @Override
    public Double calculateTax() {
        Double income = getLastYearEarnings();
        Double deductions = Math.max(STANDARD_DEDUCTION, getTotalDeductions());
        Double taxableIncome = Math.max(0, income - deductions);

        // 2024 Tax brackets for single filers
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
        } else if (taxableIncome <= 609350) {
            tax = 55678.50 + (taxableIncome - 243725) * 0.35;
        } else {
            tax = 183647.25 + (taxableIncome - 609350) * 0.37;
        }

        // Return the tax owed (tax calculated minus tax already paid)
        return Math.max(0, tax - getTotalIncomeTaxPaid());
    }

    /**
     * Returns the filing status for an individual filer.
     *
     * @return "Single" as the filing status
     */
    @Override
    public String getFilingStatus() {
        return "Single";
    }

    /**
     * Generates a tax summary report for this individual filer.
     * Includes all relevant tax information in a formatted string.
     *
     * @return a formatted String containing the tax summary
     */
    public String getTaxSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Tax Summary for Individual Filer ===\n");
        summary.append("Tax ID: ").append(getTaxId()).append("\n");
        summary.append("Name: ").append(getContactInfo().getName().getFullName()).append("\n");
        summary.append("Filing Status: ").append(getFilingStatus()).append("\n");
        summary.append("Gross Income: $").append(String.format("%.2f", getLastYearEarnings())).append("\n");
        summary.append("Total Deductions: $").append(String.format("%.2f", getTotalDeductions())).append("\n");
        summary.append("Standard Deduction: $").append(String.format("%.2f", STANDARD_DEDUCTION)).append("\n");
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
