package Management;

public class TestFrequentFlyer {
    public static void main(String[] args) {
        // Clear any existing accounts before testing
        FrequentFlyer.clearAllAccounts();

        System.out.println("=== FREQUENT FLYER TRANSFER MILES TEST SUITE ===\n");

        // Setup: Create test accounts
        FrequentFlyer sender = new FrequentFlyer(
                "SENDER123456",
                "John",
                "Michael",
                "Smith",
                "john@email.com",
                new MilesBalance(50000, 15000, 5000)
        );

        FrequentFlyer recipient = new FrequentFlyer(
                "RECIP7890123",
                "Jane",
                "Elizabeth",
                "Doe",
                "jane@email.com",
                new MilesBalance(10000, 3000, 1000)
        );

        // TEST 1: Successful transfer with all matching details
        System.out.println("TEST 1: Valid Transfer");
        System.out.println("Initial State:");
        System.out.println("  Sender miles: " + sender.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Recipient miles: " + recipient.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Recipient earned this year: " + recipient.getMilesBalance().getMilesEarnedThisYear());
        System.out.println("  Recipient expiring this year: " + recipient.getMilesBalance().getMilesExpiringThisYear());

        Deposit validDeposit = new Deposit(5000, "RECIP7890123", "Jane", "Elizabeth", "Doe");
        sender.transferMiles(validDeposit);

        System.out.println("After Transfer:");
        System.out.println("  Sender miles: " + sender.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Recipient miles: " + recipient.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Recipient earned this year: " + recipient.getMilesBalance().getMilesEarnedThisYear());
        System.out.println("  Recipient expiring this year: " + recipient.getMilesBalance().getMilesExpiringThisYear());

        // Verify results
        assert sender.getMilesBalance().getTotalMilesAvailable() == 45000 : "Sender should have 45000 miles";
        assert recipient.getMilesBalance().getTotalMilesAvailable() == 15000 : "Recipient should have 15000 miles";
        assert recipient.getMilesBalance().getMilesEarnedThisYear() == 8000 : "Recipient should have 8000 earned this year";
        assert recipient.getMilesBalance().getMilesExpiringThisYear() == 6000 : "Recipient should have 6000 expiring this year";
        System.out.println("Test 1 Passed\n");

        // TEST 2: Non-existent recipient account ID
        System.out.println("TEST 2: Non-existent Recipient Account");
        int senderMilesBefore = sender.getMilesBalance().getTotalMilesAvailable();

        Deposit invalidIdDeposit = new Deposit(2000, "WRONGID12345", "Jane", "Elizabeth", "Doe");
        sender.transferMiles(invalidIdDeposit);

        assert sender.getMilesBalance().getTotalMilesAvailable() == senderMilesBefore : "Sender miles should not change";
        System.out.println("  Sender miles unchanged: " + sender.getMilesBalance().getTotalMilesAvailable());
        System.out.println(" Test 2 Passed\n");

        // TEST 3: Name mismatch (wrong first name)
        System.out.println("TEST 3: Name Mismatch - Wrong First Name");
        senderMilesBefore = sender.getMilesBalance().getTotalMilesAvailable();

        Deposit wrongFirstName = new Deposit(2000, "RECIP7890123", "Janet", "Elizabeth", "Doe");
        sender.transferMiles(wrongFirstName);

        assert sender.getMilesBalance().getTotalMilesAvailable() == senderMilesBefore : "Sender miles should not change";
        System.out.println("  Sender miles unchanged: " + sender.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Test 3 Passed\n");

        // TEST 4: Name mismatch (wrong middle name)
        System.out.println("TEST 4: Name Mismatch - Wrong Middle Name");
        senderMilesBefore = sender.getMilesBalance().getTotalMilesAvailable();

        Deposit wrongMiddleName = new Deposit(2000, "RECIP7890123", "Jane", "Marie", "Doe");
        sender.transferMiles(wrongMiddleName);

        assert sender.getMilesBalance().getTotalMilesAvailable() == senderMilesBefore : "Sender miles should not change";
        System.out.println("  Sender miles unchanged: " + sender.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Test 4 Passed\n");

        // TEST 5: Name mismatch (wrong last name)
        System.out.println("TEST 5: Name Mismatch - Wrong Last Name");
        senderMilesBefore = sender.getMilesBalance().getTotalMilesAvailable();

        Deposit wrongLastName = new Deposit(2000, "RECIP7890123", "Jane", "Elizabeth", "Smith");
        sender.transferMiles(wrongLastName);

        assert sender.getMilesBalance().getTotalMilesAvailable() == senderMilesBefore : "Sender miles should not change";
        System.out.println("  Sender miles unchanged: " + sender.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Test 5 Passed\n");

        // TEST 6: Transfer to account without middle name
        System.out.println("TEST 6: Transfer to Account Without Middle Name");
        FrequentFlyer noMiddleName = new FrequentFlyer(
                "NOMID3456789",
                "Alice",
                "",
                "Brown",
                "alice@email.com",
                new MilesBalance(5000, 1000, 500)
        );

        System.out.println("  Initial Alice's balance: " + noMiddleName.getMilesBalance().getTotalMilesAvailable());

        // Transfer without providing middle name (should work)
        Deposit noMiddleDeposit = new Deposit(1500, "NOMID3456789", "Alice", "Brown");
        sender.transferMiles(noMiddleDeposit);

        assert noMiddleName.getMilesBalance().getTotalMilesAvailable() == 6500 : "Alice should have 6500 miles";
        assert noMiddleName.getMilesBalance().getMilesEarnedThisYear() == 2500 : "Alice should have 2500 earned this year";
        assert noMiddleName.getMilesBalance().getMilesExpiringThisYear() == 2000 : "Alice should have 2000 expiring this year";

        System.out.println("  Final Alice's balance: " + noMiddleName.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Alice's earned this year: " + noMiddleName.getMilesBalance().getMilesEarnedThisYear());
        System.out.println("  Alice's expiring this year: " + noMiddleName.getMilesBalance().getMilesExpiringThisYear());
        System.out.println("  Test 6 Passed\n");

        // TEST 7: Multiple transfers to same recipient
        System.out.println("TEST 7: Multiple Transfers to Same Recipient");
        int recipientBefore = recipient.getMilesBalance().getTotalMilesAvailable();
        int recipientEarnedBefore = recipient.getMilesBalance().getMilesEarnedThisYear();
        int recipientExpiringBefore = recipient.getMilesBalance().getMilesExpiringThisYear();

        Deposit firstTransfer = new Deposit(1000, "RECIP7890123", "Jane", "Elizabeth", "Doe");
        sender.transferMiles(firstTransfer);

        Deposit secondTransfer = new Deposit(2000, "RECIP7890123", "Jane", "Elizabeth", "Doe");
        sender.transferMiles(secondTransfer);

        assert recipient.getMilesBalance().getTotalMilesAvailable() == recipientBefore + 3000 : "Recipient should gain 3000 total";
        assert recipient.getMilesBalance().getMilesEarnedThisYear() == recipientEarnedBefore + 3000 : "Earned should increase by 3000";
        assert recipient.getMilesBalance().getMilesExpiringThisYear() == recipientExpiringBefore + 3000 : "Expiring should increase by 3000";

        System.out.println("  Total transferred: 3000 miles");
        System.out.println("  Recipient total: " + recipient.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Test 7 Passed\n");

        // TEST 8: Case-insensitive name matching
        System.out.println("TEST 8: Case-Insensitive Name Matching");
        int recipientBeforeCase = recipient.getMilesBalance().getTotalMilesAvailable();

        Deposit mixedCase = new Deposit(1000, "RECIP7890123", "JANE", "elizabeth", "DOE");
        sender.transferMiles(mixedCase);

        assert recipient.getMilesBalance().getTotalMilesAvailable() == recipientBeforeCase + 1000 : "Transfer should succeed with different case";
        System.out.println("  Transfer with mixed case names successful");
        System.out.println("  Recipient gained: 1000 miles");
        System.out.println("  Test 8 Passed\n");

        // TEST 9: Transfer maximum allowed amount
        System.out.println("TEST 9: Transfer Maximum Amount (10000 miles)");
        FrequentFlyer richSender = new FrequentFlyer(
                "RICH99999999",
                "Bill",
                "",
                "Gates",
                "bill@email.com",
                new MilesBalance(100000, 50000, 10000)
        );

        int recipientBeforeMax = recipient.getMilesBalance().getTotalMilesAvailable();
        Deposit maxDeposit = new Deposit(10000, "RECIP7890123", "Jane", "Elizabeth", "Doe");
        richSender.transferMiles(maxDeposit);

        assert recipient.getMilesBalance().getTotalMilesAvailable() == recipientBeforeMax + 10000 : "Recipient should gain 10000";
        assert richSender.getMilesBalance().getTotalMilesAvailable() == 90000 : "Rich sender should have 90000 left";

        System.out.println("  Maximum transfer successful: 10000 miles");
        System.out.println("  Rich sender remaining: " + richSender.getMilesBalance().getTotalMilesAvailable());
        System.out.println("  Test 9 Passed\n");

        // TEST 10: Transfer minimum allowed amount
        System.out.println("TEST 10: Transfer Minimum Amount (1000 miles)");
        int recipientBeforeMin = recipient.getMilesBalance().getTotalMilesAvailable();

        Deposit minDeposit = new Deposit(1000, "RECIP7890123", "Jane", "Elizabeth", "Doe");
        richSender.transferMiles(minDeposit);

        assert recipient.getMilesBalance().getTotalMilesAvailable() == recipientBeforeMin + 1000 : "Recipient should gain 1000";
        System.out.println("  Minimum transfer successful: 1000 miles");
        System.out.println("  Test 10 Passed\n");

        System.out.println("=== ALL TESTS PASSED SUCCESSFULLY ===");
        System.out.println("\nTest Summary:");
        System.out.println("  Valid transfers work correctly");
        System.out.println("  Invalid account IDs are rejected");
        System.out.println("  Name mismatches are rejected");
        System.out.println("  Miles are properly added to recipient's earned and expiring");
        System.out.println("  Case-insensitive name matching works");
        System.out.println("  Multiple transfers accumulate correctly");
    }
}