package models;

public class Invoice {
    private final String customer;
    private final double amount;
    private String status;

    public Invoice(String customer, double amount, String status) {
        this.customer = customer;
        this.amount = amount;
        this.status = status;
    }


    public Invoice(String customer, double amount) {
        this(customer, amount, "Unpaid");
    }

    public String getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
