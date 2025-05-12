package views;

import models.Invoice;

import java.util.List;

/**
 * Displays customer invoices in a readable format.
 */
public class InvoiceView {

    public void displayInvoices(List<Invoice> invoices) {
        System.out.println("\n--- Invoices ---");
        for (Invoice invoice : invoices) {
            System.out.printf("%s - %.2f - %s\n",
                    invoice.getCustomer(),
                    invoice.getAmount(),
                    invoice.getStatus());
        }
    }
}
