package Management;

// MilesBalance.java
public class MilesBalance {
    private int totalMilesAvailable;
    private int milesEarnedThisYear;
    private int milesExpiringThisYear;

    // Constructor
    public MilesBalance(int totalMilesAvailable, int milesEarnedThisYear, int milesExpiringThisYear) {
        this.totalMilesAvailable = totalMilesAvailable;
        this.milesEarnedThisYear = milesEarnedThisYear;
        this.milesExpiringThisYear = milesExpiringThisYear;
    }

    // Default constructor
    public MilesBalance() {
        this.totalMilesAvailable = 0;
        this.milesEarnedThisYear = 0;
        this.milesExpiringThisYear = 0;
    }

    // Getters
    public int getTotalMilesAvailable() {
        return totalMilesAvailable;
    }

    public int getMilesEarnedThisYear() {
        return milesEarnedThisYear;
    }

    public int getMilesExpiringThisYear() {
        return milesExpiringThisYear;
    }

    // Setters
    public void setTotalMilesAvailable(int totalMilesAvailable) {
        if (totalMilesAvailable >= 0) {
            this.totalMilesAvailable = totalMilesAvailable;
        }
    }

    public void setMilesEarnedThisYear(int milesEarnedThisYear) {
        if (milesEarnedThisYear >= 0) {
            this.milesEarnedThisYear = milesEarnedThisYear;
        }
    }

    public void setMilesExpiringThisYear(int milesExpiringThisYear) {
        if (milesExpiringThisYear >= 0) {
            this.milesExpiringThisYear = milesExpiringThisYear;
        }
    }


    @Override
    public String toString() {
        return String.format("Miles Balance:\n" +
                        "  Total Available: %,d miles\n" +
                        "  Earned This Year: %,d miles\n" +
                        "  Expiring This Year: %,d miles",
                totalMilesAvailable, milesEarnedThisYear, milesExpiringThisYear);
    }
}