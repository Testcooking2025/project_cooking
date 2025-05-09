package services;

import models.Invoice;

import java.util.*;

public class InvoiceManager {
    private final List<Invoice> invoices = new ArrayList<>();

    /**
     * Creates a new invoice for the specified customer and amount.
     *
     * @param customer The name of the customer.
     * @param amount   The amount for the invoice.
     */
    public void createInvoice(String customer, double amount) {
        invoices.add(new Invoice(customer, amount));
    }

    /**
     * Returns all invoices in the system.
     *
     * @return A list of all invoices.
     */
    public List<Invoice> getAllInvoices() {
        return invoices;
    }

    /**
     * Computes a financial summary including total invoices, paid/unpaid counts, and total amount.
     *
     * @return A map containing summary data.
     */
    public Map<String, Object> getFinancialSummary() {
        int total = invoices.size();
        long paid = invoices.stream().filter(inv -> inv.getStatus().equalsIgnoreCase("Paid")).count();
        long unpaid = total - paid;
        double totalAmount = invoices.stream().mapToDouble(Invoice::getAmount).sum();

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("Total Invoices", total);
        summary.put("Paid Invoices", paid);
        summary.put("Unpaid Invoices", unpaid);
        summary.put("Total Amount", totalAmount);
        return summary;
    }
}
