package Management;

import java.util.HashMap;
import java.util.Map;

public class FrequentFlyer {
    // Static map to track all accounts for verification
    private static Map<String, FrequentFlyer> allAccounts = new HashMap<>();

    private String accountId;
    private String firstName;
    private String middleName;
    private String lastName;

    private String emailAddress;
    private MilesBalance milesBalance;

    // Constructor
    public FrequentFlyer(String accountId, String firstName, String middleName,
                         String lastName, String emailAddress, MilesBalance milesBalance) {
        if (accountId == null || accountId.length() != 12) {
            throw new IllegalArgumentException("Account ID must be exactly 12 characters long");
        }
        this.accountId = accountId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.milesBalance = milesBalance != null ? milesBalance : new MilesBalance();

        // Register this account
        allAccounts.put(accountId, this);
    }

    /*
    1.checks that the provided information about a recipient corresponds to one of the existing customers.
    2.provided ID matches recipient’s name.
    3. count both towards miles earned this year, and miles expiring by the end of this calendar year
     */

    // THE TRANSFER MILES METHOD
    public void transferMiles(Deposit deposit) {
        // 1. Check that recipient exists (corresponds to existing customer)
        FrequentFlyer recipient = allAccounts.get(deposit.getRecipientAccountId());
        if (recipient == null) {
            return;  // Recipient doesn't exist, stop here
        }

        // 2. Check that provided ID matches recipient's name
        boolean nameMatches =
                recipient.firstName.equalsIgnoreCase(deposit.getRecipientFirstName()) &&
                        recipient.lastName.equalsIgnoreCase(deposit.getRecipientLastName());

        // Check middle name if provided
        if (deposit.getRecipientMiddleName() != null && !deposit.getRecipientMiddleName().isEmpty()) {
            nameMatches = nameMatches &&
                    recipient.middleName != null &&
                    recipient.middleName.equalsIgnoreCase(deposit.getRecipientMiddleName());
        }

        if (!nameMatches) {
            return;  // Name doesn't match, stop here
        }

        // 3. Transfer miles - count towards BOTH earned this year AND expiring this year
        int transferAmount = deposit.getAmount();

        // Deduct from sender
        milesBalance.setTotalMilesAvailable(
                milesBalance.getTotalMilesAvailable() - transferAmount
        );

        // Add to recipient's balance
        MilesBalance recipientBalance = recipient.getMilesBalance();

        // Add to total
        recipientBalance.setTotalMilesAvailable(
                recipientBalance.getTotalMilesAvailable() + transferAmount
        );

        // Add to miles earned this year
        recipientBalance.setMilesEarnedThisYear(
                recipientBalance.getMilesEarnedThisYear() + transferAmount
        );

        // Add to miles expiring this year
        recipientBalance.setMilesExpiringThisYear(
                recipientBalance.getMilesExpiringThisYear() + transferAmount
        );
    }

    // Static method to clear all accounts (useful for testing)
    public static void clearAllAccounts() {
        allAccounts.clear();
    }

    // Static method to get an account by ID (useful for testing)
    public static FrequentFlyer getAccountById(String accountId) {
        return allAccounts.get(accountId);
    }

    // Getters
    public String getAccountId() { return accountId; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmailAddress() { return emailAddress; }
    public MilesBalance getMilesBalance() { return milesBalance; }

    public String getFullName() {
        if (middleName != null && !middleName.isEmpty()) {
            return firstName + " " + middleName + " " + lastName;
        }
        return firstName + " " + lastName;
    }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setMilesBalance(MilesBalance milesBalance) { this.milesBalance = milesBalance; }
}