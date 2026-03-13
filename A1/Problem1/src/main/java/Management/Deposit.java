package Management;

// Deposit.java
public class Deposit {
    private int amount;
    private String recipientAccountId;

    private String recipientFirstName;
    private String recipientMiddleName;
    private String recipientLastName;

    // Constructor with middle name
    public Deposit(int amount, String recipientAccountId, String recipientFirstName,
                   String recipientMiddleName, String recipientLastName) {
        setAmount(amount);
        this.recipientAccountId = recipientAccountId;

        this.recipientFirstName = recipientFirstName;
        this.recipientMiddleName = recipientMiddleName;
        this.recipientLastName = recipientLastName;
    }

    // Constructor without middle name
    public Deposit(int amount, String recipientAccountId, String recipientFirstName,
                   String recipientLastName) {
        this(amount, recipientAccountId, recipientFirstName, "", recipientLastName);
    }

    // Getters
    public int getAmount() {
        return amount;
    }

    public String getRecipientAccountId() {
        return recipientAccountId;
    }

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public String getRecipientMiddleName() {
        return recipientMiddleName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public String getRecipientFullName() {
        if (recipientMiddleName != null && !recipientMiddleName.isEmpty()) {
            return recipientFirstName + " " + recipientMiddleName + " " + recipientLastName;
        }
        return recipientFirstName + " " + recipientLastName;
    }

    // Setters
    public void setAmount(int amount) {
        if (amount >= 1000 && amount <= 10000) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Deposit amount must be between 1000 and 10000 miles");
        }
    }

    public void setRecipientAccountId(String recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public void setRecipientMiddleName(String recipientMiddleName) {
        this.recipientMiddleName = recipientMiddleName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    @Override
    public String toString() {
        return String.format("Deposit Details:\n" +
                        "  Amount: %,d miles\n" +
                        "  Recipient Account ID: %s\n" +
                        "  Recipient Name: %s",
                amount, recipientAccountId, getRecipientFullName());
    }
}