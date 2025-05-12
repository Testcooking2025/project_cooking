package services;

import models.Invoice;

import java.util.*;

/**
 * Manages the generation, update, and tracking of customer invoices.
 */
public class InvoiceManager {

    private final Map<String, Invoice> invoices = new HashMap<>();

    /**
     * Creates a new invoice and stores it.
     *
     * @param customer The name of the customer.
     * @param amount   The invoice amount.
     */
    public void createInvoice(String customer, double amount) {
        invoices.put(customer, new Invoice(customer, amount));
    }

    /**
     * Generates a new invoice (alias for createInvoice).
     */
    public void generateInvoice(String customer, double amount) {
        createInvoice(customer, amount);
    }

    /**
     * Retrieves the invoice by customer name.
     *
     * @param customer The customer's name.
     * @return Invoice or null.
     */
    public Invoice getInvoiceByCustomer(String customer) {
        return invoices.get(customer);
    }

    /**
     * Updates the status of an invoice.
     */
    public void updateInvoiceStatus(String customer, String status) {
        Invoice invoice = invoices.get(customer);
        if (invoice != null) {
            invoice.setStatus(status);
        }
    }

    /**
     * Marks an invoice as paid.
     */
    public void markAsPaid(String customer) {
        updateInvoiceStatus(customer, "Paid");
    }

    /**
     * Returns all invoices.
     */
    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices.values());
    }

    /**
     * Returns the list of customer names with unpaid invoices.
     */
    public List<String> getUnpaidCustomerNames() {
        List<String> result = new ArrayList<>();
        for (Invoice invoice : invoices.values()) {
            if ("Unpaid".equalsIgnoreCase(invoice.getStatus())) {
                result.add(invoice.getCustomer());
            }
        }
        return result;
    }

    /**
     * Clears all invoices (for testing).
     */
    public void clearInvoices() {
        invoices.clear();
    }

    /**
     * Provides a financial summary including totals and statuses.
     *
     * @return A map with keys: totalInvoices, paidInvoices, unpaidInvoices, totalAmount.
     */
    public Map<String, Object> getFinancialSummary() {
        Map<String, Object> summary = new HashMap<>();
        int totalInvoices = invoices.size();
        int paidInvoices = 0;
        int unpaidInvoices = 0;
        double totalAmount = 0.0;

        for (Invoice invoice : invoices.values()) {
            totalAmount += invoice.getAmount();
            if ("Paid".equalsIgnoreCase(invoice.getStatus())) {
                paidInvoices++;
            } else {
                unpaidInvoices++;
            }
        }

        summary.put("totalInvoices", totalInvoices);
        summary.put("paidInvoices", paidInvoices);
        summary.put("unpaidInvoices", unpaidInvoices);
        summary.put("totalAmount", totalAmount);

        return summary;
    }
}
