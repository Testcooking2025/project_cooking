package views;

import models.Invoice;

public class InvoiceView {
    public void displayInvoice(Invoice invoice) {
        System.out.println("Invoice for: " + invoice.getCustomer());
        System.out.println("Amount: $" + invoice.getAmount());
        System.out.println("Status: " + invoice.getStatus());
        System.out.println("---------------");
    }
}
