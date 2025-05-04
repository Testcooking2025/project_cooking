package models;

/**
 * Represents a customer's invoice containing amount and payment status.
 */
public class Invoice {

    private final String customer;
    private final double amount;
    private String status; // "Unpaid" or "Paid"

    /**
     * Constructs a new invoice for the given customer with the specified amount.
     * The initial status is set to "Unpaid".
     *
     * @param customer The name of the customer.
     * @param amount   The amount of the invoice.
     */
    public Invoice(String customer, double amount) {
        this.customer = customer;
        this.amount = amount;
        this.status = "Unpaid";
    }

    /**
     * Returns the customer's name associated with the invoice.
     *
     * @return The customer's name.
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Returns the amount of the invoice.
     *
     * @return The invoice amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the current status of the invoice ("Unpaid" or "Paid").
     *
     * @return The status of the invoice.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Marks the invoice as paid by updating the status to "Paid".
     */
    public void markAsPaid() {
        this.status = "Paid";
    }

    /**
     * Checks whether the invoice is still unpaid.
     *
     * @return True if the invoice is unpaid, false otherwise.
     */
    public boolean isUnpaid() {
        return "Unpaid".equalsIgnoreCase(status);
    }
}
