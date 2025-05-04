package services;

import models.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for managing invoices, including creation, payment tracking,
 * and retrieving unpaid invoice data.
 */
public class InvoiceManager {

    private final List<Invoice> invoiceList = new ArrayList<>();

    /**
     * Creates a new invoice for the specified customer with the given amount.
     *
     * @param customer The name of the customer.
     * @param amount   The invoice amount.
     */
    public void createInvoice(String customer, double amount) {
        invoiceList.add(new Invoice(customer, amount));
    }

    /**
     * Marks the invoice for the specified customer as paid, if it's currently unpaid.
     *
     * @param customer The name of the customer paying the invoice.
     */
    public void payInvoice(String customer) {
        for (Invoice i : invoiceList) {
            if (i.getCustomer().equals(customer) && i.isUnpaid()) {
                i.markAsPaid();
            }
        }
    }

    /**
     * Retrieves the invoice associated with the given customer, if it exists.
     *
     * @param customer The name of the customer.
     * @return An {@link Optional} containing the customer's invoice if found.
     */
    public Optional<Invoice> getInvoiceForCustomer(String customer) {
        return invoiceList.stream()
                .filter(i -> i.getCustomer().equals(customer))
                .findFirst();
    }

    /**
     * Loads a list of invoices into the system, replacing any existing ones.
     *
     * @param invoices A list of {@link Invoice} objects to be loaded.
     */
    public void loadInvoices(List<Invoice> invoices) {
        invoiceList.clear();
        invoiceList.addAll(invoices);
    }

    /**
     * Returns a list of customers who have unpaid invoices.
     *
     * @return A list of customer names with unpaid invoices.
     */
    public List<String> listUnpaidCustomers() {
        return invoiceList.stream()
                .filter(Invoice::isUnpaid)
                .map(Invoice::getCustomer)
                .collect(Collectors.toList());
    }

    /**
     * Returns the complete list of all invoices.
     *
     * @return A list of all {@link Invoice} objects.
     */
    public List<Invoice> getAllInvoices() {
        return invoiceList;
    }
}
