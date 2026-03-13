package Tax;

/**
 * The GroupFiler abstract class represents tax filers who file as a group,
 * such as married couples or heads of household. This class extends TaxFiler
 * and adds fields specific to group filing situations including dependents
 * and related care expenses.
 *
 * <p>Group filers can claim additional tax benefits such as child tax credits
 * and dependent care credits that are not available to individual filers.</p>
 *
 * <p>This abstract class provides common functionality for all group filing
 * statuses and must be extended by concrete implementations.</p>
 *
 * @author Tax System Development Team
 * @version 1.0
 * @since 2024
 */
public abstract class GroupFiler extends TaxFiler {
    /** Number of dependents claimed on this return */
    private Integer numberOfDependents;

    /** Number of minor children (under 18) */
    private Integer numberOfMinorChildren;

    /** Annual childcare expenses */
    private Double childcareExpenses;

    /** Annual dependent care expenses (elderly, disabled, etc.) */
    private Double dependentCareExpenses;

    /**
     * Constructs a new GroupFiler with the specified tax ID and contact information.
     * Initializes dependent counts to 0 and care expenses to 0.0.
     *
     * @param taxId the unique tax identification number
     * @param contactInfo the contact information for this group filer
     */
    public GroupFiler(String taxId, ContactInfo contactInfo) {
        super(taxId, contactInfo);
        this.numberOfDependents = 0;
        this.numberOfMinorChildren = 0;
        this.childcareExpenses = 0.0;
        this.dependentCareExpenses = 0.0;
    }

    /**
     * Calculates the child tax credit based on the number of minor children.
     * For 2024, the child tax credit is $2,000 per qualifying child under 17.
     *
     * @return the child tax credit amount as a Double
     */
    protected Double calculateChildTaxCredit() {
        // 2024 child tax credit: $2,000 per qualifying child
        return numberOfMinorChildren * 2000.0;
    }

    /**
     * Calculates the dependent care credit based on care expenses.
     * The credit is 20% of eligible expenses, with limits based on number of dependents:
     * - $3,000 maximum for one dependent
     * - $6,000 maximum for two or more dependents
     *
     * @return the dependent care credit amount as a Double
     */
    protected Double calculateDependentCareCredit() {
        // Simplified calculation: 20% of expenses up to $3,000 for one dependent, $6,000 for two or more
        Double maxExpenses = numberOfDependents == 1 ? 3000.0 : 6000.0;
        Double eligibleExpenses = Math.min(dependentCareExpenses + childcareExpenses, maxExpenses);
        return eligibleExpenses * 0.20;
    }

    /**
     * Returns the number of dependents.
     *
     * @return the number of dependents as an Integer
     */
    public Integer getNumberOfDependents() {
        return numberOfDependents;
    }

    /**
     * Sets the number of dependents.
     *
     * @param numberOfDependents the number to set, must not be negative
     * @throws IllegalArgumentException if number is negative
     */
    public void setNumberOfDependents(Integer numberOfDependents) {
        if (numberOfDependents != null && numberOfDependents < 0) {
            throw new IllegalArgumentException("Number of dependents cannot be negative");
        }
        this.numberOfDependents = numberOfDependents;
    }

    /**
     * Returns the number of minor children.
     *
     * @return the number of minor children as an Integer
     */
    public Integer getNumberOfMinorChildren() {
        return numberOfMinorChildren;
    }

    /**
     * Sets the number of minor children.
     * Validates that the number doesn't exceed total dependents.
     *
     * @param numberOfMinorChildren the number to set
     * @throws IllegalArgumentException if number is negative or exceeds dependents
     */
    public void setNumberOfMinorChildren(Integer numberOfMinorChildren) {
        if (numberOfMinorChildren != null && numberOfMinorChildren < 0) {
            throw new IllegalArgumentException("Number of minor children cannot be negative");
        }
        if (numberOfMinorChildren != null && numberOfDependents != null
                && numberOfMinorChildren > numberOfDependents) {
            throw new IllegalArgumentException("Number of minor children cannot exceed number of dependents");
        }
        this.numberOfMinorChildren = numberOfMinorChildren;
    }

    /**
     * Returns the childcare expenses.
     *
     * @return the childcare expenses as a Double
     */
    public Double getChildcareExpenses() {
        return childcareExpenses;
    }

    /**
     * Sets the childcare expenses.
     *
     * @param childcareExpenses the expense amount to set, must not be negative
     * @throws IllegalArgumentException if amount is negative
     */
    public void setChildcareExpenses(Double childcareExpenses) {
        validateAmount(childcareExpenses, "Childcare expenses");
        this.childcareExpenses = childcareExpenses;
    }

    /**
     * Returns the dependent care expenses.
     *
     * @return the dependent care expenses as a Double
     */
    public Double getDependentCareExpenses() {
        return dependentCareExpenses;
    }

    /**
     * Sets the dependent care expenses.
     *
     * @param dependentCareExpenses the expense amount to set, must not be negative
     * @throws IllegalArgumentException if amount is negative
     */
    public void setDependentCareExpenses(Double dependentCareExpenses) {
        validateAmount(dependentCareExpenses, "Dependent care expenses");
        this.dependentCareExpenses = dependentCareExpenses;
    }

    /**
     * Calculates the total deductions including dependent-related expenses.
     * Adds childcare and dependent care expenses to the base deductions.
     *
     * @return the total deductions as a Double
     */
    @Override
    public Double getTotalDeductions() {
        return super.getTotalDeductions() + childcareExpenses + dependentCareExpenses;
    }

    /**
     * Generates a summary of dependent information.
     *
     * @return a formatted String containing dependent details
     */
    protected String getDependentSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Dependents: ").append(numberOfDependents).append("\n");
        summary.append("Minor Children: ").append(numberOfMinorChildren).append("\n");
        summary.append("Childcare Expenses: $").append(String.format("%.2f", childcareExpenses)).append("\n");
        summary.append("Dependent Care Expenses: $").append(String.format("%.2f", dependentCareExpenses)).append("\n");
        summary.append("Child Tax Credit: $").append(String.format("%.2f", calculateChildTaxCredit())).append("\n");
        summary.append("Dependent Care Credit: $").append(String.format("%.2f", calculateDependentCareCredit())).append("\n");
        return summary.toString();
    }
}