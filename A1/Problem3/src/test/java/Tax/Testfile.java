package Tax;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Tax Return Preparation System.
 * Tests all filer types, tax calculations, and validations.
 *
 * @author Tax System Development Team
 * @version 1.0
 */
public class Testfile {

    private Name testName;
    private ContactInfo testContact;

    @BeforeEach
    void setUp() {
        testName = new Name("John", "Doe");
        testContact = new ContactInfo(
                testName,
                "123 Test St, Test City, TS 10000",
                "555-123-4567",
                "john.doe@test.com"
        );
    }

    /**
     * Tests for Name class
     */
    @Nested
    @DisplayName("Name Class Tests")
    class NameTests {

        @Test
        @DisplayName("Should create valid name")
        void testValidNameCreation() {
            Name name = new Name("Jane", "Smith");
            assertEquals("Jane", name.getFirstName());
            assertEquals("Smith", name.getLastName());
            assertEquals("Jane Smith", name.getFullName());
        }

        @Test
        @DisplayName("Should trim whitespace from names")
        void testNameTrimming() {
            Name name = new Name("  Jane  ", "  Smith  ");
            assertEquals("Jane", name.getFirstName());
            assertEquals("Smith", name.getLastName());
        }

        @Test
        @DisplayName("Should throw exception for null first name")
        void testNullFirstName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Name(null, "Smith"));
        }

        @Test
        @DisplayName("Should throw exception for empty last name")
        void testEmptyLastName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Name("Jane", ""));
        }

        @Test
        @DisplayName("Should implement equals correctly")
        void testNameEquals() {
            Name name1 = new Name("Jane", "Smith");
            Name name2 = new Name("Jane", "Smith");
            Name name3 = new Name("John", "Smith");

            assertEquals(name1, name2);
            assertNotEquals(name1, name3);
        }
    }

    /**
     * Tests for ContactInfo class
     */
    @Nested
    @DisplayName("ContactInfo Class Tests")
    class ContactInfoTests {

        @Test
        @DisplayName("Should create valid contact info")
        void testValidContactCreation() {
            ContactInfo contact = new ContactInfo(
                    testName,
                    "456 Main St",
                    "555-9999",
                    "test@email.com"
            );

            assertEquals(testName, contact.getName());
            assertEquals("456 Main St", contact.getAddress());
            assertEquals("555-9999", contact.getPhoneNumber());
            assertEquals("test@email.com", contact.getEmailAddress());
        }

        @Test
        @DisplayName("Should normalize email to lowercase")
        void testEmailNormalization() {
            ContactInfo contact = new ContactInfo(
                    testName,
                    "456 Main St",
                    "555-9999",
                    "TEST@EMAIL.COM"
            );
            assertEquals("test@email.com", contact.getEmailAddress());
        }

        @Test
        @DisplayName("Should validate email format")
        void testInvalidEmail() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContactInfo(testName, "123 St", "555-1234", "invalid-email"));

            assertThrows(IllegalArgumentException.class,
                    () -> new ContactInfo(testName, "123 St", "555-1234", "no@dotcom"));
        }

        @Test
        @DisplayName("Should reject null fields")
        void testNullFields() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContactInfo(null, "123 St", "555-1234", "test@test.com"));

            assertThrows(IllegalArgumentException.class,
                    () -> new ContactInfo(testName, null, "555-1234", "test@test.com"));

            assertThrows(IllegalArgumentException.class,
                    () -> new ContactInfo(testName, "123 St", null, "test@test.com"));
        }
    }

    /**
     * Tests for Individual Filer tax calculations
     */
    @Nested
    @DisplayName("Individual Filer Tax Calculation Tests")
    class IndividualFilerTests {

        private IndividualFiler filer;

        @BeforeEach
        void setUp() {
            filer = new IndividualFiler("123-45-6789", testContact);
        }

        @Test
        @DisplayName("Should calculate taxes for low income individual")
        void testLowIncomeTax() {
            // Income under $55,000 - 15% rate
            filer.setLastYearEarnings(40000.0);
            filer.setTotalIncomeTaxPaid(5000.0);

            // Basic taxable: 40000 - 5000 = 35000
            // No deductions apply
            // Tax: 35000 * 0.15 = 5250
            Double tax = filer.calculateTaxes();
            assertEquals(5250.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should calculate taxes for high income individual")
        void testHighIncomeTax() {
            // Income over $55,000 - 19% rate
            filer.setLastYearEarnings(80000.0);
            filer.setTotalIncomeTaxPaid(10000.0);

            // Basic taxable: 80000 - 10000 = 70000
            // No deductions apply
            // Tax: 70000 * 0.19 = 13300
            Double tax = filer.calculateTaxes();
            assertEquals(13300.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should apply retirement and health savings deduction")
        void testRetirementHealthDeduction() {
            filer.setLastYearEarnings(60000.0);
            filer.setTotalIncomeTaxPaid(5000.0);
            filer.setRetirementContributions(10000.0);
            filer.setHealthSavingsContributions(4000.0);

            // Basic taxable: 60000 - 5000 = 55000
            // Retirement/Health deduction: (10000 + 4000) * 0.7 = 9800
            // Taxable after deduction: 55000 - 9800 = 45200
            // Tax: 45200 * 0.15 = 6780
            Double tax = filer.calculateTaxes();
            assertEquals(6780.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should apply mortgage interest and property deduction")
        void testMortgagePropertyDeduction() {
            filer.setLastYearEarnings(100000.0);
            filer.setTotalIncomeTaxPaid(10000.0);
            filer.setMortgageInterestPaid(8000.0);
            filer.setPropertyTaxesPaid(5000.0);  // Total: 13000 > 12500

            // Basic taxable: 100000 - 10000 = 90000
            // Mortgage deduction applies: 90000 - 2500 = 87500
            // Tax: 87500 * 0.19 = 16625
            Double tax = filer.calculateTaxes();
            assertEquals(16625.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should not apply mortgage deduction for high earners")
        void testNoMortgageDeductionHighEarner() {
            filer.setLastYearEarnings(300000.0);  // Over $250,000 limit
            filer.setTotalIncomeTaxPaid(30000.0);
            filer.setMortgageInterestPaid(20000.0);
            filer.setPropertyTaxesPaid(10000.0);

            // Basic taxable: 300000 - 30000 = 270000
            // No mortgage deduction (income > 250000)
            // Tax: 270000 * 0.19 = 51300
            Double tax = filer.calculateTaxes();
            assertEquals(51300.0, tax, 0.01);
        }
    }

    /**
     * Tests for Employee (special case of Individual Filer)
     */
    @Nested
    @DisplayName("Employee Tests")
    class EmployeeTests {

        private Employee employee;

        @BeforeEach
        void setUp() {
            employee = new Employee("123-45-6789", testContact, "EMP-001", "Tech Corp");
        }

        @Test
        @DisplayName("Should validate W-2 income matches earnings")
        void testW2Validation() {
            employee.setLastYearEarnings(75000.0);
            employee.setW2Income(75000.0);
            assertTrue(employee.validateW2Income());

            employee.setW2Income(74999.0);
            assertFalse(employee.validateW2Income());
        }

        @Test
        @DisplayName("Should calculate employee taxes correctly")
        void testEmployeeTaxCalculation() {
            employee.setLastYearEarnings(65000.0);
            employee.setW2Income(65000.0);
            employee.setTotalIncomeTaxPaid(8000.0);
            employee.setRetirementContributions(5000.0);

            // Basic taxable: 65000 - 8000 = 57000
            // Retirement deduction: 5000 * 0.7 = 3500
            // Taxable after deduction: 57000 - 3500 = 53500
            // Tax: 53500 * 0.15 = 8025
            Double tax = employee.calculateTaxes();
            assertEquals(8025.0, tax, 0.01);
        }
    }

    /**
     * Tests for Group Filers (Married Filing Jointly)
     */
    @Nested
    @DisplayName("Married Filing Jointly Tests")
    class MarriedFilingJointlyTests {

        private MarriedFilingJointly couple;

        @BeforeEach
        void setUp() {
            couple = new MarriedFilingJointly("123-45-6789", testContact);
        }

        @Test
        @DisplayName("Should calculate taxes for low income couple")
        void testLowIncomeCoupleTax() {
            couple.setLastYearEarnings(70000.0);
            couple.setTotalIncomeTaxPaid(8000.0);

            // Basic taxable: 70000 - 8000 = 62000
            // Tax: 62000 * 0.145 = 8990
            Double tax = couple.calculateTaxes();
            assertEquals(8990.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should calculate taxes for high income couple")
        void testHighIncomeCoupleTax() {
            couple.setLastYearEarnings(120000.0);
            couple.setTotalIncomeTaxPaid(15000.0);

            // Basic taxable: 120000 - 15000 = 105000
            // Tax: 105000 * 0.185 = 19425
            Double tax = couple.calculateTaxes();
            assertEquals(19425.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should cap retirement/health deduction at $17,500")
        void testRetirementHealthDeductionCap() {
            couple.setLastYearEarnings(150000.0);
            couple.setTotalIncomeTaxPaid(20000.0);
            couple.setRetirementContributions(20000.0);
            couple.setHealthSavingsContributions(15000.0);

            // Basic taxable: 150000 - 20000 = 130000
            // Retirement/Health: (20000 + 15000) * 0.65 = 22750
            // Capped at 17500
            // Taxable after deduction: 130000 - 17500 = 112500
            // Tax: 112500 * 0.185 = 20812.50
            Double tax = couple.calculateTaxes();
            assertEquals(20812.50, tax, 0.01);
        }

        @Test
        @DisplayName("Should apply childcare deduction")
        void testChildcareDeduction() {
            couple.setLastYearEarnings(150000.0);
            couple.setTotalIncomeTaxPaid(20000.0);
            couple.setNumberOfDependents(2);
            couple.setNumberOfMinorChildren(2);
            couple.setChildcareExpenses(8000.0);

            // Basic taxable: 150000 - 20000 = 130000
            // Childcare deduction applies: 130000 - 1250 = 128750
            // Tax: 128750 * 0.185 = 23818.75
            Double tax = couple.calculateTaxes();
            assertEquals(23818.75, tax, 0.01);
        }

        @Test
        @DisplayName("Should not apply childcare deduction for high earners")
        void testNoChildcareHighEarner() {
            couple.setLastYearEarnings(250000.0);  // Over $200,000 limit
            couple.setTotalIncomeTaxPaid(30000.0);
            couple.setChildcareExpenses(10000.0);

            // Basic taxable: 250000 - 30000 = 220000
            // No childcare deduction (income > 200000)
            // Tax: 220000 * 0.185 = 40700
            Double tax = couple.calculateTaxes();
            assertEquals(40700.0, tax, 0.01);
        }
    }

    /**
     * Tests for Head of Household
     */
    @Nested
    @DisplayName("Head of Household Tests")
    class HeadOfHouseholdTests {

        private HeadOfHousehold headOfHousehold;

        @BeforeEach
        void setUp() {
            headOfHousehold = new HeadOfHousehold("123-45-6789", testContact);
        }

        @Test
        @DisplayName("Should verify qualification with dependents")
        void testQualification() {
            assertFalse(headOfHousehold.isQualified());

            headOfHousehold.setNumberOfDependents(1);
            assertTrue(headOfHousehold.isQualified());
        }

        @Test
        @DisplayName("Should calculate HOH taxes with all deductions")
        void testCompleteHOHTaxCalculation() {
            headOfHousehold.setLastYearEarnings(85000.0);
            headOfHousehold.setTotalIncomeTaxPaid(12000.0);
            headOfHousehold.setRetirementContributions(8000.0);
            headOfHousehold.setHealthSavingsContributions(3000.0);
            headOfHousehold.setMortgageInterestPaid(9000.0);
            headOfHousehold.setPropertyTaxesPaid(4000.0);  // Total: 13000 > 12500
            headOfHousehold.setNumberOfDependents(1);
            headOfHousehold.setNumberOfMinorChildren(1);
            headOfHousehold.setChildcareExpenses(6000.0);  // > 5000

            // Basic taxable: 85000 - 12000 = 73000
            // Retirement/Health: (8000 + 3000) * 0.65 = 7150
            // After retirement: 73000 - 7150 = 65850
            // Mortgage deduction: 65850 - 2500 = 63350
            // Childcare deduction: 63350 - 1250 = 62100
            // Tax: 62100 * 0.145 = 9004.50
            Double tax = headOfHousehold.calculateTaxes();
            assertEquals(9004.50, tax, 0.01);
        }
    }

    /**
     * Tests for edge cases and validation
     */
    @Nested
    @DisplayName("Edge Case and Validation Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle negative income after deductions")
        void testNegativeIncomeAfterDeductions() {
            IndividualFiler filer = new IndividualFiler("123-45-6789", testContact);
            filer.setLastYearEarnings(10000.0);
            filer.setTotalIncomeTaxPaid(12000.0);

            // Basic taxable would be negative: 10000 - 12000 = -2000
            // Should be treated as 0
            // Tax: 0 * 0.15 = 0
            Double tax = filer.calculateTaxes();
            assertEquals(0.0, tax, 0.01);
        }

        @Test
        @DisplayName("Should reject negative monetary values")
        void testNegativeMonetaryValues() {
            IndividualFiler filer = new IndividualFiler("123-45-6789", testContact);

            assertThrows(IllegalArgumentException.class,
                    () -> filer.setLastYearEarnings(-1000.0));

            assertThrows(IllegalArgumentException.class,
                    () -> filer.setTotalIncomeTaxPaid(-500.0));

            assertThrows(IllegalArgumentException.class,
                    () -> filer.setMortgageInterestPaid(-100.0));
        }

        @Test
        @DisplayName("Should validate minor children don't exceed dependents")
        void testMinorChildrenValidation() {
            MarriedFilingJointly couple = new MarriedFilingJointly("123-45-6789", testContact);
            couple.setNumberOfDependents(2);

            assertThrows(IllegalArgumentException.class,
                    () -> couple.setNumberOfMinorChildren(3));
        }

        @Test
        @DisplayName("Should handle zero income and deductions")
        void testZeroValues() {
            IndividualFiler filer = new IndividualFiler("123-45-6789", testContact);
            // All values default to 0.0

            Double tax = filer.calculateTaxes();
            assertEquals(0.0, tax, 0.01);
        }
    }

    /**
     * Integration tests for complete tax scenarios
     */
    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should calculate complex individual scenario")
        void testComplexIndividualScenario() {
            Employee employee = new Employee("123-45-6789", testContact);
            employee.setLastYearEarnings(95000.0);
            employee.setW2Income(95000.0);
            employee.setTotalIncomeTaxPaid(18000.0);
            employee.setRetirementContributions(6000.0);
            employee.setHealthSavingsContributions(3500.0);
            employee.setMortgageInterestPaid(10000.0);
            employee.setPropertyTaxesPaid(5000.0);
            employee.setStudentLoanAndTuitionPaid(2500.0);
            employee.setCharitableContributions(3000.0);

            // Basic taxable: 95000 - 18000 = 77000
            // Retirement/Health: (6000 + 3500) * 0.7 = 6650
            // After retirement: 77000 - 6650 = 70350
            // Mortgage deduction applies: 70350 - 2500 = 67850
            // Tax: 67850 * 0.19 = 12891.50
            Double tax = employee.calculateTaxes();
            assertEquals(12891.50, tax, 0.01);
        }

        @Test
        @DisplayName("Should calculate complex group scenario")
        void testComplexGroupScenario() {
            MarriedFilingJointly couple = new MarriedFilingJointly("123-45-6789", testContact);
            couple.setLastYearEarnings(180000.0);
            couple.setTotalIncomeTaxPaid(35000.0);
            couple.setRetirementContributions(24000.0);
            couple.setHealthSavingsContributions(7000.0);
            couple.setMortgageInterestPaid(18000.0);
            couple.setPropertyTaxesPaid(8000.0);
            couple.setNumberOfDependents(3);
            couple.setNumberOfMinorChildren(2);
            couple.setChildcareExpenses(12000.0);
            couple.setDependentCareExpenses(3000.0);

            // Basic taxable: 180000 - 35000 = 145000
            // Retirement/Health: (24000 + 7000) * 0.65 = 20150, capped at 17500
            // After retirement: 145000 - 17500 = 127500
            // Mortgage deduction applies: 127500 - 2500 = 125000
            // Childcare deduction applies: 125000 - 1250 = 123750
            // Tax: 123750 * 0.185 = 22893.75
            Double tax = couple.calculateTaxes();
            assertEquals(22893.75, tax, 0.01);
        }
    }
}
